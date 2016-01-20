package org.renci.hearsay.commands.canvas;

import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_TRANSCRIPT_VERSION_ID;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Alignment;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.renci.hearsay.dao.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullAlignmentsRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PullAlignmentsRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullAlignmentsRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion, Integer genomeRefId) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public void run() {
        logger.debug("ENTERING run()");

        try {

            List<ReferenceSequence> referenceSequenceList = hearsayDAOBeanService.getReferenceSequenceDAO()
                    .findByIdentifierSystem(REFSEQ_TRANSCRIPT_VERSION_ID);

            ExecutorService es = Executors.newFixedThreadPool(12);

            for (ReferenceSequence referenceSequence : referenceSequenceList) {

                es.submit(() -> {

                    logger.info(referenceSequence.toString());

                    try {
                        Set<Identifier> identifiers = referenceSequence.getIdentifiers();

                        String transcriptId = null;
                        for (Identifier identifier : identifiers) {
                            if (identifier.getSystem().equals(REFSEQ_TRANSCRIPT_VERSION_ID)) {
                                transcriptId = identifier.getValue();
                                break;
                            }
                        }

                        if (StringUtils.isEmpty(transcriptId)) {
                            logger.warn("transcriptId is empty");
                            return;
                        }

                        List<TranscriptMaps> transcriptMapsList = canvasDAOBeanService.getTranscriptMapsDAO()
                                .findByGenomeRefIdAndRefSeqVersionAndTranscriptId(genomeRefId, refSeqVersion, transcriptId);
                        if (CollectionUtils.isEmpty(transcriptMapsList)) {
                            logger.warn("No TranscriptMaps found");
                            return;
                        }
                        logger.info("transcriptMapsList.size(): {}", transcriptMapsList.size());

                        TranscriptMaps transcriptMaps = transcriptMapsList.get(0);
                        logger.info(transcriptMaps.toString());

                        Alignment alignment = new Alignment();
                        alignment.getReferenceSequences().add(referenceSequence);
                        List<RefSeqCodingSequence> refSeqCodingSequenceList = canvasDAOBeanService.getRefSeqCodingSequenceDAO()
                                .findByRefSeqVersionAndTranscriptId("includeAll", refSeqVersion, transcriptId);
                        if (CollectionUtils.isNotEmpty(refSeqCodingSequenceList)) {
                            RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceList.get(0);
                            Set<RegionGroup> regionGroupSet = refSeqCDS.getLocations();
                            if (CollectionUtils.isNotEmpty(regionGroupSet)) {
                                RegionGroup regionGroup = regionGroupSet.iterator().next();
                                List<RegionGroupRegion> regionGroupRegionList = canvasDAOBeanService.getRegionGroupRegionDAO()
                                        .findByRegionGroupId(regionGroup.getRegionGroupId());
                                if (CollectionUtils.isNotEmpty(regionGroupRegionList)) {
                                    // should only be one here
                                    RegionGroupRegion rgr = regionGroupRegionList.get(0);
                                    Location proteinLocation = new Location(rgr.getKey().getRegionStart(), rgr.getKey().getRegionEnd());
                                    proteinLocation.setId(hearsayDAOBeanService.getLocationDAO().save(proteinLocation));
                                    alignment.setProteinLocation(proteinLocation);
                                }
                            }
                        }
                        alignment.setId(hearsayDAOBeanService.getAlignmentDAO().save(alignment));

                        List<TranscriptMapsExons> exons = canvasDAOBeanService.getTranscriptMapsExonsDAO()
                                .findByTranscriptMapsId(transcriptMaps.getId());

                        if (CollectionUtils.isEmpty(exons)) {
                            logger.warn("No Exons found");
                            return;
                        }

                        for (TranscriptMapsExons exon : exons) {
                            Region region = new Region(org.renci.hearsay.dao.model.RegionType.EXON);
                            region.setAlignment(alignment);

                            Location regionLocation = null;
                            if ("-".equals(transcriptMaps.getStrand())) {
                                regionLocation = new Location(exon.getContigEnd(), exon.getContigStart());
                            } else {
                                regionLocation = new Location(exon.getContigStart(), exon.getContigEnd());
                            }
                            regionLocation.setId(hearsayDAOBeanService.getLocationDAO().save(regionLocation));
                            region.setRegionLocation(regionLocation);

                            Location transcriptLocation = new Location(exon.getTranscrStart(), exon.getTranscrEnd());
                            transcriptLocation.setId(hearsayDAOBeanService.getLocationDAO().save(transcriptLocation));
                            region.setTranscriptLocation(transcriptLocation);

                            region.setId(hearsayDAOBeanService.getRegionDAO().save(region));
                        }

                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        e.printStackTrace();
                    }

                });

            }
            es.shutdown();
            es.awaitTermination(4L, TimeUnit.HOURS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

}

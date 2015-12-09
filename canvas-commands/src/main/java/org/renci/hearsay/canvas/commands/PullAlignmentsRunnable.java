package org.renci.hearsay.canvas.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
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
            List<TranscriptMaps> foundTranscriptReferenceSequences = canvasDAOBeanService.getTranscriptMapsDAO()
                    .findByGenomeRefIdAndRefSeqVersion("includeAll", genomeRefId, refSeqVersion);

            if (CollectionUtils.isNotEmpty(foundTranscriptReferenceSequences)) {
                logger.info("foundTranscriptReferenceSequences.size() = {}", foundTranscriptReferenceSequences.size());

                for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {

                    List<Long> referenceSequenceIdentifierIds = new ArrayList<Long>();

                    String versionedRefSeqAccession = transcriptMaps.getTranscript().getVersionId();

                    Identifier exampleIdentifier = new Identifier("canvas/refseq/transcript/versionId", versionedRefSeqAccession);
                    List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO().findByExample(exampleIdentifier);
                    if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                        foundIdentifierList.forEach(a -> referenceSequenceIdentifierIds.add(a.getId()));
                    }

                    GenomeRefSeq genomeRefSeq = transcriptMaps.getGenomeRefSeq();
                    String versionedGenomicAccession = genomeRefSeq.getVerAccession();
                    exampleIdentifier = new Identifier("canvas/ref/genomeRefSeq/verAccession", versionedGenomicAccession);
                    foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO().findByExample(exampleIdentifier);
                    if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                        foundIdentifierList.forEach(a -> referenceSequenceIdentifierIds.add(a.getId()));
                    }
                    List<ReferenceSequence> foundReferenceSequenceList = hearsayDAOBeanService.getReferenceSequenceDAO()
                            .findByIdentifiers(referenceSequenceIdentifierIds);

                    if (CollectionUtils.isEmpty(foundReferenceSequenceList)) {
                        logger.warn("ReferenceSequence not found");
                        continue;
                    }

                    ReferenceSequence referenceSequence = foundReferenceSequenceList.get(0);

                    Alignment alignment = new Alignment();
                    List<RefSeqCodingSequence> refSeqCodingSequenceList = canvasDAOBeanService.getRefSeqCodingSequenceDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
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

                    alignment.getReferenceSequences().add(referenceSequence);
                    hearsayDAOBeanService.getAlignmentDAO().save(alignment);

                    List<TranscriptMapsExons> exons = transcriptMaps.getExons();

                    if ("-".equals(transcriptMaps.getStrand())) {
                        exons.sort((a, b) -> b.getTranscrStart().compareTo(a.getTranscrStart()));
                    } else {
                        exons.sort((a, b) -> a.getTranscrStart().compareTo(b.getTranscrStart()));
                    }

                    for (TranscriptMapsExons exon : exons) {
                        Region region = new Region(org.renci.hearsay.dao.model.RegionType.EXON);
                        region.setAlignment(alignment);

                        Location regionLocation = new Location(exon.getContigStart(), exon.getContigEnd());
                        regionLocation.setId(hearsayDAOBeanService.getLocationDAO().save(regionLocation));
                        region.setRegionLocation(regionLocation);

                        Location transcriptLocation = new Location(exon.getTranscrStart(), exon.getTranscrEnd());
                        transcriptLocation.setId(hearsayDAOBeanService.getLocationDAO().save(transcriptLocation));
                        region.setTranscriptLocation(transcriptLocation);

                        region.setId(hearsayDAOBeanService.getRegionDAO().save(region));
                        alignment.getRegions().add(region);
                    }

                    hearsayDAOBeanService.getAlignmentDAO().save(alignment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

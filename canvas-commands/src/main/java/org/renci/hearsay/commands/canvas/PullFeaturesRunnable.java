package org.renci.hearsay.commands.canvas;

import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_TRANSCRIPT_VERSION_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullFeaturesRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PullFeaturesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private String geneName;

    public PullFeaturesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public void run() {
        logger.debug("ENTERING run()");

        try {
            List<ReferenceSequence> referenceSequences = new ArrayList<ReferenceSequence>();
            if (StringUtils.isNotEmpty(geneName)) {
                List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findBySymbol(geneName);
                if (CollectionUtils.isNotEmpty(geneList)) {
                    referenceSequences.addAll(hearsayDAOBeanService.getReferenceSequenceDAO().findByGeneId(geneList.get(0).getId()));
                }
            } else {
                referenceSequences
                        .addAll(hearsayDAOBeanService.getReferenceSequenceDAO().findByIdentifierSystem(REFSEQ_TRANSCRIPT_VERSION_ID));
            }

            if (CollectionUtils.isEmpty(referenceSequences)) {
                logger.warn("no ReferenceSequences found");
                return;
            }

            ExecutorService es = Executors.newFixedThreadPool(8);

            for (ReferenceSequence refseq : referenceSequences) {

                es.submit(() -> {

                    try {
                        logger.info(refseq.toString());

                        String accession = null;
                        for (Identifier identifier : refseq.getIdentifiers()) {
                            if (identifier.getSystem().equals(REFSEQ_TRANSCRIPT_VERSION_ID)) {
                                accession = identifier.getValue();
                                logger.info(accession);
                                break;
                            }
                        }

                        List<Feature> canvasFeatures = canvasDAOBeanService.getFeatureDAO()
                                .findByRefSeqVersionAndTranscriptId(refSeqVersion, accession);

                        if (CollectionUtils.isEmpty(canvasFeatures)) {
                            logger.warn("no canvas features found");
                            return;
                        }

                        logger.debug("canvasFeatures.size(): {}", canvasFeatures.size());

                        for (Feature canvasFeature : canvasFeatures) {
                            logger.info(canvasFeature.toString());
                            RegionGroup regionGroup = canvasFeature.getRegionGroup();
                            if (regionGroup != null) {
                                List<RegionGroupRegion> regionGroupRegionList = canvasDAOBeanService.getRegionGroupRegionDAO()
                                        .findByRegionGroupId(regionGroup.getRegionGroupId());

                                if (CollectionUtils.isEmpty(regionGroupRegionList)) {
                                    logger.warn("no RegionGroupRegions found");
                                    continue;
                                }

                                org.renci.hearsay.dao.model.Feature hearsayFeature = new org.renci.hearsay.dao.model.Feature();
                                if (StringUtils.isNotEmpty(canvasFeature.getNote())) {
                                    hearsayFeature.setNote(canvasFeature.getNote());
                                }

                                RegionGroupRegion rgr = regionGroupRegionList.get(0);
                                Location regionLocation = new Location(rgr.getKey().getRegionStart(), rgr.getKey().getRegionEnd());
                                regionLocation.setId(hearsayDAOBeanService.getLocationDAO().save(regionLocation));
                                hearsayFeature.getLocations().add(regionLocation);
                                hearsayFeature.getReferenceSequences().add(refseq);
                                hearsayFeature.setId(hearsayDAOBeanService.getFeatureDAO().save(hearsayFeature));
                                logger.info(hearsayFeature.toString());
                            }
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

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

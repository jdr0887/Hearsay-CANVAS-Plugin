package org.renci.hearsay.canvas.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullFeaturesCallable implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(PullFeaturesCallable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private String geneName;

    public PullFeaturesCallable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(8, 8, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
        List<ReferenceSequence> transcriptRefSeqs = new ArrayList<ReferenceSequence>();
        if (StringUtils.isNotEmpty(geneName)) {
            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findBySymbol(geneName);
            if (CollectionUtils.isNotEmpty(geneList)) {
                transcriptRefSeqs
                        .addAll(hearsayDAOBeanService.getReferenceSequenceDAO().findByGeneId(geneList.get(0).getId()));
            }
        } else {
            transcriptRefSeqs.addAll(hearsayDAOBeanService.getReferenceSequenceDAO().findAll());
        }

        if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
            for (ReferenceSequence transcriptRefSeq : transcriptRefSeqs) {
                PersistFeaturesCallable runnable = new PersistFeaturesCallable();
                runnable.setRefSeqVersion(refSeqVersion);
                runnable.setTranscriptRefSeq(transcriptRefSeq);
                tpe.submit(runnable);
            }
        }
        tpe.shutdown();
        return null;
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

    class PersistFeaturesCallable implements Callable<Void> {

        private final Logger logger = LoggerFactory.getLogger(PersistFeaturesCallable.class);

        private ReferenceSequence transcriptRefSeq;

        private String refSeqVersion;

        public PersistFeaturesCallable() {
            super();
        }

        @Override
        public Void call() throws HearsayDAOException {
            logger.debug("ENTERING call()");

            String accession = null;
            for (Identifier identifier : transcriptRefSeq.getIdentifiers()) {
                if (identifier.getSystem().equals("www.ncbi.nlm.nih.gov/nuccore")) {
                    accession = identifier.getValue();
                }
            }

            List<Feature> canvasFeatures = canvasDAOBeanService.getFeatureDAO()
                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, accession);
            if (canvasFeatures != null && !canvasFeatures.isEmpty()) {
                for (Feature canvasFeature : canvasFeatures) {
                    RegionGroup regionGroup = canvasFeature.getRegionGroup();
                    if (regionGroup != null) {
                        Set<RegionGroupRegion> regionGroupRegions = regionGroup.getRegionGroupRegions();
                        if (regionGroupRegions != null && !regionGroupRegions.isEmpty()) {
                            RegionGroupRegion[] regionGroupRegionArray = regionGroupRegions
                                    .toArray(new RegionGroupRegion[regionGroupRegions.size()]);
                            org.renci.hearsay.dao.model.Feature hearsayFeature = new org.renci.hearsay.dao.model.Feature();
                            hearsayFeature.setNote(canvasFeature.getNote());

                            Location regionLocation = new Location(regionGroupRegionArray[0].getRegionStart(),
                                    regionGroupRegionArray[0].getRegionEnd());
                            regionLocation.setId(hearsayDAOBeanService.getLocationDAO().save(regionLocation));

                            hearsayFeature.getLocations().add(regionLocation);
                            hearsayDAOBeanService.getFeatureDAO().save(hearsayFeature);

                            transcriptRefSeq.getFeatures().add(hearsayFeature);
                            hearsayDAOBeanService.getReferenceSequenceDAO().save(transcriptRefSeq);
                        }
                    }
                }
            }
            return null;
        }

        public ReferenceSequence getTranscriptRefSeq() {
            return transcriptRefSeq;
        }

        public void setTranscriptRefSeq(ReferenceSequence transcriptRefSeq) {
            this.transcriptRefSeq = transcriptRefSeq;
        }

        public String getRefSeqVersion() {
            return refSeqVersion;
        }

        public void setRefSeqVersion(String refSeqVersion) {
            this.refSeqVersion = refSeqVersion;
        }

    }

}

package org.renci.hearsay.commons.canvas;

import java.util.List;
import java.util.Set;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullFeaturesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullFeaturesRunnable.class);

    private String refSeqVersion;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullFeaturesRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");
        try {
            List<TranscriptRefSeq> transcriptRefSeqs = hearsayDAOBean.getTranscriptRefSeqDAO().findAll();
            if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
                for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
                    List<Feature> canvasFeatures = canvasDAOBean.getFeatureDAO().findByRefSeqVersionAndTranscriptId(
                            refSeqVersion, transcriptRefSeq.getAccession());

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
                                    hearsayFeature.setRegionStart(regionGroupRegionArray[0].getRegionStart());
                                    hearsayFeature.setRegionStop(regionGroupRegionArray[0].getRegionEnd());
                                    hearsayFeature.setTranscriptRefSeq(transcriptRefSeq);
                                    hearsayDAOBean.getFeatureDAO().save(hearsayFeature);
                                }
                            }
                        }
                    }
                }
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

    }

    public CANVASDAOBean getCanvasDAOBean() {
        return canvasDAOBean;
    }

    public void setCanvasDAOBean(CANVASDAOBean canvasDAOBean) {
        this.canvasDAOBean = canvasDAOBean;
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

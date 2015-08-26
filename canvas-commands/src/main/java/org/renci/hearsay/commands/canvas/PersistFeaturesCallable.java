package org.renci.hearsay.commands.canvas;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistFeaturesCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PersistFeaturesCallable.class);

    private TranscriptRefSeq transcriptRefSeq;

    private String refSeqVersion;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PersistFeaturesCallable() {
        super();
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");
        List<Feature> canvasFeatures = canvasDAOBean.getFeatureDAO().findByRefSeqVersionAndTranscriptId(refSeqVersion,
                transcriptRefSeq.getAccession());
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
        return null;
    }

    public TranscriptRefSeq getTranscriptRefSeq() {
        return transcriptRefSeq;
    }

    public void setTranscriptRefSeq(TranscriptRefSeq transcriptRefSeq) {
        this.transcriptRefSeq = transcriptRefSeq;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
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

}

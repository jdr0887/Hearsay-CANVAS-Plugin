package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingCallable implements Callable<List<org.renci.hearsay.dao.model.Transcript>> {

    private final Logger logger = LoggerFactory.getLogger(CreateTranscriptListCallable.class);

    private String refSeqVersion;

    private Integer genomeRefId;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public CANVASCachingCallable() {
        super();
    }

    @Override
    public List<org.renci.hearsay.dao.model.Transcript> call() {
        logger.info("ENTERING call()");
        List<org.renci.hearsay.dao.model.Transcript> results = new ArrayList<org.renci.hearsay.dao.model.Transcript>();

        logger.info(refSeqVersion);
        logger.info(genomeRefId.toString());

        try {

            List<TranscriptMapsExons> mapsExonsResults = canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersion(genomeRefId, refSeqVersion);

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                CreateTranscriptListCallable persistTranscriptRunnable = new CreateTranscriptListCallable();
                persistTranscriptRunnable.setCanvasDAOBean(canvasDAOBean);
                persistTranscriptRunnable.setHearsayDAOBean(hearsayDAOBean);
                persistTranscriptRunnable.setMapsExonsResults(mapsExonsResults);
                persistTranscriptRunnable.setRefSeqVersion(refSeqVersion);
                results.addAll(persistTranscriptRunnable.call());
            }

        } catch (Exception e) {
            logger.error("error", e);
        }

        logger.info("LEAVING call()");
        return results;
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

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

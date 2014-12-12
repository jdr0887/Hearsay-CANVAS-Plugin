package org.renci.hearsay.commons.canvas;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
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
            ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 2, 2, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
            List<TranscriptRefSeq> transcriptRefSeqs = hearsayDAOBean.getTranscriptRefSeqDAO().findAll();
            if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
                for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
                    PersistFeaturesRunnable runnable = new PersistFeaturesRunnable();
                    runnable.setCanvasDAOBean(canvasDAOBean);
                    runnable.setHearsayDAOBean(hearsayDAOBean);
                    runnable.setRefSeqVersion(refSeqVersion);
                    runnable.setTranscriptRefSeq(transcriptRefSeq);
                    tpe.submit(runnable);
                }
            }
            tpe.shutdown();
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

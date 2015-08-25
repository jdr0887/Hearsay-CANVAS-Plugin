package org.renci.hearsay.commons.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullFeaturesCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullFeaturesCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    private String geneName;

    public PullFeaturesCallable(CANVASDAOBean canvasDAOBean, HearsayDAOBean hearsayDAOBean, String refSeqVersion) {
        super();
        this.canvasDAOBean = canvasDAOBean;
        this.hearsayDAOBean = hearsayDAOBean;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(8, 8, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());
        List<TranscriptRefSeq> transcriptRefSeqs = new ArrayList<TranscriptRefSeq>();
        if (StringUtils.isNotEmpty(geneName)) {
            transcriptRefSeqs.addAll(hearsayDAOBean.getTranscriptRefSeqDAO().findByGeneName(geneName));
        } else {
            transcriptRefSeqs.addAll(hearsayDAOBean.getTranscriptRefSeqDAO().findAll());
        }

        if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
            for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
                PersistFeaturesCallable runnable = new PersistFeaturesCallable();
                runnable.setCanvasDAOBean(canvasDAOBean);
                runnable.setHearsayDAOBean(hearsayDAOBean);
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

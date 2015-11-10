package org.renci.hearsay.commands.canvas;

import java.util.List;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenesRunnable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    public PullGenesRunnable(CANVASDAOBean canvasDAOBean, HearsayDAOBean hearsayDAOBean, String refSeqVersion) {
        super();
        this.canvasDAOBean = canvasDAOBean;
        this.hearsayDAOBean = hearsayDAOBean;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");
        try {
            List<RefSeqGene> refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersion(refSeqVersion);
            if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                for (RefSeqGene refSeqGene : refSeqGeneList) {
                    Gene exampleGene = new Gene();
                    exampleGene.setSymbol(refSeqGene.getName());
                    
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByExample(exampleGene);
                    if (alreadyPersistedGeneList == null
                            || (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty())) {
                        exampleGene.setDescription(refSeqGene.getDescription());
                        hearsayDAOBean.getGeneDAO().save(exampleGene);
                    }
                }
            }
        } catch (Exception e) {
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

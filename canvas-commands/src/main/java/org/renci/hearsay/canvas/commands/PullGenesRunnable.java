package org.renci.hearsay.canvas.commands;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Gene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    public PullGenesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {
            List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO()
                    .findByRefSeqVersion(refSeqVersion);
            if (CollectionUtils.isNotEmpty(refSeqGeneList)) {
                for (RefSeqGene refSeqGene : refSeqGeneList) {
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO()
                            .findBySymbol(refSeqGene.getName());
                    if (CollectionUtils.isNotEmpty(alreadyPersistedGeneList)) {
                        logger.warn("Gene is already persisted: {}", refSeqGene.getName());
                        continue;
                    }

                    Gene gene = new Gene();
                    gene.setSymbol(refSeqGene.getName());
                    gene.setDescription(refSeqGene.getDescription());
                    logger.info("persisting: {}", gene.toString());
                    hearsayDAOBeanService.getGeneDAO().save(gene);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("FINISHED run()");
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

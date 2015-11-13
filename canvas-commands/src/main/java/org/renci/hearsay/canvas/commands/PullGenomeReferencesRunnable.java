package org.renci.hearsay.canvas.commands;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.GenomeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenomeReferencesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenomeReferencesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenomeReferencesRunnable(CANVASDAOBeanService canvasDAOBeanService,
            HearsayDAOBeanService hearsayDAOBeanService) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {
            List<GenomeRefSeq> genomeRefSeqList = canvasDAOBeanService.getGenomeRefSeqDAO().findAll();
            if (CollectionUtils.isNotEmpty(genomeRefSeqList)) {
                for (GenomeRefSeq genomeRefSeq : genomeRefSeqList) {
                    GenomeReference foundGenomeReference = hearsayDAOBeanService.getGenomeReferenceDAO()
                            .findByName(genomeRefSeq.getVerAccession());
                    if (foundGenomeReference == null) {
                        GenomeReference genomeReference = new GenomeReference(genomeRefSeq.getVerAccession());
                        hearsayDAOBeanService.getGenomeReferenceDAO().save(genomeReference);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("FINISHED run()");
    }

}

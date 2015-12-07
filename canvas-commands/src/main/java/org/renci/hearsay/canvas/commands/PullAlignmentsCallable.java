package org.renci.hearsay.canvas.commands;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullAlignmentsCallable implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(PullAlignmentsCallable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullAlignmentsCallable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion, Integer genomeRefId) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING run()");

        List<TranscriptMaps> foundTranscriptReferenceSequences = canvasDAOBeanService.getTranscriptMapsDAO()
                .findByGenomeRefIdAndRefSeqVersion(genomeRefId, refSeqVersion);

        if (CollectionUtils.isNotEmpty(foundTranscriptReferenceSequences)) {
            logger.info("foundTranscriptReferenceSequences.size() = {}", foundTranscriptReferenceSequences.size());

            for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {

            }
        }
        return null;
    }

}

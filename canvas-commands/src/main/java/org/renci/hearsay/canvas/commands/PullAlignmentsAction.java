package org.renci.hearsay.canvas.commands;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-alignments", description = "Pull Alignments")
@Service
public class PullAlignmentsAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PullAlignmentsAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Argument(index = 1, name = "genomeRefId", description = "GenomeRef Identifier", required = true, multiValued = false)
    private Integer genomeRefId;

    @Reference
    private CANVASDAOBeanService canvasDAOBeanService;

    @Reference
    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullAlignmentsAction() {
        super();
    }

    @Override
    public Object execute() {
        logger.debug("ENTERING execute()");
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(new PullAlignmentsRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion, genomeRefId));
        es.shutdown();
        return null;
    }

}

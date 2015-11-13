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

@Command(scope = "canvas", name = "pull-genes", description = "Pull Genes")
@Service
public class PullGenesAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(PullGenesAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Reference
    private CANVASDAOBeanService canvasDAOBeanService;

    @Reference
    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenesAction() {
        super();
    }

    @Override
    public Object execute() {
        logger.debug("ENTERING execute()");
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new PullGenesRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion));
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

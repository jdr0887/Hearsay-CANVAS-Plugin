package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-features", description = "Pull Features")
@Service
public class PullFeaturesAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PullFeaturesAction.class);

    @Option(name = "--refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Option(name = "--geneName", description = "RefSeq Version Identifier", required = false, multiValued = false)
    private String geneName;

    @Reference
    private CANVASDAOBeanService canvasDAOBeanService;

    @Reference
    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullFeaturesAction() {
        super();
    }

    @Override
    public Object execute() {
        logger.debug("ENTERING execute()");
        try {
            PullFeaturesRunnable callable = new PullFeaturesRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion);
            if (StringUtils.isNotEmpty(geneName)) {
                callable.setGeneName(geneName);
            }
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(callable);
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

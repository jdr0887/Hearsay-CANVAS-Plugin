package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullFeaturesRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-features", description = "Pull Features")
public class PullFeaturesAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullFeaturesAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    private PullFeaturesRunnable runnable;

    public PullFeaturesAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        runnable.setRefSeqVersion(refSeqVersion);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(runnable);
        es.shutdown();
        return null;
    }

    public PullFeaturesRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(PullFeaturesRunnable runnable) {
        this.runnable = runnable;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

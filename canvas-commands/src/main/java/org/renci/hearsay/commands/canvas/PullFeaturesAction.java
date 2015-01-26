package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullFeaturesCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-features", description = "Pull Features")
public class PullFeaturesAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullFeaturesAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    private PullFeaturesCallable callable;

    public PullFeaturesAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        callable.setRefSeqVersion(refSeqVersion);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(callable);
        es.shutdown();
        return null;
    }

    public PullFeaturesCallable getCallable() {
        return callable;
    }

    public void setCallable(PullFeaturesCallable callable) {
        this.callable = callable;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

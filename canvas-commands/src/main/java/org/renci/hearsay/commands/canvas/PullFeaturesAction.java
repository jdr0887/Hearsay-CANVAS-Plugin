package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-features", description = "Pull Features")
public class PullFeaturesAction extends AbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(PullFeaturesAction.class);

    @Option(name = "--refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Option(name = "--geneName", description = "RefSeq Version Identifier", required = false, multiValued = false)
    private String geneName;

    private PullFeaturesCallable callable;

    public PullFeaturesAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");

        callable.setRefSeqVersion(refSeqVersion);

        if (StringUtils.isNotEmpty(geneName)) {
            callable.setGeneName(geneName);
        }
        try {
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

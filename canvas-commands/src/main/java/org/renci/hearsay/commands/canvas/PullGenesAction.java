package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullGenesRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-genes", description = "Pull Genes")
public class PullGenesAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullGenesAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Argument(index = 1, name = "genomeRefId", description = "GenomeRef Identifier", required = true, multiValued = false)
    private Integer genomeRefId;

    private PullGenesRunnable runnable;

    public PullGenesAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        runnable.setRefSeqVersion(refSeqVersion);
        runnable.setGenomeRefId(genomeRefId);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(runnable);
        es.shutdown();
        return null;
    }

    public PullGenesRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(PullGenesRunnable runnable) {
        this.runnable = runnable;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

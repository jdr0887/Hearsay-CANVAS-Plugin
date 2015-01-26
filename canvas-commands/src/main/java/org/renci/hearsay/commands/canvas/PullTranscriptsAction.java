package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullTranscriptsCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-transcripts", description = "Pull Transcripts")
public class PullTranscriptsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullTranscriptsAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Argument(index = 1, name = "genomeRefId", description = "GenomeRef Identifier", required = true, multiValued = false)
    private Integer genomeRefId;

    private PullTranscriptsCallable callable;

    public PullTranscriptsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        callable.setRefSeqVersion(refSeqVersion);
        callable.setGenomeRefId(genomeRefId);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(callable);
        es.shutdown();
        return null;
    }

    public PullTranscriptsCallable getCallable() {
        return callable;
    }

    public void setCallable(PullTranscriptsCallable callable) {
        this.callable = callable;
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

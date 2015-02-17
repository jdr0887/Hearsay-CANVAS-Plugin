package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.commons.canvas.PullTranscriptsCallable;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-transcripts", description = "Pull Transcripts")
public class PullTranscriptsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullTranscriptsAction.class);

    @Argument(index = 0, name = "refSeqVersion", description = "RefSeq Version Identifier", required = true, multiValued = false)
    private String refSeqVersion;

    @Argument(index = 1, name = "genomeRefId", description = "GenomeRef Identifier", required = true, multiValued = false)
    private Integer genomeRefId;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullTranscriptsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        PullTranscriptsCallable callable = new PullTranscriptsCallable(canvasDAOBean, hearsayDAOBean, refSeqVersion,
                genomeRefId);
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(callable);
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

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

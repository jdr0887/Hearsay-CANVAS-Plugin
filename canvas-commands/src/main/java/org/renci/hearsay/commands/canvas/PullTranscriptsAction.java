package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-transcripts", description = "Pull Transcripts")
public class PullTranscriptsAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PullTranscriptsAction.class);

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
    public Object execute() {
        logger.debug("ENTERING execute()");
        try {
            PullTranscriptsCallable callable = new PullTranscriptsCallable();
            callable.setCanvasDAOBean(canvasDAOBean);
            callable.setHearsayDAOBean(hearsayDAOBean);
            callable.setRefSeqVersion(refSeqVersion);
            callable.setGenomeRefId(genomeRefId);

            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(callable);
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CANVASDAOBean getCanvasDAOBean() {
        return canvasDAOBean;
    }

    public void setCanvasDAOBean(CANVASDAOBean canvasDAOBean) {
        this.canvasDAOBean = canvasDAOBean;
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
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

package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.DeleteTranscriptAlignmentsRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "delete-transcript-alignments", description = "Delete Transcript Alignments")
public class DeleteTranscriptAlignmentsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(DeleteTranscriptAlignmentsAction.class);

    private DeleteTranscriptAlignmentsRunnable runnable;

    public DeleteTranscriptAlignmentsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(runnable);
        es.shutdown();
        return null;
    }

    public DeleteTranscriptAlignmentsRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(DeleteTranscriptAlignmentsRunnable runnable) {
        this.runnable = runnable;
    }

}

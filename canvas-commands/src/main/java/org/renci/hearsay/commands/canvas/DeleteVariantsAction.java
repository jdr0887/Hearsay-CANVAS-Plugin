package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.DeleteVariantsCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "delete-variants", description = "Delete Variants")
public class DeleteVariantsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(DeleteVariantsAction.class);

    private DeleteVariantsCallable runnable;

    public DeleteVariantsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        ExecutorService es = Executors.newSingleThreadExecutor();
        try {
            es.submit(runnable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.shutdown();
        return null;
    }

    public DeleteVariantsCallable getRunnable() {
        return runnable;
    }

    public void setRunnable(DeleteVariantsCallable runnable) {
        this.runnable = runnable;
    }

}

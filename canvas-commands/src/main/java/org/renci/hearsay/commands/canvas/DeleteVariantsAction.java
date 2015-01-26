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

    private DeleteVariantsCallable callable;

    public DeleteVariantsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        ExecutorService es = Executors.newSingleThreadExecutor();
        try {
            es.submit(callable).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.shutdown();
        return null;
    }

    public DeleteVariantsCallable getCallable() {
        return callable;
    }

    public void setCallable(DeleteVariantsCallable callable) {
        this.callable = callable;
    }

}

package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullVariantsRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-variants", description = "Pull Variants")
public class PullVariantsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsAction.class);

    private PullVariantsRunnable runnable;

    public PullVariantsAction() {
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

    public PullVariantsRunnable getRunnable() {
        return runnable;
    }

    public void setRunnable(PullVariantsRunnable runnable) {
        this.runnable = runnable;
    }

}

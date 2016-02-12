package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-variants", description = "Pull Variants")
@Service
public class PullVariantsAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsAction.class);

    @Reference
    private CANVASDAOBeanService canvasDAOBeanService;

    @Reference
    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullVariantsAction() {
        super();
    }

    @Override
    public Object execute() {
        logger.debug("ENTERING execute()");
        PullVariantsRunnable callable = new PullVariantsRunnable(canvasDAOBeanService, hearsayDAOBeanService);
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(callable);
        es.shutdown();
        return null;
    }

}

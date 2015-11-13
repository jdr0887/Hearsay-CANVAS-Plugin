package org.renci.hearsay.canvas.commands;

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

@Command(scope = "canvas", name = "pull-gene-population-frequencies", description = "Pull Gene Population Frequencies")
@Service
public class PullGenePopulationFrequenciesAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesAction.class);

    @Reference
    private CANVASDAOBeanService canvasDAOBeanService;

    @Reference
    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenePopulationFrequenciesAction() {
        super();
    }

    @Override
    public Object execute() throws Exception {
        logger.debug("ENTERING execute()");
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(new PullGenePopulationFrequenciesCallable(canvasDAOBeanService, hearsayDAOBeanService));
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

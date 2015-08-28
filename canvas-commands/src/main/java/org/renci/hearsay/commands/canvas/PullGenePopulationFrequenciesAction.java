package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-gene-population-frequencies", description = "Pull Gene Population Frequencies")
public class PullGenePopulationFrequenciesAction extends AbstractAction {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesAction.class);

    private PullGenePopulationFrequenciesCallable callable;

    public PullGenePopulationFrequenciesAction() {
        super();
    }

    @Override
    protected Object doExecute() throws Exception {
        logger.debug("ENTERING doExecute()");
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(callable);
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PullGenePopulationFrequenciesCallable getCallable() {
        return callable;
    }

    public void setCallable(PullGenePopulationFrequenciesCallable callable) {
        this.callable = callable;
    }

}

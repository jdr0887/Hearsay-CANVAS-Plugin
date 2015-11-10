package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-gene-population-frequencies", description = "Pull Gene Population Frequencies")
public class PullGenePopulationFrequenciesAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesAction.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullGenePopulationFrequenciesAction() {
        super();
    }

    @Override
    public Object execute() throws Exception {
        logger.debug("ENTERING execute()");

        PullGenePopulationFrequenciesCallable callable = new PullGenePopulationFrequenciesCallable();

        callable.setCanvasDAOBean(canvasDAOBean);
        callable.setHearsayDAOBean(hearsayDAOBean);

        try {
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

}

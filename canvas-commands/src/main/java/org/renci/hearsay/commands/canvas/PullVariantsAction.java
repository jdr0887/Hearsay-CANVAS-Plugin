package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;
import org.apache.karaf.shell.api.action.Action;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-variants", description = "Pull Variants")
public class PullVariantsAction implements Action {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsAction.class);

    @Option(name = "--locVarId", description = "location variant id", required = false, multiValued = false)
    private Long locationVariantId;

    @Option(name = "--geneName", description = "Gene name", required = false, multiValued = false)
    private String geneName;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullVariantsAction() {
        super();
    }

    @Override
    public Object execute() {
        logger.debug("ENTERING execute()");

        PullVariantsCallable callable = new PullVariantsCallable(canvasDAOBean, hearsayDAOBean);

        if (StringUtils.isNotEmpty(geneName)) {
            callable.setGeneName(geneName);
        }

        if (locationVariantId != null) {
            callable.setLocationVariantId(locationVariantId);
        }

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(callable);
        es.shutdown();
        return null;
    }

    public Long getLocationVariantId() {
        return locationVariantId;
    }

    public void setLocationVariantId(Long locationVariantId) {
        this.locationVariantId = locationVariantId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
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

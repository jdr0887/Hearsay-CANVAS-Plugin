package org.renci.hearsay.commands.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.DeleteVariantsCallable;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "delete-variants", description = "Delete Variants")
public class DeleteVariantsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(DeleteVariantsAction.class);

    @Argument(name = "geneName", description = "Gene Name", required = false, multiValued = false)
    private String geneName;

    private HearsayDAOBean hearsayDAOBean;

    public DeleteVariantsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        DeleteVariantsCallable callable = new DeleteVariantsCallable(hearsayDAOBean);
        if (StringUtils.isNotEmpty(geneName)) {
            callable.setGeneName(geneName);
        }
        try {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(callable);
            es.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

}

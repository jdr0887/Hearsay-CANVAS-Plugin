package org.renci.hearsay.commands.canvas;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.AbstractAction;
import org.renci.hearsay.commons.canvas.PullVariantsCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Command(scope = "canvas", name = "pull-variants", description = "Pull Variants")
public class PullVariantsAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsAction.class);

    private PullVariantsCallable callable;

    public PullVariantsAction() {
        super();
    }

    @Override
    public Object doExecute() {
        logger.debug("ENTERING doExecute()");
        callable.call();
        return null;
    }

    public PullVariantsCallable getCallable() {
        return callable;
    }

    public void setCallable(PullVariantsCallable callable) {
        this.callable = callable;
    }

}

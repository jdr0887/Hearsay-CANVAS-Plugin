package org.renci.hearsay.canvas;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingTask extends TimerTask {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingTask.class);

    private CANVASCachingCallable callable;

    public CANVASCachingTask() {
        super();
    }

    @Override
    public void run() {
        logger.debug("ENTERING run()");
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            List<org.renci.hearsay.dao.model.Transcript> results = executor.submit(callable).get();
            logger.info("results.size(): {}", results.size());
            executor.shutdownNow();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public CANVASCachingCallable getCallable() {
        return callable;
    }

    public void setCallable(CANVASCachingCallable callable) {
        this.callable = callable;
    }

}

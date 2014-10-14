package org.renci.hearsay.canvas;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.renci.hearsay.dao.model.TranscriptRefSeq;
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
            List<TranscriptRefSeq> results = executor.submit(callable).get();
            logger.info("results.size(): {}", results.size());
            executor.awaitTermination(1L, TimeUnit.DAYS);
            executor.shutdownNow();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Error", e);
        }
    }

    public CANVASCachingCallable getCallable() {
        return callable;
    }

    public void setCallable(CANVASCachingCallable callable) {
        this.callable = callable;
    }

}

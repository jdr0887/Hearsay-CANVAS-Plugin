package org.renci.hearsay.canvas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingService {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingService.class);

    private CANVASCachingRunnable runnable;

    private final ExecutorService es = Executors.newSingleThreadExecutor();

    public CANVASCachingService() {
        super();
    }

    public void start() throws Exception {
        logger.info("ENTERING start()");
        es.execute(runnable);
        es.shutdown();
    }

    public void stop() throws Exception {
        logger.info("ENTERING stop()");
        es.shutdownNow();
    }

}

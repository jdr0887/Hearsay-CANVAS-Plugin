package org.renci.hearsay.canvas;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingService {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingService.class);

    private final Timer cacheTimer = new Timer();

    private CANVASCachingPropertyBean cachingPropertyBean;

    private CANVASCachingTask task;

    public CANVASCachingService() {
        super();
    }

    public void start() throws Exception {
        logger.info("ENTERING start()");
        long delay = 1 * 60 * 1000; // 1 minute
        cacheTimer.scheduleAtFixedRate(this.task, delay,
                this.cachingPropertyBean.getDelayBetweenCachingRuns() * 60 * 1000);
    }

    public void stop() throws Exception {
        logger.info("ENTERING stop()");
        cacheTimer.cancel();
    }

    public CANVASCachingPropertyBean getCachingPropertyBean() {
        return cachingPropertyBean;
    }

    public void setCachingPropertyBean(CANVASCachingPropertyBean cachingPropertyBean) {
        this.cachingPropertyBean = cachingPropertyBean;
    }

    public CANVASCachingTask getTask() {
        return task;
    }

    public void setTask(CANVASCachingTask task) {
        this.task = task;
    }

}

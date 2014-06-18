package org.renci.hearsay.canvas;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingService {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingService.class);

    private final Timer cacheTimer = new Timer();

    private Boolean active;

    private Integer delayBetweenRuns;

    private CANVASCachingTask task;

    public CANVASCachingService() {
        super();
    }

    public void start() throws Exception {
        logger.info("ENTERING start()");
        long delay = 1 * 60 * 1000; // 1 minute
        cacheTimer.scheduleAtFixedRate(this.task, delay, this.delayBetweenRuns * 60 * 1000);
    }

    public void stop() throws Exception {
        logger.info("ENTERING stop()");
        cacheTimer.cancel();
    }

    public Integer getDelayBetweenRuns() {
        return delayBetweenRuns;
    }

    public void setDelayBetweenRuns(Integer delayBetweenRuns) {
        this.delayBetweenRuns = delayBetweenRuns;
    }

    public CANVASCachingTask getTask() {
        return task;
    }

    public void setTask(CANVASCachingTask task) {
        this.task = task;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}

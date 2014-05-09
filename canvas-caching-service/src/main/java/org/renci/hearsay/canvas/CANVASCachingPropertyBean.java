package org.renci.hearsay.canvas;

public class CANVASCachingPropertyBean {

    private String refSeqVersion;

    private Integer genomeRefId;

    private Integer delayBetweenCachingRuns;

    private Boolean cachingIsOn;

    public CANVASCachingPropertyBean() {
        super();
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

    public Integer getDelayBetweenCachingRuns() {
        return delayBetweenCachingRuns;
    }

    public void setDelayBetweenCachingRuns(Integer delayBetweenCachingRuns) {
        this.delayBetweenCachingRuns = delayBetweenCachingRuns;
    }

    public Boolean getCachingIsOn() {
        return cachingIsOn;
    }

    public void setCachingIsOn(Boolean cachingIsOn) {
        this.cachingIsOn = cachingIsOn;
    }

    @Override
    public String toString() {
        return String
                .format("CANVASCachingPropertyBean [refSeqVersion=%s, genomeRefId=%s, delayBetweenCachingRuns=%s, cachingIsOn=%s]",
                        refSeqVersion, genomeRefId, delayBetweenCachingRuns, cachingIsOn);
    }

}

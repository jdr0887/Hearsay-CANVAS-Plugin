package org.renci.hearsay.canvas.dao.model;

import org.apache.commons.lang.math.IntRange;
import org.renci.hearsay.dao.model.RegionType;

public class Exon {

    private Integer number;

    private Integer transcriptStart;

    private Integer transcriptEnd;

    private Integer contigStart;

    private Integer contigEnd;

    private RegionType regionType;

    public Exon() {
        super();
    }

    public Exon(Integer number, Integer transcriptStart, Integer transcriptEnd, Integer contigStart, Integer contigEnd,
            RegionType regionType) {
        super();
        this.number = number;
        this.transcriptStart = transcriptStart;
        this.transcriptEnd = transcriptEnd;
        this.contigStart = contigStart;
        this.contigEnd = contigEnd;
        this.regionType = regionType;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTranscriptStart() {
        return transcriptStart;
    }

    public void setTranscriptStart(Integer transcriptStart) {
        this.transcriptStart = transcriptStart;
    }

    public Integer getTranscriptEnd() {
        return transcriptEnd;
    }

    public void setTranscriptEnd(Integer transcriptEnd) {
        this.transcriptEnd = transcriptEnd;
    }

    public Integer getContigStart() {
        return contigStart;
    }

    public void setContigStart(Integer contigStart) {
        this.contigStart = contigStart;
    }

    public Integer getContigEnd() {
        return contigEnd;
    }

    public void setContigEnd(Integer contigEnd) {
        this.contigEnd = contigEnd;
    }

    public IntRange toRange() {
        return new IntRange(this.contigStart, this.contigEnd);
    }

    @Override
    public String toString() {
        return "Exon [number=" + number + ", transcriptStart=" + transcriptStart + ", transcriptEnd=" + transcriptEnd
                + ", contigStart=" + contigStart + ", contigEnd=" + contigEnd + "]";
    }

}

package org.renci.hearsay.canvas.dao;

import org.apache.commons.lang.math.IntRange;

public class Exon {

    private Integer number;

    private Integer transcriptStart;

    private Integer transcriptEnd;

    private Integer contigStart;

    private Integer contigEnd;

    public Exon(Integer number, Integer transcriptStart, Integer transcriptEnd, Integer contigStart, Integer contigEnd) {
        super();
        this.number = number;
        this.transcriptStart = transcriptStart;
        this.transcriptEnd = transcriptEnd;
        this.contigStart = contigStart;
        this.contigEnd = contigEnd;
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

}

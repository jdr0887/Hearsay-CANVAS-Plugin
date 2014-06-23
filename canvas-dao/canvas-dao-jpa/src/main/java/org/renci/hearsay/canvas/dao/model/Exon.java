package org.renci.hearsay.canvas.dao.model;

import org.apache.commons.lang.math.IntRange;
import org.renci.hearsay.dao.model.RegionType;

public class Exon {

    private Integer number;

    private Integer transcriptStart;

    private Integer transcriptEnd;

    private Integer contigStart;

    private Integer contigEnd;

    private Integer genomeStart;

    private Integer genomeEnd;

    private RegionType regionType;

    public Exon() {
        super();
    }

    public Integer getGenomeStart() {
        return genomeStart;
    }

    public void setGenomeStart(Integer genomeStart) {
        this.genomeStart = genomeStart;
    }

    public Integer getGenomeEnd() {
        return genomeEnd;
    }

    public void setGenomeEnd(Integer genomeEnd) {
        this.genomeEnd = genomeEnd;
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
        return new IntRange(this.genomeStart, this.genomeEnd);
    }

    @Override
    public String toString() {
        return String
                .format("Exon [number=%s, transcriptStart=%s, transcriptEnd=%s, contigStart=%s, contigEnd=%s, genomeStart=%s, genomeEnd=%s, regionType=%s]",
                        number, transcriptStart, transcriptEnd, contigStart, contigEnd, genomeStart, genomeEnd,
                        regionType.toString());
    }

}

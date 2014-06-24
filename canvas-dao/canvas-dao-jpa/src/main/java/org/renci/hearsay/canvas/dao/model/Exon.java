package org.renci.hearsay.canvas.dao.model;

import org.apache.commons.lang.math.IntRange;
import org.renci.hearsay.dao.model.RegionType;

public class Exon implements Comparable<Exon> {

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contigEnd == null) ? 0 : contigEnd.hashCode());
        result = prime * result + ((contigStart == null) ? 0 : contigStart.hashCode());
        result = prime * result + ((genomeEnd == null) ? 0 : genomeEnd.hashCode());
        result = prime * result + ((genomeStart == null) ? 0 : genomeStart.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
        result = prime * result + ((transcriptEnd == null) ? 0 : transcriptEnd.hashCode());
        result = prime * result + ((transcriptStart == null) ? 0 : transcriptStart.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exon other = (Exon) obj;
        if (contigEnd == null) {
            if (other.contigEnd != null)
                return false;
        } else if (!contigEnd.equals(other.contigEnd))
            return false;
        if (contigStart == null) {
            if (other.contigStart != null)
                return false;
        } else if (!contigStart.equals(other.contigStart))
            return false;
        if (genomeEnd == null) {
            if (other.genomeEnd != null)
                return false;
        } else if (!genomeEnd.equals(other.genomeEnd))
            return false;
        if (genomeStart == null) {
            if (other.genomeStart != null)
                return false;
        } else if (!genomeStart.equals(other.genomeStart))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        if (regionType != other.regionType)
            return false;
        if (transcriptEnd == null) {
            if (other.transcriptEnd != null)
                return false;
        } else if (!transcriptEnd.equals(other.transcriptEnd))
            return false;
        if (transcriptStart == null) {
            if (other.transcriptStart != null)
                return false;
        } else if (!transcriptStart.equals(other.transcriptStart))
            return false;
        return true;
    }

    @Override
    public int compareTo(Exon e) {
        int ret = e.getGenomeStart().compareTo(this.genomeStart);
        if (ret == 0) {
            switch (this.regionType) {
                case UTR3:
                    ret = 1;
                    break;
                case EXON:
                case INTRON:
                case UTR:
                    ret = e.getTranscriptStart().compareTo(this.transcriptStart);
                    break;
                case UTR5:
                    ret = -1;
                    break;
            }
        }
        if (ret == 0) {
            ret = e.getTranscriptStart().compareTo(this.transcriptStart);
        }
        if (ret == 0 && this.contigStart != null && e.getContigStart() != null) {
            ret = e.getContigStart().compareTo(this.contigStart);
        }
        return ret;
    }

}

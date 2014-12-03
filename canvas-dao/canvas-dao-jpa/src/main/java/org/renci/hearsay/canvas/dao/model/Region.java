package org.renci.hearsay.canvas.dao.model;

import org.apache.commons.lang.math.IntRange;
import org.renci.hearsay.dao.model.RegionType;

public class Region implements Comparable<Region> {

    private Integer number;

    private Integer transcriptStart;

    private Integer transcriptStop;

    private Integer contigStart;

    private Integer contigStop;

    private Integer genomeStart;

    private Integer genomeStop;

    private RegionType regionType;

    public Region() {
        super();
    }

    public Integer getGenomeStart() {
        return genomeStart;
    }

    public void setGenomeStart(Integer genomeStart) {
        this.genomeStart = genomeStart;
    }

    public Integer getGenomeStop() {
        return genomeStop;
    }

    public void setGenomeStop(Integer genomeStop) {
        this.genomeStop = genomeStop;
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

    public Integer getTranscriptStop() {
        return transcriptStop;
    }

    public void setTranscriptStop(Integer transcriptStop) {
        this.transcriptStop = transcriptStop;
    }

    public Integer getContigStart() {
        return contigStart;
    }

    public void setContigStart(Integer contigStart) {
        this.contigStart = contigStart;
    }

    public Integer getContigStop() {
        return contigStop;
    }

    public void setContigStop(Integer contigStop) {
        this.contigStop = contigStop;
    }

    public IntRange toRange() {
        return new IntRange(this.genomeStart, this.genomeStop);
    }

    @Override
    public String toString() {

        // if (regionType == null) {
        // return String
        // .format("Region [number=%s, genomeStart=%s, genomeStop=%s, transcriptStart=%s, transcriptStop=%s, contigStart=%s, contigStop=%s]",
        // number, genomeStart, genomeStop, transcriptStart, transcriptStop, contigStart, contigStop);
        // }
        // return String
        // .format("Region [number=%s, genomeStart=%s, genomeStop=%s, transcriptStart=%s, transcriptStop=%s, contigStart=%s, contigStop=%s, regionType=%s]",
        // number, genomeStart, genomeStop, transcriptStart, transcriptStop, contigStart, contigStop,
        // regionType.toString());

        return String.format("%d\t%d\t%s\t%s\t%s", genomeStart, genomeStop, regionType.toString(),
                transcriptStart == null ? "" : transcriptStart.toString(),
                transcriptStop == null ? "" : transcriptStop.toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contigStop == null) ? 0 : contigStop.hashCode());
        result = prime * result + ((contigStart == null) ? 0 : contigStart.hashCode());
        result = prime * result + ((genomeStop == null) ? 0 : genomeStop.hashCode());
        result = prime * result + ((genomeStart == null) ? 0 : genomeStart.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((regionType == null) ? 0 : regionType.hashCode());
        result = prime * result + ((transcriptStop == null) ? 0 : transcriptStop.hashCode());
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
        Region other = (Region) obj;
        if (contigStop == null) {
            if (other.contigStop != null)
                return false;
        } else if (!contigStop.equals(other.contigStop))
            return false;
        if (contigStart == null) {
            if (other.contigStart != null)
                return false;
        } else if (!contigStart.equals(other.contigStart))
            return false;
        if (genomeStop == null) {
            if (other.genomeStop != null)
                return false;
        } else if (!genomeStop.equals(other.genomeStop))
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
        if (transcriptStop == null) {
            if (other.transcriptStop != null)
                return false;
        } else if (!transcriptStop.equals(other.transcriptStop))
            return false;
        if (transcriptStart == null) {
            if (other.transcriptStart != null)
                return false;
        } else if (!transcriptStart.equals(other.transcriptStart))
            return false;
        return true;
    }

    @Override
    public int compareTo(Region e) {
        int ret = 0;
        if (ret == 0) {
            ret = this.genomeStart.compareTo(e.getGenomeStart());
        }
        if (ret == 0 && this.transcriptStart != null && e.getTranscriptStart() != null) {
            ret = this.transcriptStart.compareTo(e.getTranscriptStart());
        }
        if (ret == 0 && this.genomeStop != null && e.getGenomeStop() != null) {
            ret = this.genomeStop.compareTo(e.getGenomeStop());
        }
        if (ret == 0 && this.transcriptStop != null && e.getTranscriptStop() != null) {
            ret = this.transcriptStop.compareTo(e.getTranscriptStop());
        }
        if (ret == 0 && this.contigStart != null && e.getContigStart() != null) {
            ret = this.contigStart.compareTo(e.getContigStart());
        }
        if (ret == 0 && this.contigStop != null && e.getContigStop() != null) {
            ret = this.contigStop.compareTo(e.getContigStop());
        }
        if (ret == 0 && this.regionType != null) {
            ret = this.regionType.compareTo(e.getRegionType());
        }
        return ret;
    }

}

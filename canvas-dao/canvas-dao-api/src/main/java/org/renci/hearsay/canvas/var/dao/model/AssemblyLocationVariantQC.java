package org.renci.hearsay.canvas.var.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "var", name = "asm_loc_var_qc")
public class AssemblyLocationVariantQC implements Persistable {

    private static final long serialVersionUID = -5626899616641958084L;

    @MapsId("asm_id")
    @ManyToOne
    @JoinColumn(name = "asm_id")
    private Assembly asm;

    @MapsId("loc_var_id")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @EmbeddedId
    private AssemblyLocationVariantQCPK key;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "qd")
    private Float qd;

    @Column(name = "read_pos_rank_sum")
    private Float readPosRankSum;

    @Column(name = "frac_reads_with_dels")
    private Float fracReadsWithDels;

    @Column(name = "hrun")
    private Integer hrun;

    @Column(name = "strand_score")
    private Float strandScore;

    @Column(name = "ref_depth")
    private Integer refDepth;

    @Column(name = "alt_depth")
    private Integer altDepth;

    public AssemblyLocationVariantQC() {
        super();
    }

    public Assembly getAsm() {
        return asm;
    }

    public void setAsm(Assembly asm) {
        this.asm = asm;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public AssemblyLocationVariantQCPK getKey() {
        return key;
    }

    public void setKey(AssemblyLocationVariantQCPK key) {
        this.key = key;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Float getQd() {
        return qd;
    }

    public void setQd(Float qd) {
        this.qd = qd;
    }

    public Float getReadPosRankSum() {
        return readPosRankSum;
    }

    public void setReadPosRankSum(Float readPosRankSum) {
        this.readPosRankSum = readPosRankSum;
    }

    public Float getFracReadsWithDels() {
        return fracReadsWithDels;
    }

    public void setFracReadsWithDels(Float fracReadsWithDels) {
        this.fracReadsWithDels = fracReadsWithDels;
    }

    public Integer getHrun() {
        return hrun;
    }

    public void setHrun(Integer hrun) {
        this.hrun = hrun;
    }

    public Float getStrandScore() {
        return strandScore;
    }

    public void setStrandScore(Float strandScore) {
        this.strandScore = strandScore;
    }

    public Integer getRefDepth() {
        return refDepth;
    }

    public void setRefDepth(Integer refDepth) {
        this.refDepth = refDepth;
    }

    public Integer getAltDepth() {
        return altDepth;
    }

    public void setAltDepth(Integer altDepth) {
        this.altDepth = altDepth;
    }

    @Override
    public String toString() {
        return "AssemblyLocactionVariantQC [asm=" + asm + ", locationVariant=" + locationVariant + ", key=" + key
                + ", depth=" + depth + ", qd=" + qd + ", readPosRankSum=" + readPosRankSum + ", fracReadsWithDels="
                + fracReadsWithDels + ", hrun=" + hrun + ", strandScore=" + strandScore + ", refDepth=" + refDepth
                + ", altDepth=" + altDepth + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((altDepth == null) ? 0 : altDepth.hashCode());
        result = prime * result + ((asm == null) ? 0 : asm.hashCode());
        result = prime * result + ((depth == null) ? 0 : depth.hashCode());
        result = prime * result + ((fracReadsWithDels == null) ? 0 : fracReadsWithDels.hashCode());
        result = prime * result + ((hrun == null) ? 0 : hrun.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((qd == null) ? 0 : qd.hashCode());
        result = prime * result + ((readPosRankSum == null) ? 0 : readPosRankSum.hashCode());
        result = prime * result + ((refDepth == null) ? 0 : refDepth.hashCode());
        result = prime * result + ((strandScore == null) ? 0 : strandScore.hashCode());
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
        AssemblyLocationVariantQC other = (AssemblyLocationVariantQC) obj;
        if (altDepth == null) {
            if (other.altDepth != null)
                return false;
        } else if (!altDepth.equals(other.altDepth))
            return false;
        if (asm == null) {
            if (other.asm != null)
                return false;
        } else if (!asm.equals(other.asm))
            return false;
        if (depth == null) {
            if (other.depth != null)
                return false;
        } else if (!depth.equals(other.depth))
            return false;
        if (fracReadsWithDels == null) {
            if (other.fracReadsWithDels != null)
                return false;
        } else if (!fracReadsWithDels.equals(other.fracReadsWithDels))
            return false;
        if (hrun == null) {
            if (other.hrun != null)
                return false;
        } else if (!hrun.equals(other.hrun))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (locationVariant == null) {
            if (other.locationVariant != null)
                return false;
        } else if (!locationVariant.equals(other.locationVariant))
            return false;
        if (qd == null) {
            if (other.qd != null)
                return false;
        } else if (!qd.equals(other.qd))
            return false;
        if (readPosRankSum == null) {
            if (other.readPosRankSum != null)
                return false;
        } else if (!readPosRankSum.equals(other.readPosRankSum))
            return false;
        if (refDepth == null) {
            if (other.refDepth != null)
                return false;
        } else if (!refDepth.equals(other.refDepth))
            return false;
        if (strandScore == null) {
            if (other.strandScore != null)
                return false;
        } else if (!strandScore.equals(other.strandScore))
            return false;
        return true;
    }

}

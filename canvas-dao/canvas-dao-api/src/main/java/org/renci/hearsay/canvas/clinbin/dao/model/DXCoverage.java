package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "dx_coverage")
public class DXCoverage implements Persistable {

    private static final long serialVersionUID = -2985428056984351080L;

    @EmbeddedId
    private DXCoveragePK key;

    @MapsId("exon")
    @OneToOne
    @JoinColumn(name = "dx_exon_id")
    private DXExons exon;

    @Column(name = "frac_gt_1")
    private Double fracGt1;

    @Column(name = "frac_gt_5")
    private Double fracGt5;

    @Column(name = "frac_gt_10")
    private Double fracGt10;

    @Column(name = "frac_gt_15")
    private Double fracGt15;

    @Column(name = "frac_gt_30")
    private Double fracGt30;

    @Column(name = "frac_gt_50")
    private Double fracGt50;

    public DXCoverage() {
        super();
    }

    public DXCoveragePK getKey() {
        return key;
    }

    public void setKey(DXCoveragePK key) {
        this.key = key;
    }

    public DXExons getExon() {
        return exon;
    }

    public void setExon(DXExons exon) {
        this.exon = exon;
    }

    public Double getFracGt1() {
        return fracGt1;
    }

    public void setFracGt1(Double fracGt1) {
        this.fracGt1 = fracGt1;
    }

    public Double getFracGt5() {
        return fracGt5;
    }

    public void setFracGt5(Double fracGt5) {
        this.fracGt5 = fracGt5;
    }

    public Double getFracGt10() {
        return fracGt10;
    }

    public void setFracGt10(Double fracGt10) {
        this.fracGt10 = fracGt10;
    }

    public Double getFracGt15() {
        return fracGt15;
    }

    public void setFracGt15(Double fracGt15) {
        this.fracGt15 = fracGt15;
    }

    public Double getFracGt30() {
        return fracGt30;
    }

    public void setFracGt30(Double fracGt30) {
        this.fracGt30 = fracGt30;
    }

    public Double getFracGt50() {
        return fracGt50;
    }

    public void setFracGt50(Double fracGt50) {
        this.fracGt50 = fracGt50;
    }

    @Override
    public String toString() {
        return String.format(
                "DxCoverage [exon=%s, fracGt1=%s, fracGt5=%s, fracGt10=%s, fracGt15=%s, fracGt30=%s, fracGt50=%s]",
                exon, fracGt1, fracGt5, fracGt10, fracGt15, fracGt30, fracGt50);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exon == null) ? 0 : exon.hashCode());
        result = prime * result + ((fracGt1 == null) ? 0 : fracGt1.hashCode());
        result = prime * result + ((fracGt10 == null) ? 0 : fracGt10.hashCode());
        result = prime * result + ((fracGt15 == null) ? 0 : fracGt15.hashCode());
        result = prime * result + ((fracGt30 == null) ? 0 : fracGt30.hashCode());
        result = prime * result + ((fracGt5 == null) ? 0 : fracGt5.hashCode());
        result = prime * result + ((fracGt50 == null) ? 0 : fracGt50.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        DXCoverage other = (DXCoverage) obj;
        if (exon == null) {
            if (other.exon != null)
                return false;
        } else if (!exon.equals(other.exon))
            return false;
        if (fracGt1 == null) {
            if (other.fracGt1 != null)
                return false;
        } else if (!fracGt1.equals(other.fracGt1))
            return false;
        if (fracGt10 == null) {
            if (other.fracGt10 != null)
                return false;
        } else if (!fracGt10.equals(other.fracGt10))
            return false;
        if (fracGt15 == null) {
            if (other.fracGt15 != null)
                return false;
        } else if (!fracGt15.equals(other.fracGt15))
            return false;
        if (fracGt30 == null) {
            if (other.fracGt30 != null)
                return false;
        } else if (!fracGt30.equals(other.fracGt30))
            return false;
        if (fracGt5 == null) {
            if (other.fracGt5 != null)
                return false;
        } else if (!fracGt5.equals(other.fracGt5))
            return false;
        if (fracGt50 == null) {
            if (other.fracGt50 != null)
                return false;
        } else if (!fracGt50.equals(other.fracGt50))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UnimportantExonPK implements Serializable {

    private static final long serialVersionUID = -7711785993510035926L;

    @Column(name = "transcr")
    private String transcr;

    @Column(name = "noncan_exon")
    private Integer noncanExon;

    public UnimportantExonPK() {
        super();
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    public Integer getNoncanExon() {
        return noncanExon;
    }

    public void setNoncanExon(Integer noncanExon) {
        this.noncanExon = noncanExon;
    }

    @Override
    public String toString() {
        return String.format("UnimportantExonPK [transcr=%s, noncanExon=%s]", transcr, noncanExon);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((noncanExon == null) ? 0 : noncanExon.hashCode());
        result = prime * result + ((transcr == null) ? 0 : transcr.hashCode());
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
        UnimportantExonPK other = (UnimportantExonPK) obj;
        if (noncanExon == null) {
            if (other.noncanExon != null)
                return false;
        } else if (!noncanExon.equals(other.noncanExon))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

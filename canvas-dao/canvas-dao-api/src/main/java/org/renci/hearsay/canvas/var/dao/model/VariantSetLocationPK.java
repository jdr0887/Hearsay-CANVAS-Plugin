package org.renci.hearsay.canvas.var.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VariantSetLocationPK implements Serializable {

    private static final long serialVersionUID = -2850126342576220251L;

    @Column(name = "ver_set_id")
    private Integer varSetId;

    @Column(name = "ref_ver_accession")
    private String versionAccession;

    @Column(name = "pos")
    private Integer pos;

    public VariantSetLocationPK() {
        super();
    }

    public Integer getVarSetId() {
        return varSetId;
    }

    public void setVarSetId(Integer varSetId) {
        this.varSetId = varSetId;
    }

    public String getVersionAccession() {
        return versionAccession;
    }

    public void setVersionAccession(String versionAccession) {
        this.versionAccession = versionAccession;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "VariantSetLocationPK [varSetId=" + varSetId + ", versionAccession=" + versionAccession + ", pos=" + pos
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((varSetId == null) ? 0 : varSetId.hashCode());
        result = prime * result + ((versionAccession == null) ? 0 : versionAccession.hashCode());
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
        VariantSetLocationPK other = (VariantSetLocationPK) obj;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (varSetId == null) {
            if (other.varSetId != null)
                return false;
        } else if (!varSetId.equals(other.varSetId))
            return false;
        if (versionAccession == null) {
            if (other.versionAccession != null)
                return false;
        } else if (!versionAccession.equals(other.versionAccession))
            return false;
        return true;
    }

}

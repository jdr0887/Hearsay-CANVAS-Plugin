package org.renci.hearsay.canvas.var.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssemblyLocationPK implements Serializable {

    private static final long serialVersionUID = 745714824781965526L;

    @Column(name = "asm_id")
    private Integer asmId;

    @Column(name = "ref_ver_accession")
    private String versionAccession;

    @Column(name = "pos")
    private Integer pos;

    public AssemblyLocationPK() {
        super();
    }

    public Integer getAsmId() {
        return asmId;
    }

    public void setAsmId(Integer asmId) {
        this.asmId = asmId;
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
        return "AssemblyLocationPK [asmId=" + asmId + ", versionAccession=" + versionAccession + ", pos=" + pos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asmId == null) ? 0 : asmId.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
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
        AssemblyLocationPK other = (AssemblyLocationPK) obj;
        if (asmId == null) {
            if (other.asmId != null)
                return false;
        } else if (!asmId.equals(other.asmId))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (versionAccession == null) {
            if (other.versionAccession != null)
                return false;
        } else if (!versionAccession.equals(other.versionAccession))
            return false;
        return true;
    }

}

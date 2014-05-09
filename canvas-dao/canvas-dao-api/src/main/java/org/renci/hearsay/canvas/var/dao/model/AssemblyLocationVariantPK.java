package org.renci.hearsay.canvas.var.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssemblyLocationVariantPK implements Serializable {

    private static final long serialVersionUID = 745714824781965526L;

    @Column(name = "asm_id", nullable = false)
    private Integer asmId;

    @Column(name = "loc_var_id", nullable = false)
    private Long locationVariantId;

    public AssemblyLocationVariantPK() {
        super();
    }

    public Integer getAsmId() {
        return asmId;
    }

    public void setAsmId(Integer asmId) {
        this.asmId = asmId;
    }

    public Long getLocationVariantId() {
        return locationVariantId;
    }

    public void setLocationVariantId(Long locationVariantId) {
        this.locationVariantId = locationVariantId;
    }

    @Override
    public String toString() {
        return "AssemblyLocationVariantQCPK [asmId=" + asmId + ", locationVariantId=" + locationVariantId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asmId == null) ? 0 : asmId.hashCode());
        result = prime * result + ((locationVariantId == null) ? 0 : locationVariantId.hashCode());
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
        AssemblyLocationVariantPK other = (AssemblyLocationVariantPK) obj;
        if (asmId == null) {
            if (other.asmId != null)
                return false;
        } else if (!asmId.equals(other.asmId))
            return false;
        if (locationVariantId == null) {
            if (other.locationVariantId != null)
                return false;
        } else if (!locationVariantId.equals(other.locationVariantId))
            return false;
        return true;
    }

}

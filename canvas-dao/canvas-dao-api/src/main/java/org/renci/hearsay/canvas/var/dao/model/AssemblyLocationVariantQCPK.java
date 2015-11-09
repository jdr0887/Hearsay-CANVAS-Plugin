package org.renci.hearsay.canvas.var.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssemblyLocationVariantQCPK implements Serializable {

    private static final long serialVersionUID = 1885906950903803056L;

    @Column(name = "asm_id")
    private Integer assembly;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    public AssemblyLocationVariantQCPK() {
        super();
    }

    public Integer getAssembly() {
        return assembly;
    }

    public void setAssembly(Integer assembly) {
        this.assembly = assembly;
    }

    public Long getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(Long locationVariant) {
        this.locationVariant = locationVariant;
    }

    @Override
    public String toString() {
        return String.format("AssemblyLocationVariantQCPK [assembly=%s, locationVariant=%s]", assembly,
                locationVariant);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assembly == null) ? 0 : assembly.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
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
        AssemblyLocationVariantQCPK other = (AssemblyLocationVariantQCPK) obj;
        if (assembly == null) {
            if (other.assembly != null)
                return false;
        } else if (!assembly.equals(other.assembly))
            return false;
        if (locationVariant == null) {
            if (other.locationVariant != null)
                return false;
        } else if (!locationVariant.equals(other.locationVariant))
            return false;
        return true;
    }

}

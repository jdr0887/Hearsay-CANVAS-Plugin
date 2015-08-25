package org.renci.hearsay.canvas.dao.model;

import java.io.Serializable;
import java.util.TreeSet;

import org.renci.hearsay.dao.model.StrandType;

public class Mapping implements Serializable {

    private static final long serialVersionUID = 1556350565254385634L;

    private String versionAccession;

    private StrandType strandType;

    private TreeSet<Region> regions;

    public Mapping(String versionAccession, StrandType strandType) {
        super();
        this.versionAccession = versionAccession;
        this.strandType = strandType;
        this.regions = new TreeSet<Region>();
    }

    public StrandType getStrandType() {
        return strandType;
    }

    public void setStrandType(StrandType strandType) {
        this.strandType = strandType;
    }

    public String getVersionAccession() {
        return versionAccession;
    }

    public void setVersionAccession(String versionAccession) {
        this.versionAccession = versionAccession;
    }

    public TreeSet<Region> getRegions() {
        return regions;
    }

    @Override
    public String toString() {
        return String.format("Mapping [versionAccession=%s, strandType=%s]", versionAccession, strandType);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((strandType == null) ? 0 : strandType.hashCode());
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
        Mapping other = (Mapping) obj;
        if (strandType != other.strandType)
            return false;
        if (versionAccession == null) {
            if (other.versionAccession != null)
                return false;
        } else if (!versionAccession.equals(other.versionAccession))
            return false;
        return true;
    }

}

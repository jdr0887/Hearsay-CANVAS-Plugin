package org.renci.hearsay.canvas.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mapping implements Serializable {

    private static final long serialVersionUID = 1556350565254385634L;

    private String versionAccession;

    private List<Exon> exons = new ArrayList<Exon>();

    public Mapping(String versionAccession) {
        super();
        this.versionAccession = versionAccession;
    }

    public String getVersionAccession() {
        return versionAccession;
    }

    public void setVersionAccession(String versionAccession) {
        this.versionAccession = versionAccession;
    }

    public List<Exon> getExons() {
        return exons;
    }

    public void setExons(List<Exon> exons) {
        this.exons = exons;
    }

    @Override
    public String toString() {
        return String.format("Mapping [versionAccession=%s]", versionAccession);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        if (versionAccession == null) {
            if (other.versionAccession != null)
                return false;
        } else if (!versionAccession.equals(other.versionAccession))
            return false;
        return true;
    }

}

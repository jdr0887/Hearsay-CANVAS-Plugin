package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "incidental_bin_group_version")
public class IncidentalBinGroupVersion implements Persistable {

    private static final long serialVersionUID = 2212074020390950607L;

    @EmbeddedId
    private IncidentalBinGroupVersionPK key;

    @MapsId
    @Column(name = "ibin_group_version")
    private Integer ibinGroupVersion;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "incidental_bin_id")
    private IncidentalBin incidentalBin;

    public IncidentalBinGroupVersion() {
        super();
    }

    public IncidentalBinGroupVersionPK getKey() {
        return key;
    }

    public void setKey(IncidentalBinGroupVersionPK key) {
        this.key = key;
    }

    public Integer getIbinGroupVersion() {
        return ibinGroupVersion;
    }

    public void setIbinGroupVersion(Integer ibinGroupVersion) {
        this.ibinGroupVersion = ibinGroupVersion;
    }

    public IncidentalBin getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(IncidentalBin incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    @Override
    public String toString() {
        return String.format("IncidentalBinGroupVersion [ibinGroupVersion=%s, incidentalBin=%s]", ibinGroupVersion,
                incidentalBin);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ibinGroupVersion == null) ? 0 : ibinGroupVersion.hashCode());
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
        IncidentalBinGroupVersion other = (IncidentalBinGroupVersion) obj;
        if (ibinGroupVersion == null) {
            if (other.ibinGroupVersion != null)
                return false;
        } else if (!ibinGroupVersion.equals(other.ibinGroupVersion))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}

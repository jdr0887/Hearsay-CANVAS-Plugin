package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "analysis_class_incidental")
public class AnalysisClassIncidental implements Persistable {

    private static final long serialVersionUID = -4523788379740204587L;

    @EmbeddedId
    private AnalysisClassIncidentalPK key;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    public AnalysisClassIncidental() {
        super();
    }

    public AnalysisClassIncidentalPK getKey() {
        return key;
    }

    public void setKey(AnalysisClassIncidentalPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    @Override
    public String toString() {
        return String.format("AnalysisClassIncidental [key=%s, locationVariant=%s]", key, locationVariant);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        AnalysisClassIncidental other = (AnalysisClassIncidental) obj;
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
        return true;
    }

}

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
@Table(schema = "clinbin", name = "diagnostic_bin_results")
public class DiagnosticBinResults implements Persistable {

    private static final long serialVersionUID = -4066677132203326435L;

    @EmbeddedId
    private DiagnosticBinResultsPK key;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @MapsId("dx")
    @ManyToOne
    @JoinColumn(name = "dx_id")
    private DX dx;

    @MapsId("diseaseClass")
    @ManyToOne
    @JoinColumn(name = "class_id")
    private DiseaseClass diseaseClass;

    public DiagnosticBinResults() {
        super();
    }

    public DiagnosticBinResultsPK getKey() {
        return key;
    }

    public void setKey(DiagnosticBinResultsPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public DX getDx() {
        return dx;
    }

    public void setDx(DX dx) {
        this.dx = dx;
    }

    public DiseaseClass getDiseaseClass() {
        return diseaseClass;
    }

    public void setDiseaseClass(DiseaseClass diseaseClass) {
        this.diseaseClass = diseaseClass;
    }

    @Override
    public String toString() {
        return String.format("DiagnosticBinResults [key=%s]", key);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((diseaseClass == null) ? 0 : diseaseClass.hashCode());
        result = prime * result + ((dx == null) ? 0 : dx.hashCode());
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
        DiagnosticBinResults other = (DiagnosticBinResults) obj;
        if (diseaseClass == null) {
            if (other.diseaseClass != null)
                return false;
        } else if (!diseaseClass.equals(other.diseaseClass))
            return false;
        if (dx == null) {
            if (other.dx != null)
                return false;
        } else if (!dx.equals(other.dx))
            return false;
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

package org.renci.hearsay.canvas.exac.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "exac", name = "max_variant_freq")
@NamedQueries({
        @NamedQuery(name = "exac.MaxVariantFrequency.findByLocationVariantIdAndVersion", query = "SELECT a FROM MaxVariantFrequency a where a.locationVariant.id = :locationVariantId and a.version = :version order by a.maxAlleleFrequency desc") })
public class MaxVariantFrequency implements Persistable {

    private static final long serialVersionUID = -1388708510623130329L;

    @EmbeddedId
    private MaxVariantFrequencyPK key;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @Column(name = "max_allele_freq")
    private Double maxAlleleFrequency;

    public MaxVariantFrequency() {
        super();
    }

    public MaxVariantFrequencyPK getKey() {
        return key;
    }

    public void setKey(MaxVariantFrequencyPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Double getMaxAlleleFrequency() {
        return maxAlleleFrequency;
    }

    public void setMaxAlleleFrequency(Double maxAlleleFrequency) {
        this.maxAlleleFrequency = maxAlleleFrequency;
    }

    @Override
    public String toString() {
        return String.format("MaxVariantFrequency [locationVariant=%s, maxAlleleFrequency=%s]", locationVariant,
                maxAlleleFrequency);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((maxAlleleFrequency == null) ? 0 : maxAlleleFrequency.hashCode());
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
        MaxVariantFrequency other = (MaxVariantFrequency) obj;
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
        if (maxAlleleFrequency == null) {
            if (other.maxAlleleFrequency != null)
                return false;
        } else if (!maxAlleleFrequency.equals(other.maxAlleleFrequency))
            return false;
        return true;
    }

}

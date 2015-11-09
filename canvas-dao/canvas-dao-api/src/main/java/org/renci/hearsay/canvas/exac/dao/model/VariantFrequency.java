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
@Table(schema = "exac", name = "variant_freq")
@NamedQueries({
        @NamedQuery(name = "exac.VariantFrequency.findByLocationVariantIdAndVersion", query = "SELECT a FROM VariantFrequency a where a.locationVariant.id = :locationVariantId and a.version = :version order by a.alternateAlleleFrequency desc") })
public class VariantFrequency implements Persistable {

    private static final long serialVersionUID = 4359650786462818369L;

    @EmbeddedId
    private VariantFrequencyPK key;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @Column(name = "alt_allele_freq")
    private Double alternateAlleleFrequency;

    @Column(name = "alt_allele_count", insertable = false, updatable = false)
    private Integer alternateAlleleCount;

    @Column(name = "alt_allele_count")
    private Integer totalAlleleCount;

    public VariantFrequency() {
        super();
    }

    public VariantFrequencyPK getKey() {
        return key;
    }

    public void setKey(VariantFrequencyPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Double getAlternateAlleleFrequency() {
        return alternateAlleleFrequency;
    }

    public void setAlternateAlleleFrequency(Double alternateAlleleFrequency) {
        this.alternateAlleleFrequency = alternateAlleleFrequency;
    }

    public Integer getAlternateAlleleCount() {
        return alternateAlleleCount;
    }

    public void setAlternateAlleleCount(Integer alternateAlleleCount) {
        this.alternateAlleleCount = alternateAlleleCount;
    }

    public Integer getTotalAlleleCount() {
        return totalAlleleCount;
    }

    public void setTotalAlleleCount(Integer totalAlleleCount) {
        this.totalAlleleCount = totalAlleleCount;
    }

    @Override
    public String toString() {
        return String.format(
                "VariantFrequency [locationVariant=%s, alternateAlleleFrequency=%s, alternateAlleleCount=%s, totalAlleleCount=%s]",
                locationVariant, alternateAlleleFrequency, alternateAlleleCount, totalAlleleCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternateAlleleCount == null) ? 0 : alternateAlleleCount.hashCode());
        result = prime * result + ((alternateAlleleFrequency == null) ? 0 : alternateAlleleFrequency.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((totalAlleleCount == null) ? 0 : totalAlleleCount.hashCode());
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
        VariantFrequency other = (VariantFrequency) obj;
        if (alternateAlleleCount == null) {
            if (other.alternateAlleleCount != null)
                return false;
        } else if (!alternateAlleleCount.equals(other.alternateAlleleCount))
            return false;
        if (alternateAlleleFrequency == null) {
            if (other.alternateAlleleFrequency != null)
                return false;
        } else if (!alternateAlleleFrequency.equals(other.alternateAlleleFrequency))
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
        if (totalAlleleCount == null) {
            if (other.totalAlleleCount != null)
                return false;
        } else if (!totalAlleleCount.equals(other.totalAlleleCount))
            return false;
        return true;
    }

}

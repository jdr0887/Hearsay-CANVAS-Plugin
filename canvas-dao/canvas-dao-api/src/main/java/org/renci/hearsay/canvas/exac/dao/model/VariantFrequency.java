package org.renci.hearsay.canvas.exac.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "exac", name = "variant_freq")
public class VariantFrequency implements Persistable {

    private static final long serialVersionUID = 4359650786462818369L;

    @EmbeddedId
    private VariantFrequencyPK key;

    @MapsId
    @ManyToOne
    private LocationVariant locationVariant;

    @MapsId
    @Column(name = "exac_version", length = 10)
    private String version;

    @MapsId
    @Column(name = "population", length = 5)
    private String population;

    @Column(name = "alt_allele_freq")
    private Double alternateAlleleFrequency;

    @Column(name = "alt_allele_count")
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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
        return String
                .format("VariantFrequency [locationVariant=%s, version=%s, population=%s, alternateAlleleFrequency=%s, alternateAlleleCount=%s, totalAlleleCount=%s]",
                        locationVariant, version, population, alternateAlleleFrequency, alternateAlleleCount,
                        totalAlleleCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternateAlleleCount == null) ? 0 : alternateAlleleCount.hashCode());
        result = prime * result + ((alternateAlleleFrequency == null) ? 0 : alternateAlleleFrequency.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((population == null) ? 0 : population.hashCode());
        result = prime * result + ((totalAlleleCount == null) ? 0 : totalAlleleCount.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        if (population == null) {
            if (other.population != null)
                return false;
        } else if (!population.equals(other.population))
            return false;
        if (totalAlleleCount == null) {
            if (other.totalAlleleCount != null)
                return false;
        } else if (!totalAlleleCount.equals(other.totalAlleleCount))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

}

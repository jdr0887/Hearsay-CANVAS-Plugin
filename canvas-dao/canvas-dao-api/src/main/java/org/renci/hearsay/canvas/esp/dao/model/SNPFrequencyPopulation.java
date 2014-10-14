package org.renci.hearsay.canvas.esp.dao.model;

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
@Table(schema = "esp", name = "snp_freq_population")
@NamedQueries({ @NamedQuery(name = "esp.SNPFrequencyPopulation.findByLocationVariantIdAndVersion", query = "SELECT a FROM SNPFrequencyPopulation a where a.locationVariant.id = :locationVariantId and a.version = :version") })
public class SNPFrequencyPopulation implements Persistable {

    private static final long serialVersionUID = 644922221095328483L;

    @EmbeddedId
    private SNPFrequencyPopulationPK key;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @MapsId
    @Column(name = "esp_version")
    private Integer version;

    @MapsId
    @Column(name = "population", length = 5)
    private String population;

    @Column(name = "alt_allele_freq")
    private Float altAlleleFreq;

    @Column(name = "total_allele_count")
    private Integer totalAlleleCount;

    @Column(name = "alt_allele_count")
    private Integer altAlleleCount;

    public SNPFrequencyPopulation() {
        super();
    }

    public SNPFrequencyPopulationPK getKey() {
        return key;
    }

    public void setKey(SNPFrequencyPopulationPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public Float getAltAlleleFreq() {
        return altAlleleFreq;
    }

    public void setAltAlleleFreq(Float altAlleleFreq) {
        this.altAlleleFreq = altAlleleFreq;
    }

    public Integer getTotalAlleleCount() {
        return totalAlleleCount;
    }

    public void setTotalAlleleCount(Integer totalAlleleCount) {
        this.totalAlleleCount = totalAlleleCount;
    }

    public Integer getAltAlleleCount() {
        return altAlleleCount;
    }

    public void setAltAlleleCount(Integer altAlleleCount) {
        this.altAlleleCount = altAlleleCount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return String
                .format("SNPFrequencyPopulation [version=%s, population=%s, altAlleleFreq=%s, totalAlleleCount=%s, altAlleleCount=%s]",
                        version, population, altAlleleFreq, totalAlleleCount, altAlleleCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((altAlleleCount == null) ? 0 : altAlleleCount.hashCode());
        result = prime * result + ((altAlleleFreq == null) ? 0 : altAlleleFreq.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((population == null) ? 0 : population.hashCode());
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
        SNPFrequencyPopulation other = (SNPFrequencyPopulation) obj;
        if (altAlleleCount == null) {
            if (other.altAlleleCount != null)
                return false;
        } else if (!altAlleleCount.equals(other.altAlleleCount))
            return false;
        if (altAlleleFreq == null) {
            if (other.altAlleleFreq != null)
                return false;
        } else if (!altAlleleFreq.equals(other.altAlleleFreq))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
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
        return true;
    }

}

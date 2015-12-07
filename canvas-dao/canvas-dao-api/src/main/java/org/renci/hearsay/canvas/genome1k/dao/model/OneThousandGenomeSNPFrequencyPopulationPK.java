package org.renci.hearsay.canvas.genome1k.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OneThousandGenomeSNPFrequencyPopulationPK implements Serializable {

    private static final long serialVersionUID = -6667568470320810780L;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "gen1000_version")
    private Integer version;

    @Column(name = "population", length = 5)
    private String population;

    public OneThousandGenomeSNPFrequencyPopulationPK() {
        super();
    }

    public Long getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(Long locationVariant) {
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

    @Override
    public String toString() {
        return String.format("SNPFrequencyPopulationPK [locationVariant=%s, version=%s, population=%s]", locationVariant, version,
                population);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((population == null) ? 0 : population.hashCode());
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
        OneThousandGenomeSNPFrequencyPopulationPK other = (OneThousandGenomeSNPFrequencyPopulationPK) obj;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
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
        return true;
    }

}

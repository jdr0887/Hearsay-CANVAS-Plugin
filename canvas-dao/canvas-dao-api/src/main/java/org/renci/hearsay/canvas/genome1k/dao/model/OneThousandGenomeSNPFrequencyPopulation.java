package org.renci.hearsay.canvas.genome1k.dao.model;

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
@Table(schema = "gen1000", name = "snp_freq_population")
@NamedQueries({
        @NamedQuery(name = "genome1k.OneThousandGenomeSNPFrequencyPopulation.findByLocationVariantIdAndVersion", query = "SELECT a FROM OneThousandGenomeSNPFrequencyPopulation a where a.locationVariant.id = :locationVariantId and a.version = :version") })
public class OneThousandGenomeSNPFrequencyPopulation implements Persistable {

    private static final long serialVersionUID = 644922221095328483L;

    @EmbeddedId
    private OneThousandGenomeSNPFrequencyPopulationPK key;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @Column(name = "alt_allele_freq")
    private Float altAlleleFreq;

    @Column(name = "total_allele_count")
    private Integer totalAlleleCount;

    @Column(name = "alt_allele_count")
    private Integer altAlleleCount;

    public OneThousandGenomeSNPFrequencyPopulation() {
        super();
    }

    public OneThousandGenomeSNPFrequencyPopulationPK getKey() {
        return key;
    }

    public void setKey(OneThousandGenomeSNPFrequencyPopulationPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
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
        return String.format("SNPFrequencyPopulation [altAlleleFreq=%s, totalAlleleCount=%s, altAlleleCount=%s]",
                altAlleleFreq, totalAlleleCount, altAlleleCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((altAlleleCount == null) ? 0 : altAlleleCount.hashCode());
        result = prime * result + ((altAlleleFreq == null) ? 0 : altAlleleFreq.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        OneThousandGenomeSNPFrequencyPopulation other = (OneThousandGenomeSNPFrequencyPopulation) obj;
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
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (totalAlleleCount == null) {
            if (other.totalAlleleCount != null)
                return false;
        } else if (!totalAlleleCount.equals(other.totalAlleleCount))
            return false;
        return true;
    }

}

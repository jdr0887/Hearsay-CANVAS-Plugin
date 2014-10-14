package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MaxFreqPK implements Serializable {

    private static final long serialVersionUID = -3316429320541124734L;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "gen1000_version")
    private Integer gen1000Version;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @Column(name = "source")
    private String source;

    public MaxFreqPK() {
        super();
    }

    public Long getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(Long locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Integer getGen1000Version() {
        return gen1000Version;
    }

    public void setGen1000Version(Integer gen1000Version) {
        this.gen1000Version = gen1000Version;
    }

    public Float getMaxAlleleFreq() {
        return maxAlleleFreq;
    }

    public void setMaxAlleleFreq(Float maxAlleleFreq) {
        this.maxAlleleFreq = maxAlleleFreq;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("MaxFreqPK [locationVariant=%s, gen1000Version=%s, maxAlleleFreq=%s, source=%s]",
                locationVariant, gen1000Version, maxAlleleFreq, source);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gen1000Version == null) ? 0 : gen1000Version.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((maxAlleleFreq == null) ? 0 : maxAlleleFreq.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
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
        MaxFreqPK other = (MaxFreqPK) obj;
        if (gen1000Version == null) {
            if (other.gen1000Version != null)
                return false;
        } else if (!gen1000Version.equals(other.gen1000Version))
            return false;
        if (locationVariant == null) {
            if (other.locationVariant != null)
                return false;
        } else if (!locationVariant.equals(other.locationVariant))
            return false;
        if (maxAlleleFreq == null) {
            if (other.maxAlleleFreq != null)
                return false;
        } else if (!maxAlleleFreq.equals(other.maxAlleleFreq))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }

}

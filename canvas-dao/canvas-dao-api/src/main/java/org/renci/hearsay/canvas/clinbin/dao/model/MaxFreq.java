package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "max_freq")
public class MaxFreq implements Persistable {

    private static final long serialVersionUID = -2401541418491242656L;

    @EmbeddedId
    private MaxFreqPK key;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @MapsId
    @Column(name = "gen1000_version")
    private Integer gen1000Version;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @ManyToOne
    @JoinColumn(name = "source")
    private MaxFreqSource source;

    public MaxFreq() {
        super();
    }

    public MaxFreqPK getKey() {
        return key;
    }

    public void setKey(MaxFreqPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
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

    public MaxFreqSource getSource() {
        return source;
    }

    public void setSource(MaxFreqSource source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("MaxFreq [gen1000Version=%s, maxAlleleFreq=%s]", gen1000Version, maxAlleleFreq);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gen1000Version == null) ? 0 : gen1000Version.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((maxAlleleFreq == null) ? 0 : maxAlleleFreq.hashCode());
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
        MaxFreq other = (MaxFreq) obj;
        if (gen1000Version == null) {
            if (other.gen1000Version != null)
                return false;
        } else if (!gen1000Version.equals(other.gen1000Version))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (maxAlleleFreq == null) {
            if (other.maxAlleleFreq != null)
                return false;
        } else if (!maxAlleleFreq.equals(other.maxAlleleFreq))
            return false;
        return true;
    }

}

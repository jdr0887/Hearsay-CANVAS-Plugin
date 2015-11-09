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

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @Column(name = "max_allele_freq")
    private Double maxAlleleFreq;

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

    public Double getMaxAlleleFreq() {
        return maxAlleleFreq;
    }

    public void setMaxAlleleFreq(Double maxAlleleFreq) {
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
        return String.format("MaxFreq [maxAlleleFreq=%s]", maxAlleleFreq);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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

package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "unimportant_feature")
public class UnimportantFeature implements Persistable {

    private static final long serialVersionUID = 1561860572156487981L;

    @EmbeddedId
    private UnimportantFeaturePK key;

    @MapsId
    @Lob
    @Column(name = "transcr")
    private String transcr;

    @MapsId
    @Column(name = "feature_id")
    private Integer featureId;

    @Column(name = "count")
    private Integer count;

    public UnimportantFeature() {
        super();
    }

    public UnimportantFeaturePK getKey() {
        return key;
    }

    public void setKey(UnimportantFeaturePK key) {
        this.key = key;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("UnimportantFeature [transcr=%s, featureId=%s, count=%s]", transcr, featureId, count);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((transcr == null) ? 0 : transcr.hashCode());
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
        UnimportantFeature other = (UnimportantFeature) obj;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        if (featureId == null) {
            if (other.featureId != null)
                return false;
        } else if (!featureId.equals(other.featureId))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

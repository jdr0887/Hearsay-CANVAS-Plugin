package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "refseq", name = "feature")
public class Feature implements Persistable {

    private static final long serialVersionUID = -3021365878092927482L;

    @Id
    @Column(name = "refseq_feature_id")
    private Integer refseqFeatureId;

    @ManyToOne
    @JoinColumn(name = "feature_type_type_name")
    private FeatureTypes featureTypes;

    @Column(name = "refseq_ver")
    private String refseqVer;

    @Column(name = "note", length = 1023)
    private String note;

    @ManyToOne
    @JoinColumn(name = "loc_region_group_id")
    private RegionGroup regionGroup;

    public Feature() {
        super();
    }

    public Integer getRefseqFeatureId() {
        return refseqFeatureId;
    }

    public void setRefseqFeatureId(Integer refseqFeatureId) {
        this.refseqFeatureId = refseqFeatureId;
    }

    public FeatureTypes getFeatureTypes() {
        return featureTypes;
    }

    public void setFeatureTypes(FeatureTypes featureTypes) {
        this.featureTypes = featureTypes;
    }

    public String getRefseqVer() {
        return refseqVer;
    }

    public void setRefseqVer(String refseqVer) {
        this.refseqVer = refseqVer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RegionGroup getRegionGroup() {
        return regionGroup;
    }

    public void setRegionGroup(RegionGroup regionGroup) {
        this.regionGroup = regionGroup;
    }

    @Override
    public String toString() {
        return String.format("Feature [refseqFeatureId=%s, refseqVer=%s, note=%s]", refseqFeatureId, refseqVer, note);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((refseqFeatureId == null) ? 0 : refseqFeatureId.hashCode());
        result = prime * result + ((refseqVer == null) ? 0 : refseqVer.hashCode());
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
        Feature other = (Feature) obj;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        if (refseqFeatureId == null) {
            if (other.refseqFeatureId != null)
                return false;
        } else if (!refseqFeatureId.equals(other.refseqFeatureId))
            return false;
        if (refseqVer == null) {
            if (other.refseqVer != null)
                return false;
        } else if (!refseqVer.equals(other.refseqVer))
            return false;
        return true;
    }

}

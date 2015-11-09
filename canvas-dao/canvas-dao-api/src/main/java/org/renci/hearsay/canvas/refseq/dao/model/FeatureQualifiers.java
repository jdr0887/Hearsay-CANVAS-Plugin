package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "feature_qualifiers")
public class FeatureQualifiers {

    @Id
    @Column(name = "refseq_feature_id")
    private Integer refseqFeatureId;

    @Id
    @Column(name = "key")
    private String key;

    @Id
    @Column(name = "value")
    private String value;

    public FeatureQualifiers() {
        super();
    }

    public Integer getRefseqFeatureId() {
        return refseqFeatureId;
    }

    public void setRefseqFeatureId(Integer refseqFeatureId) {
        this.refseqFeatureId = refseqFeatureId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "feature_qualifiers")
public class FeatureQualifiers {

    @Column(name = "refseq_feature_id")
    private Integer refseqFeatureId;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    public FeatureQualifiers() {
        super();
    }

}

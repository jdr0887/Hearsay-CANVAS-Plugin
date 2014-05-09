package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeatureTypes")
@Entity
@Table(schema = "refseq", name = "feature_types")
public class FeatureTypes {

    @Column(name = "type_name", length = 31)
    private String typeName;

    public FeatureTypes() {
        super();
    }

}

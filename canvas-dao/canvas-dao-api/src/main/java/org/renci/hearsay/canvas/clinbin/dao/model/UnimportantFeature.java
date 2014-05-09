package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnimportantFeature")
@XmlRootElement(name = "unimportantFeature")
@Entity
@Table(name = "unimportant_feature")
public class UnimportantFeature {

    @Lob
    @Column(name = "transcr")
    private String transcr;

    @Column(name = "feature_id")
    private Integer featureId;

    @Column(name = "count")
    private Integer count;

    public UnimportantFeature() {
        super();
    }

}

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
@XmlType(name = "DiseaseClass")
@XmlRootElement(name = "diseaseClass")
@Entity
@Table(name = "disease_class")
public class DiseaseClass {

    @Column(name = "class_id")
    private Integer classId;

    @Lob
    @Column(name = "class_name")
    private String className;

    public DiseaseClass() {
        super();
    }

}

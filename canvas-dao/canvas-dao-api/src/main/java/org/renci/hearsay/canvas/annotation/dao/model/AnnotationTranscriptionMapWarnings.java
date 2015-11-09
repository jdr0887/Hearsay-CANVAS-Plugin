package org.renci.hearsay.canvas.annotation.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@Entity
@Table(schema = "annot", name = "transcr_map_warnings")
public class AnnotationTranscriptionMapWarnings {

    @Id
    @Column(name = "warning_name")
    private String warningName;

    public AnnotationTranscriptionMapWarnings() {
        super();
    }

}

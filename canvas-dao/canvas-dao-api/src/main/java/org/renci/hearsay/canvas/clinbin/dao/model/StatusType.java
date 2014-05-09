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
@XmlType(name = "StatusType")
@XmlRootElement(name = "statusType")
@Entity
@Table(name = "status_type")
public class StatusType {

    @Lob
    @Column(name = "status")
    private String status;

    public StatusType() {
        super();
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalBinType")
@XmlRootElement(name = "incidentalBinType")
@Entity
@Table(name = "incidental_bin_type")
public class IncidentalBinType {

    @Column(name = "incidental_bin_type_name", length = 15)
    private String incidentalBinTypeName;

    public IncidentalBinType() {
        super();
    }

}

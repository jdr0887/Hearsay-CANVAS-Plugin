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
@XmlType(name = "CarrierStatus")
@XmlRootElement(name = "carrierStatus")
@Entity
@Table(name = "carrier_status")
public class CarrierStatus {

    @Column(name = "carrier_status_id")
    private Integer carrierStatusId;

    @Lob
    @Column(name = "carrier_status_name")
    private String carrierStatusName;

    public CarrierStatus() {
        super();
    }

}

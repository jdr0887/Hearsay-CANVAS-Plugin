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
@XmlType(name = "ZygosityModeType")
@XmlRootElement(name = "zygosityModeType")
@Entity
@Table(name = "zygosity_mode_type")
public class ZygosityModeType {

    @Lob
    @Column(name = "zygosity_mode_type_name")
    private String zygosityModeTypeName;

    public ZygosityModeType() {
        super();
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DX")
@XmlRootElement(name = "dx")
@Entity
@Table(name = "dx")
public class DX {

    @Column(name = "dx_id")
    private Long dxId;

    @Column(name = "dx_name", length = 1024)
    private String dxName;

    public DX() {
        super();
    }

}

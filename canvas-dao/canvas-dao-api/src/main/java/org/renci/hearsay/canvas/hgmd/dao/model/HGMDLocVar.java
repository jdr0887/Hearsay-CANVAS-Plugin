package org.renci.hearsay.canvas.hgmd.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HgmdLocVar")
@XmlRootElement(name = "hgmdLocVar")
@Entity
@Table(name = "hgmd_loc_var")
public class HGMDLocVar {

    @Column(name = "acc_num", length = 10)
    private String accNum;

    @Column(name = "loc_var_id")
    private Integer locVarId;

    public HGMDLocVar() {
        super();
    }

}

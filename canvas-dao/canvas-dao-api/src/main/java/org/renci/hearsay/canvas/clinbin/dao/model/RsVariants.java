package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RsVariants")
@XmlRootElement(name = "rsVariants")
@Entity
@Table(name = "rs_variants")
public class RsVariants {

    @Column(name = "bin_id", length = 15)
    private String binId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "loc_var_id")
    private Integer locVarId;

    public RsVariants() {
        super();
    }

}

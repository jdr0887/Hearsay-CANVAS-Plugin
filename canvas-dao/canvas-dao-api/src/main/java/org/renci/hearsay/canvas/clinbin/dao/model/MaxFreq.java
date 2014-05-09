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
@XmlType(name = "MaxFreq")
@XmlRootElement(name = "maxFreq")
@Entity
@Table(name = "max_freq")
public class MaxFreq {

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Column(name = "gen1000_version")
    private Integer gen1000Version;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @Lob
    @Column(name = "source")
    private String source;

    public MaxFreq() {
        super();
    }

}

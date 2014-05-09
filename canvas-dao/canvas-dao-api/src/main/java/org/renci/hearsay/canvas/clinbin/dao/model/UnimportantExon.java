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
@XmlType(name = "UnimportantExon")
@XmlRootElement(name = "unimportantExon")
@Entity
@Table(name = "unimportant_exon")
public class UnimportantExon {

    @Lob
    @Column(name = "transcr")
    private String transcr;

    @Column(name = "noncan_exon")
    private Integer noncanExon;

    @Column(name = "count")
    private Integer count;

    public UnimportantExon() {
        super();
    }

}

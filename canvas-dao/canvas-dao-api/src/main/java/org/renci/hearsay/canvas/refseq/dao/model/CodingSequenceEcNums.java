package org.renci.hearsay.canvas.refseq.dao.model;

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
@Table(schema = "refseq", name = "cds_ec_nums")
public class CodingSequenceEcNums {

    @Column(name = "refseq_cds_id", nullable = false)
    private RefSeqCodingSequence cds;

    @Id
    @Column(name = "ec_num", length = 31)
    private String ecNum;

    public CodingSequenceEcNums() {
        super();
    }

}

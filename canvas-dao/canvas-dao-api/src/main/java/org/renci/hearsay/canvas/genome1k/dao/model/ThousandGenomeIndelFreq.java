package org.renci.hearsay.canvas.genome1k.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@Entity
@Table(schema = "gen1000", name = "indel_freq")
public class ThousandGenomeIndelFreq {

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Column(name = "gen1000_version")
    private Integer gen1000Version;

    @Column(name = "population", length = 5)
    private String population;

    @Column(name = "allele_freq")
    private Float alleleFreq;

    @Column(name = "num_samples")
    private Integer numSamples;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "homopolymer_count")
    private Integer homopolymerCount;

    @Column(name = "forward_var_count")
    private Integer forwardVarCount;

    @Column(name = "reverse_var_count")
    private Integer reverseVarCount;

    public ThousandGenomeIndelFreq() {
        super();
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalBinGene")
@XmlRootElement(name = "incidentalBinGene")
@Entity
@Table(name = "incidental_bin_gene")
public class IncidentalBinGene {

    @Column(name = "incidental_bin_id")
    private Integer incidentalBinId;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "disease")
    private String disease;

    public IncidentalBinGene() {
        super();
    }

}

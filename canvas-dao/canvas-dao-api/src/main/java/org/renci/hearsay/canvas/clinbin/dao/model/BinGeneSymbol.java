package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinGeneSymbol")
@XmlRootElement(name = "binGeneSymbol")
@Entity
@Table(name = "bin_gene_symbol")
public class BinGeneSymbol {

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "version_id")
    private Integer versionId;

    @Column(name = "bin_id")
    private Integer binId;

    @Column(name = "gene_type_id")
    private Integer geneTypeId;

    @Column(name = "gene_symbol")
    private String geneSymbol;

    public BinGeneSymbol() {
        super();
    }

}

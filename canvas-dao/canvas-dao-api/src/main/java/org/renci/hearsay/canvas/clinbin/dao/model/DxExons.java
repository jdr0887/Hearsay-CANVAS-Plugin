package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DxExons")
@XmlRootElement(name = "dxExons")
@Entity
@Table(name = "dx_exons")
public class DxExons {

    @Column(name = "dx_exon_id")
    private Long dxExonId;

    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "transcr", length = 100)
    private String transcr;

    @Column(name = "exon")
    private Integer exon;

    @Column(name = "chromosome", length = 100)
    private String chromosome;

    @Column(name = "interval_start")
    private Integer intervalStart;

    @Column(name = "interval_end")
    private Integer intervalEnd;

    public DxExons() {
        super();
    }

}

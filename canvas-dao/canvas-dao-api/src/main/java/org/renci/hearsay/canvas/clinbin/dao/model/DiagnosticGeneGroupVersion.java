package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticGeneGroupVersion")
@XmlRootElement(name = "diagnosticGeneGroupVersion")
@Entity
@Table(name = "diagnostic_gene_group_version")
public class DiagnosticGeneGroupVersion {

    @Column(name = "dbin_group_version")
    private Integer dbinGroupVersion;

    @Column(name = "dx_id")
    private Integer dxId;

    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    public DiagnosticGeneGroupVersion() {
        super();
    }

}

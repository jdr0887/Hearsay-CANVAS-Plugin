package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticResultVersion")
@XmlRootElement(name = "diagnosticResultVersion")
@Entity
@Table(name = "diagnostic_result_version")
public class DiagnosticResultVersion {

    @Column(name = "diagnostic_result_version")
    private Integer diagnosticResultVersion;

    @Column(name = "ref_id")
    private Integer refId;

    @Column(name = "refseq_version")
    private Integer refseqVersion;

    @Column(name = "hgmd_version")
    private Integer hgmdVersion;

    @Column(name = "gen1000_snp_version")
    private Integer gen1000SnpVersion;

    @Column(name = "gen1000_indel_version")
    private Integer gen1000IndelVersion;

    @Column(name = "dbin_group_version")
    private Integer dbinGroupVersion;

    @Column(name = "algorithm_version")
    private Integer algorithmVersion;

    public DiagnosticResultVersion() {
        super();
    }

}

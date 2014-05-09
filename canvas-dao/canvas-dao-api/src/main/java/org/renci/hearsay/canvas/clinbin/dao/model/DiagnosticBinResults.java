package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticBinResults")
@XmlRootElement(name = "diagnosticBinResults")
@Entity
@Table(name = "diagnostic_bin_results")
public class DiagnosticBinResults {

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Column(name = "bin_timestamp")
    private Date binTimestamp;

    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    @Column(name = "dx_id")
    private Integer dxId;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "transcr", length = 31)
    private String transcr;

    public DiagnosticBinResults() {
        super();
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalBinResults")
@XmlRootElement(name = "incidentalBinResults")
@Entity
@Table(name = "incidental_bin_results")
public class IncidentalBinResults {

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Column(name = "bin_timestamp")
    private Date binTimestamp;

    @Column(name = "incidental_list_version")
    private Integer incidentalListVersion;

    @Column(name = "bin_name", length = 1023)
    private String binName;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "disease")
    private String disease;

    @Lob
    @Column(name = "zygosity_mode")
    private String zygosityMode;

    @Column(name = "incidental_bin", length = 15)
    private String incidentalBin;

    @Column(name = "transcr", length = 31)
    private String transcr;

    public IncidentalBinResults() {
        super();
    }

}

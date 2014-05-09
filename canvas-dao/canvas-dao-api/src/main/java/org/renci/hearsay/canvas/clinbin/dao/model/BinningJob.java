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
@XmlType(name = "BinningJob")
@XmlRootElement(name = "binningJob")
@Entity
@Table(name = "binning_job")
public class BinningJob {

    @Column(name = "binning_job_id")
    private Long binningJobId;

    @Column(name = "incidental_list_version")
    private Integer incidentalListVersion;

    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    @Column(name = "participant", length = 50)
    private String participant;

    @Column(name = "bin_start")
    private Date binStart;

    @Column(name = "bin_stop")
    private Date binStop;

    @Column(name = "select_bin_1")
    private Boolean selectBin1;

    @Column(name = "select_bin_2a")
    private Boolean selectBin2a;

    @Column(name = "select_bin_2b")
    private Boolean selectBin2b;

    @Column(name = "select_bin_2c")
    private Boolean selectBin2c;

    @Column(name = "dx_id")
    private Integer dxId;

    @Lob
    @Column(name = "status")
    private String status;

    @Column(name = "failure_message", length = 1023)
    private String failureMessage;

    @Column(name = "select_carrier")
    private Boolean selectCarrier;

    @Lob
    @Column(name = "vcf_file")
    private String vcfFile;

    public BinningJob() {
        super();
    }

}

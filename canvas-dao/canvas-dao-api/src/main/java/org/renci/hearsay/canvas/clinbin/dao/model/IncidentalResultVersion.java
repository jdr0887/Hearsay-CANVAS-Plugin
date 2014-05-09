package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalResultVersion")
@XmlRootElement(name = "incidentalResultVersion")
@Entity
@Table(name = "incidental_result_version")
public class IncidentalResultVersion {

    @Column(name = "binning_result_version")
    private Integer binningResultVersion;

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

    @Column(name = "ibin_group_version")
    private Integer ibinGroupVersion;

    @Column(name = "binning_algorithm_version")
    private Integer binningAlgorithmVersion;

    public IncidentalResultVersion() {
        super();
    }

}

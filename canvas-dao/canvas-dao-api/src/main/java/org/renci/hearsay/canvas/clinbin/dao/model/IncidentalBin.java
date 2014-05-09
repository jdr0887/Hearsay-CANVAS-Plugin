package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalBin")
@XmlRootElement(name = "incidentalBin")
@Entity
@Table(name = "incidental_bin")
public class IncidentalBin {

    @Column(name = "incidental_bin_id")
    private Long incidentalBinId;

    @Column(name = "incidental_list_version")
    private Integer incidentalListVersion;

    @Column(name = "bin_name", length = 1024)
    private String binName;

    @Lob
    @Column(name = "zygosity_mode")
    private String zygosityMode;

    @Column(name = "incidental_bin", length = 15)
    private String incidentalBin;

    public IncidentalBin() {
        super();
    }

}

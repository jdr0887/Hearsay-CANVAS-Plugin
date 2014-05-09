package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncidentalBinGroupVersion")
@XmlRootElement(name = "incidentalBinGroupVersion")
@Entity
@Table(name = "incidental_bin_group_version")
public class IncidentalBinGroupVersion {

    @Column(name = "ibin_group_version")
    private Integer ibinGroupVersion;

    @Column(name = "incidental_bin_id")
    private Integer incidentalBinId;

    public IncidentalBinGroupVersion() {
        super();
    }

}

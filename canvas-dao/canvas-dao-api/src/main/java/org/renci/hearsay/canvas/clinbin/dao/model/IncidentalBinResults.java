package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGene;
import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "incidental_bin_results")
public class IncidentalBinResults implements Persistable {

    private static final long serialVersionUID = -5498414203626044918L;

    @EmbeddedId
    private IncidentalBinResultsPK key;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @Column(name = "bin_timestamp")
    private Date binTimestamp;

    @MapsId
    @Column(name = "bin_version")
    private Integer binVersion;

    @Column(name = "bin_name", length = 1023)
    private String binName;

    @ManyToOne
    @JoinColumn(name = "gene_id")
    private AnnotationGene gene;

    @Column(name = "disease")
    private String disease;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "zygosity_mode")
    private ZygosityModeType zygosityMode;

    @Column(name = "incidental_bin", length = 15)
    private String incidentalBin;

    @MapsId
    @Column(name = "transcr", length = 31)
    private String transcr;

    public IncidentalBinResults() {
        super();
    }

    public IncidentalBinResultsPK getKey() {
        return key;
    }

    public void setKey(IncidentalBinResultsPK key) {
        this.key = key;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Date getBinTimestamp() {
        return binTimestamp;
    }

    public void setBinTimestamp(Date binTimestamp) {
        this.binTimestamp = binTimestamp;
    }

    public Integer getBinVersion() {
        return binVersion;
    }

    public void setBinVersion(Integer binVersion) {
        this.binVersion = binVersion;
    }

    public String getBinName() {
        return binName;
    }

    public void setBinName(String binName) {
        this.binName = binName;
    }

    public AnnotationGene getGene() {
        return gene;
    }

    public void setGene(AnnotationGene gene) {
        this.gene = gene;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public ZygosityModeType getZygosityMode() {
        return zygosityMode;
    }

    public void setZygosityMode(ZygosityModeType zygosityMode) {
        this.zygosityMode = zygosityMode;
    }

    public String getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(String incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    @Override
    public String toString() {
        return String
                .format("IncidentalBinResults [binTimestamp=%s, incidentalListVersion=%s, binName=%s, disease=%s, incidentalBin=%s, transcr=%s]",
                        binTimestamp, binVersion, binName, disease, incidentalBin, transcr);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binName == null) ? 0 : binName.hashCode());
        result = prime * result + ((binTimestamp == null) ? 0 : binTimestamp.hashCode());
        result = prime * result + ((disease == null) ? 0 : disease.hashCode());
        result = prime * result + ((incidentalBin == null) ? 0 : incidentalBin.hashCode());
        result = prime * result + ((binVersion == null) ? 0 : binVersion.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((transcr == null) ? 0 : transcr.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IncidentalBinResults other = (IncidentalBinResults) obj;
        if (binName == null) {
            if (other.binName != null)
                return false;
        } else if (!binName.equals(other.binName))
            return false;
        if (binTimestamp == null) {
            if (other.binTimestamp != null)
                return false;
        } else if (!binTimestamp.equals(other.binTimestamp))
            return false;
        if (disease == null) {
            if (other.disease != null)
                return false;
        } else if (!disease.equals(other.disease))
            return false;
        if (incidentalBin == null) {
            if (other.incidentalBin != null)
                return false;
        } else if (!incidentalBin.equals(other.incidentalBin))
            return false;
        if (binVersion == null) {
            if (other.binVersion != null)
                return false;
        } else if (!binVersion.equals(other.binVersion))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

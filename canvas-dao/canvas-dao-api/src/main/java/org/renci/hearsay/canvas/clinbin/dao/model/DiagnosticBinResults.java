package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "diagnostic_bin_results")
public class DiagnosticBinResults implements Persistable {

    private static final long serialVersionUID = -4066677132203326435L;

    @EmbeddedId
    private DiagnosticBinResultsPK key;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @MapsId
    @Column(name = "bin_timestamp")
    private Date binTimestamp;

    @MapsId
    @Column(name = "bin_version")
    private Integer binVersion;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "dx_id")
    private DX dx;

    @MapsId
    @Column(name = "gene_id")
    private Integer geneId;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "class_id")
    private DiseaseClass diseaseClass;

    @MapsId
    @Column(name = "transcr", length = 31)
    private String transcr;

    @MapsId
    @Column(name = "mapnum")
    private Integer mapnum;

    public DiagnosticBinResults() {
        super();
    }

    public DiagnosticBinResultsPK getKey() {
        return key;
    }

    public void setKey(DiagnosticBinResultsPK key) {
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

    public DX getDx() {
        return dx;
    }

    public void setDx(DX dx) {
        this.dx = dx;
    }

    public Integer getGeneId() {
        return geneId;
    }

    public void setGeneId(Integer geneId) {
        this.geneId = geneId;
    }

    public DiseaseClass getDiseaseClass() {
        return diseaseClass;
    }

    public void setDiseaseClass(DiseaseClass diseaseClass) {
        this.diseaseClass = diseaseClass;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    public Integer getMapnum() {
        return mapnum;
    }

    public void setMapnum(Integer mapnum) {
        this.mapnum = mapnum;
    }

    @Override
    public String toString() {
        return String.format("DiagnosticBinResults [binTimestamp=%s, binVersion=%s, geneId=%s, transcr=%s, mapnum=%s]",
                binTimestamp, binVersion, geneId, transcr, mapnum);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binTimestamp == null) ? 0 : binTimestamp.hashCode());
        result = prime * result + ((binVersion == null) ? 0 : binVersion.hashCode());
        result = prime * result + ((geneId == null) ? 0 : geneId.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((mapnum == null) ? 0 : mapnum.hashCode());
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
        DiagnosticBinResults other = (DiagnosticBinResults) obj;
        if (binTimestamp == null) {
            if (other.binTimestamp != null)
                return false;
        } else if (!binTimestamp.equals(other.binTimestamp))
            return false;
        if (binVersion == null) {
            if (other.binVersion != null)
                return false;
        } else if (!binVersion.equals(other.binVersion))
            return false;
        if (geneId == null) {
            if (other.geneId != null)
                return false;
        } else if (!geneId.equals(other.geneId))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (mapnum == null) {
            if (other.mapnum != null)
                return false;
        } else if (!mapnum.equals(other.mapnum))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

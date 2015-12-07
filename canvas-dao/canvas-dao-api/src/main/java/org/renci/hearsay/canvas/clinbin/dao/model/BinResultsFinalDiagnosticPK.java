package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BinResultsFinalDiagnosticPK implements Serializable {

    private static final long serialVersionUID = 8670971325600111390L;

    @Column(name = "participant", length = 50)
    private String participant;

    @Column(name = "dx_id")
    private Integer dx;

    @Column(name = "diagnostic_result_version")
    private Integer diagnosticResultVersion;

    @Column(name = "asm_id")
    private Integer assembly;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "mapnum")
    private Integer mapnum;

    @Column(name = "transcr")
    private String transcr;

    public BinResultsFinalDiagnosticPK() {
        super();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Integer getDx() {
        return dx;
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }

    public Integer getDiagnosticResultVersion() {
        return diagnosticResultVersion;
    }

    public void setDiagnosticResultVersion(Integer diagnosticResultVersion) {
        this.diagnosticResultVersion = diagnosticResultVersion;
    }

    public Integer getAssembly() {
        return assembly;
    }

    public void setAssembly(Integer assembly) {
        this.assembly = assembly;
    }

    public Long getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(Long locationVariant) {
        this.locationVariant = locationVariant;
    }

    public Integer getMapnum() {
        return mapnum;
    }

    public void setMapnum(Integer mapnum) {
        this.mapnum = mapnum;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    @Override
    public String toString() {
        return String.format(
                "BinResultsFinalDiagnosticPK [participant=%s, dx=%s, diagnosticResultVersion=%s, assembly=%s, locationVariant=%s, mapnum=%s, transcr=%s]",
                participant, dx, diagnosticResultVersion, assembly, locationVariant, mapnum, transcr);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assembly == null) ? 0 : assembly.hashCode());
        result = prime * result + ((diagnosticResultVersion == null) ? 0 : diagnosticResultVersion.hashCode());
        result = prime * result + ((dx == null) ? 0 : dx.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((mapnum == null) ? 0 : mapnum.hashCode());
        result = prime * result + ((participant == null) ? 0 : participant.hashCode());
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
        BinResultsFinalDiagnosticPK other = (BinResultsFinalDiagnosticPK) obj;
        if (assembly == null) {
            if (other.assembly != null)
                return false;
        } else if (!assembly.equals(other.assembly))
            return false;
        if (diagnosticResultVersion == null) {
            if (other.diagnosticResultVersion != null)
                return false;
        } else if (!diagnosticResultVersion.equals(other.diagnosticResultVersion))
            return false;
        if (dx == null) {
            if (other.dx != null)
                return false;
        } else if (!dx.equals(other.dx))
            return false;
        if (locationVariant == null) {
            if (other.locationVariant != null)
                return false;
        } else if (!locationVariant.equals(other.locationVariant))
            return false;
        if (mapnum == null) {
            if (other.mapnum != null)
                return false;
        } else if (!mapnum.equals(other.mapnum))
            return false;
        if (participant == null) {
            if (other.participant != null)
                return false;
        } else if (!participant.equals(other.participant))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BinResultsFinalIncidentalPK implements Serializable {

    private static final long serialVersionUID = -475726325800038187L;

    @Column(name = "participant")
    private String participant;

    @Column(name = "incidental_bin")
    private String incidentalBin;

    @Column(name = "incidental_result_version")
    private Integer incidentalResultVersion;

    @Column(name = "asm_id")
    private Long asm;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "mapnum")
    private Integer mapnum;

    @Column(name = "transcr", length = 31)
    private String transcr;

    public BinResultsFinalIncidentalPK() {
        super();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(String incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    public Integer getIncidentalResultVersion() {
        return incidentalResultVersion;
    }

    public void setIncidentalResultVersion(Integer incidentalResultVersion) {
        this.incidentalResultVersion = incidentalResultVersion;
    }

    public Long getAsm() {
        return asm;
    }

    public void setAsm(Long asm) {
        this.asm = asm;
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
        return String
                .format("BinResultsFinalIncidentalPK [participant=%s, incidentalBin=%s, incidentalResultVersion=%s, asm=%s, locationVariant=%s, mapnum=%s, transcr=%s]",
                        participant, incidentalBin, incidentalResultVersion, asm, locationVariant, mapnum, transcr);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asm == null) ? 0 : asm.hashCode());
        result = prime * result + ((incidentalBin == null) ? 0 : incidentalBin.hashCode());
        result = prime * result + ((incidentalResultVersion == null) ? 0 : incidentalResultVersion.hashCode());
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
        BinResultsFinalIncidentalPK other = (BinResultsFinalIncidentalPK) obj;
        if (asm == null) {
            if (other.asm != null)
                return false;
        } else if (!asm.equals(other.asm))
            return false;
        if (incidentalBin == null) {
            if (other.incidentalBin != null)
                return false;
        } else if (!incidentalBin.equals(other.incidentalBin))
            return false;
        if (incidentalResultVersion == null) {
            if (other.incidentalResultVersion != null)
                return false;
        } else if (!incidentalResultVersion.equals(other.incidentalResultVersion))
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

package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BinResultsFinalRiskXPK implements Serializable {

    private static final long serialVersionUID = -4432334844095779990L;

    @Column(name = "participant")
    private String participant;

    @Column(name = "incidental_bin_id")
    private Integer incidentalBin;

    @Column(name = "incidental_result_version")
    private Integer incidentalResultVersion;

    @Column(name = "asm_id")
    private Integer assembly;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "haplotype_id")
    private Integer haplotypeId;

    @Column(name = "phenotype_id")
    private Integer phenotypeX;

    public BinResultsFinalRiskXPK() {
        super();
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Integer getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(Integer incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    public Integer getIncidentalResultVersion() {
        return incidentalResultVersion;
    }

    public void setIncidentalResultVersion(Integer incidentalResultVersion) {
        this.incidentalResultVersion = incidentalResultVersion;
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

    public Integer getHaplotypeId() {
        return haplotypeId;
    }

    public void setHaplotypeId(Integer haplotypeId) {
        this.haplotypeId = haplotypeId;
    }

    public Integer getPhenotypeX() {
        return phenotypeX;
    }

    public void setPhenotypeX(Integer phenotypeX) {
        this.phenotypeX = phenotypeX;
    }

    @Override
    public String toString() {
        return String
                .format("BinResultsFinalRiskXPK [participant=%s, incidentalBin=%s, incidentalResultVersion=%s, assembly=%s, locationVariant=%s, haplotypeId=%s, phenotypeX=%s]",
                        participant, incidentalBin, incidentalResultVersion, assembly, locationVariant, haplotypeId,
                        phenotypeX);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assembly == null) ? 0 : assembly.hashCode());
        result = prime * result + ((haplotypeId == null) ? 0 : haplotypeId.hashCode());
        result = prime * result + ((incidentalBin == null) ? 0 : incidentalBin.hashCode());
        result = prime * result + ((incidentalResultVersion == null) ? 0 : incidentalResultVersion.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((participant == null) ? 0 : participant.hashCode());
        result = prime * result + ((phenotypeX == null) ? 0 : phenotypeX.hashCode());
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
        BinResultsFinalRiskXPK other = (BinResultsFinalRiskXPK) obj;
        if (assembly == null) {
            if (other.assembly != null)
                return false;
        } else if (!assembly.equals(other.assembly))
            return false;
        if (haplotypeId == null) {
            if (other.haplotypeId != null)
                return false;
        } else if (!haplotypeId.equals(other.haplotypeId))
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
        if (participant == null) {
            if (other.participant != null)
                return false;
        } else if (!participant.equals(other.participant))
            return false;
        if (phenotypeX == null) {
            if (other.phenotypeX != null)
                return false;
        } else if (!phenotypeX.equals(other.phenotypeX))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IncidentalBinGenePK implements Serializable {

    private static final long serialVersionUID = 5774709424660804712L;

    @Column(name = "incidental_bin_id")
    private Long incidentalBin;

    @Column(name = "gene_id")
    private Long gene;

    @Column(name = "disease")
    private String disease;

    public IncidentalBinGenePK() {
        super();
    }

    public Long getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(Long incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    public Long getGene() {
        return gene;
    }

    public void setGene(Long gene) {
        this.gene = gene;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return String.format("IncidentalBinGenePK [incidentalBin=%s, gene=%s, disease=%s]", incidentalBin, gene,
                disease);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((disease == null) ? 0 : disease.hashCode());
        result = prime * result + ((gene == null) ? 0 : gene.hashCode());
        result = prime * result + ((incidentalBin == null) ? 0 : incidentalBin.hashCode());
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
        IncidentalBinGenePK other = (IncidentalBinGenePK) obj;
        if (disease == null) {
            if (other.disease != null)
                return false;
        } else if (!disease.equals(other.disease))
            return false;
        if (gene == null) {
            if (other.gene != null)
                return false;
        } else if (!gene.equals(other.gene))
            return false;
        if (incidentalBin == null) {
            if (other.incidentalBin != null)
                return false;
        } else if (!incidentalBin.equals(other.incidentalBin))
            return false;
        return true;
    }

}

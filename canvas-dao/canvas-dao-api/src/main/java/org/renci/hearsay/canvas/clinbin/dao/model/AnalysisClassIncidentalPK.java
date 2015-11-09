package org.renci.hearsay.canvas.clinbin.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AnalysisClassIncidentalPK implements Serializable {

    private static final long serialVersionUID = 7996961551667362972L;

    @Column(name = "analysis_class_incidental_id")
    private Integer id;

    @Column(name = "selected_class")
    private String selectedClass;

    @Column(name = "select_class_descr")
    private String selectClassDescr;

    @Column(name = "loc_var_id")
    private Long locationVariant;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "hgnc_gene")
    private String hgncGene;

    @Column(name = "disease")
    private String disease;

    @Column(name = "bin")
    private String bin;

    public AnalysisClassIncidentalPK() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }

    public String getSelectClassDescr() {
        return selectClassDescr;
    }

    public void setSelectClassDescr(String selectClassDescr) {
        this.selectClassDescr = selectClassDescr;
    }

    public Long getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(Long locationVariant) {
        this.locationVariant = locationVariant;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getHgncGene() {
        return hgncGene;
    }

    public void setHgncGene(String hgncGene) {
        this.hgncGene = hgncGene;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    @Override
    public String toString() {
        return String
                .format("AnalysisClassIncidentalPK [id=%s, selectedClass=%s, selectClassDescr=%s, locationVariant=%s, userName=%s, timestamp=%s, hgncGene=%s, disease=%s, bin=%s]",
                        id, selectedClass, selectClassDescr, locationVariant, userName, timestamp, hgncGene, disease,
                        bin);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bin == null) ? 0 : bin.hashCode());
        result = prime * result + ((disease == null) ? 0 : disease.hashCode());
        result = prime * result + ((hgncGene == null) ? 0 : hgncGene.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((locationVariant == null) ? 0 : locationVariant.hashCode());
        result = prime * result + ((selectClassDescr == null) ? 0 : selectClassDescr.hashCode());
        result = prime * result + ((selectedClass == null) ? 0 : selectedClass.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        AnalysisClassIncidentalPK other = (AnalysisClassIncidentalPK) obj;
        if (bin == null) {
            if (other.bin != null)
                return false;
        } else if (!bin.equals(other.bin))
            return false;
        if (disease == null) {
            if (other.disease != null)
                return false;
        } else if (!disease.equals(other.disease))
            return false;
        if (hgncGene == null) {
            if (other.hgncGene != null)
                return false;
        } else if (!hgncGene.equals(other.hgncGene))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (locationVariant == null) {
            if (other.locationVariant != null)
                return false;
        } else if (!locationVariant.equals(other.locationVariant))
            return false;
        if (selectClassDescr == null) {
            if (other.selectClassDescr != null)
                return false;
        } else if (!selectClassDescr.equals(other.selectClassDescr))
            return false;
        if (selectedClass == null) {
            if (other.selectedClass != null)
                return false;
        } else if (!selectedClass.equals(other.selectedClass))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

}

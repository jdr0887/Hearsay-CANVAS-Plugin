package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "analysis_class")
public class AnalysisClass implements Persistable {

    private static final long serialVersionUID = -3824671216263174677L;

    @EmbeddedId
    private AnalysisClassPK key;

    @MapsId
    @Column(name = "analysis_class_id")
    private Long id;

    @MapsId
    @Lob
    @Column(name = "selected_class")
    private String selectedClass;

    @MapsId
    @Lob
    @Column(name = "select_class_descr")
    private String selectClassDescr;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @MapsId
    @Lob
    @Column(name = "user_name")
    private String userName;

    @MapsId
    @Column(name = "timestamp")
    private Date timestamp;

    @MapsId
    @Lob
    @Column(name = "hgnc_gene")
    private String hgncGene;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "dx_id")
    private DX dx;

    public AnalysisClass() {
        super();
    }

    public AnalysisClassPK getKey() {
        return key;
    }

    public void setKey(AnalysisClassPK key) {
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
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

    public DX getDx() {
        return dx;
    }

    public void setDx(DX dx) {
        this.dx = dx;
    }

    @Override
    public String toString() {
        return String.format(
                "AnalysisClass [id=%s, selectedClass=%s, selectClassDescr=%s, userName=%s, timestamp=%s, hgncGene=%s]",
                id, selectedClass, selectClassDescr, userName, timestamp, hgncGene);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hgncGene == null) ? 0 : hgncGene.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        AnalysisClass other = (AnalysisClass) obj;
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
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
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

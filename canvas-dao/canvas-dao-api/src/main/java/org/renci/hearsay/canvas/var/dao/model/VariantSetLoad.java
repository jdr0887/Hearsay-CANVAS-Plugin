package org.renci.hearsay.canvas.var.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "var", name = "var_set_load")
public class VariantSetLoad implements Persistable {

    private static final long serialVersionUID = -858368193464014117L;

    @Id
    @ManyToOne
    @JoinColumn(name = "var_set_id")
    private VariantSet varSet;

    @Column(name = "load_filename")
    private String loadFilename;

    @Column(name = "load_user")
    private String loadUser;

    @Column(name = "load_time_start")
    private Date loadTimeStart;

    @Column(name = "load_time_stop")
    private Date loadTimeStop;

    @Column(name = "notes", length = 1024)
    private String notes;

    @Column(name = "num_snp_rows")
    private Integer numberOfSNPRows;

    @Column(name = "num_del_rows")
    private Integer numberOfDelRows;

    @Column(name = "num_sub_rows")
    private Integer numberOfSubRows;

    @Column(name = "num_ins_rows")
    private Integer numberOfInsRows;

    @Column(name = "num_multi_rows")
    private Integer numberOfMultiRows;

    @Column(name = "num_skipped_ref_rows")
    private Integer numberOfSkippedRefRows;

    @Column(name = "num_filtered_rows")
    private Integer numberOfFilteredRows;

    @Column(name = "num_error_rows")
    private Integer numberOfErrorRows;

    public VariantSetLoad() {
        super();
    }

    public VariantSet getVarSet() {
        return varSet;
    }

    public void setVarSet(VariantSet varSet) {
        this.varSet = varSet;
    }

    public String getLoadFilename() {
        return loadFilename;
    }

    public void setLoadFilename(String loadFilename) {
        this.loadFilename = loadFilename;
    }

    public String getLoadUser() {
        return loadUser;
    }

    public void setLoadUser(String loadUser) {
        this.loadUser = loadUser;
    }

    public Date getLoadTimeStart() {
        return loadTimeStart;
    }

    public void setLoadTimeStart(Date loadTimeStart) {
        this.loadTimeStart = loadTimeStart;
    }

    public Date getLoadTimeStop() {
        return loadTimeStop;
    }

    public void setLoadTimeStop(Date loadTimeStop) {
        this.loadTimeStop = loadTimeStop;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getNumberOfSNPRows() {
        return numberOfSNPRows;
    }

    public void setNumberOfSNPRows(Integer numberOfSNPRows) {
        this.numberOfSNPRows = numberOfSNPRows;
    }

    public Integer getNumberOfDelRows() {
        return numberOfDelRows;
    }

    public void setNumberOfDelRows(Integer numberOfDelRows) {
        this.numberOfDelRows = numberOfDelRows;
    }

    public Integer getNumberOfSubRows() {
        return numberOfSubRows;
    }

    public void setNumberOfSubRows(Integer numberOfSubRows) {
        this.numberOfSubRows = numberOfSubRows;
    }

    public Integer getNumberOfInsRows() {
        return numberOfInsRows;
    }

    public void setNumberOfInsRows(Integer numberOfInsRows) {
        this.numberOfInsRows = numberOfInsRows;
    }

    public Integer getNumberOfMultiRows() {
        return numberOfMultiRows;
    }

    public void setNumberOfMultiRows(Integer numberOfMultiRows) {
        this.numberOfMultiRows = numberOfMultiRows;
    }

    public Integer getNumberOfSkippedRefRows() {
        return numberOfSkippedRefRows;
    }

    public void setNumberOfSkippedRefRows(Integer numberOfSkippedRefRows) {
        this.numberOfSkippedRefRows = numberOfSkippedRefRows;
    }

    public Integer getNumberOfFilteredRows() {
        return numberOfFilteredRows;
    }

    public void setNumberOfFilteredRows(Integer numberOfFilteredRows) {
        this.numberOfFilteredRows = numberOfFilteredRows;
    }

    public Integer getNumberOfErrorRows() {
        return numberOfErrorRows;
    }

    public void setNumberOfErrorRows(Integer numberOfErrorRows) {
        this.numberOfErrorRows = numberOfErrorRows;
    }

    @Override
    public String toString() {
        return "VariantSetLoad [varSet=" + varSet + ", loadFilename=" + loadFilename + ", loadUser=" + loadUser + ", loadTimeStart="
                + loadTimeStart + ", loadTimeStop=" + loadTimeStop + ", notes=" + notes + ", numberOfSNPRows=" + numberOfSNPRows
                + ", numberOfDelRows=" + numberOfDelRows + ", numberOfSubRows=" + numberOfSubRows + ", numberOfInsRows=" + numberOfInsRows
                + ", numberOfMultiRows=" + numberOfMultiRows + ", numberOfSkippedRefRows=" + numberOfSkippedRefRows
                + ", numberOfFilteredRows=" + numberOfFilteredRows + ", numberOfErrorRows=" + numberOfErrorRows + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((loadFilename == null) ? 0 : loadFilename.hashCode());
        result = prime * result + ((loadTimeStart == null) ? 0 : loadTimeStart.hashCode());
        result = prime * result + ((loadTimeStop == null) ? 0 : loadTimeStop.hashCode());
        result = prime * result + ((loadUser == null) ? 0 : loadUser.hashCode());
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        result = prime * result + ((numberOfDelRows == null) ? 0 : numberOfDelRows.hashCode());
        result = prime * result + ((numberOfErrorRows == null) ? 0 : numberOfErrorRows.hashCode());
        result = prime * result + ((numberOfFilteredRows == null) ? 0 : numberOfFilteredRows.hashCode());
        result = prime * result + ((numberOfInsRows == null) ? 0 : numberOfInsRows.hashCode());
        result = prime * result + ((numberOfMultiRows == null) ? 0 : numberOfMultiRows.hashCode());
        result = prime * result + ((numberOfSNPRows == null) ? 0 : numberOfSNPRows.hashCode());
        result = prime * result + ((numberOfSkippedRefRows == null) ? 0 : numberOfSkippedRefRows.hashCode());
        result = prime * result + ((numberOfSubRows == null) ? 0 : numberOfSubRows.hashCode());
        result = prime * result + ((varSet == null) ? 0 : varSet.hashCode());
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
        VariantSetLoad other = (VariantSetLoad) obj;
        if (loadFilename == null) {
            if (other.loadFilename != null)
                return false;
        } else if (!loadFilename.equals(other.loadFilename))
            return false;
        if (loadTimeStart == null) {
            if (other.loadTimeStart != null)
                return false;
        } else if (!loadTimeStart.equals(other.loadTimeStart))
            return false;
        if (loadTimeStop == null) {
            if (other.loadTimeStop != null)
                return false;
        } else if (!loadTimeStop.equals(other.loadTimeStop))
            return false;
        if (loadUser == null) {
            if (other.loadUser != null)
                return false;
        } else if (!loadUser.equals(other.loadUser))
            return false;
        if (notes == null) {
            if (other.notes != null)
                return false;
        } else if (!notes.equals(other.notes))
            return false;
        if (numberOfDelRows == null) {
            if (other.numberOfDelRows != null)
                return false;
        } else if (!numberOfDelRows.equals(other.numberOfDelRows))
            return false;
        if (numberOfErrorRows == null) {
            if (other.numberOfErrorRows != null)
                return false;
        } else if (!numberOfErrorRows.equals(other.numberOfErrorRows))
            return false;
        if (numberOfFilteredRows == null) {
            if (other.numberOfFilteredRows != null)
                return false;
        } else if (!numberOfFilteredRows.equals(other.numberOfFilteredRows))
            return false;
        if (numberOfInsRows == null) {
            if (other.numberOfInsRows != null)
                return false;
        } else if (!numberOfInsRows.equals(other.numberOfInsRows))
            return false;
        if (numberOfMultiRows == null) {
            if (other.numberOfMultiRows != null)
                return false;
        } else if (!numberOfMultiRows.equals(other.numberOfMultiRows))
            return false;
        if (numberOfSNPRows == null) {
            if (other.numberOfSNPRows != null)
                return false;
        } else if (!numberOfSNPRows.equals(other.numberOfSNPRows))
            return false;
        if (numberOfSkippedRefRows == null) {
            if (other.numberOfSkippedRefRows != null)
                return false;
        } else if (!numberOfSkippedRefRows.equals(other.numberOfSkippedRefRows))
            return false;
        if (numberOfSubRows == null) {
            if (other.numberOfSubRows != null)
                return false;
        } else if (!numberOfSubRows.equals(other.numberOfSubRows))
            return false;
        if (varSet == null) {
            if (other.varSet != null)
                return false;
        } else if (!varSet.equals(other.varSet))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "diagnostic_gene_group_version")
public class DiagnosticGeneGroupVersion implements Persistable {

    private static final long serialVersionUID = -7848676259877544883L;

    @EmbeddedId
    private DiagnosticGeneGroupVersionPK key;

    @MapsId
    @Column(name = "dbin_group_version")
    private Integer dbinGroupVersion;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "dx_id")
    private DX dx;

    @MapsId
    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    public DiagnosticGeneGroupVersion() {
        super();
    }

    public DiagnosticGeneGroupVersionPK getKey() {
        return key;
    }

    public void setKey(DiagnosticGeneGroupVersionPK key) {
        this.key = key;
    }

    public Integer getDbinGroupVersion() {
        return dbinGroupVersion;
    }

    public void setDbinGroupVersion(Integer dbinGroupVersion) {
        this.dbinGroupVersion = dbinGroupVersion;
    }

    public DX getDx() {
        return dx;
    }

    public void setDx(DX dx) {
        this.dx = dx;
    }

    public Integer getDiagnosticListVersion() {
        return diagnosticListVersion;
    }

    public void setDiagnosticListVersion(Integer diagnosticListVersion) {
        this.diagnosticListVersion = diagnosticListVersion;
    }

    @Override
    public String toString() {
        return String.format("DiagnosticGeneGroupVersion [dbinGroupVersion=%s, diagnosticListVersion=%s]",
                dbinGroupVersion, diagnosticListVersion);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dbinGroupVersion == null) ? 0 : dbinGroupVersion.hashCode());
        result = prime * result + ((diagnosticListVersion == null) ? 0 : diagnosticListVersion.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
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
        DiagnosticGeneGroupVersion other = (DiagnosticGeneGroupVersion) obj;
        if (dbinGroupVersion == null) {
            if (other.dbinGroupVersion != null)
                return false;
        } else if (!dbinGroupVersion.equals(other.dbinGroupVersion))
            return false;
        if (diagnosticListVersion == null) {
            if (other.diagnosticListVersion != null)
                return false;
        } else if (!diagnosticListVersion.equals(other.diagnosticListVersion))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.clinvar.dao.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticResultVersion;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX;

@Entity
@Table(schema = "clinvar", name = "versions")
public class Versions {

    @Id
    @Column(name = "clinvar_version_id")
    private Long id;

    @Column(name = "clinvar_xml_release")
    private String release;

    @OneToMany(mappedBy = "clinvarVersion", fetch = FetchType.LAZY)
    private Set<DiagnosticResultVersion> diagnosticResultVersions;

    @OneToMany(mappedBy = "clinvarVersion", fetch = FetchType.LAZY)
    private Set<IncidentalResultVersionX> incidentalResultVersions;

    public Versions() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Set<DiagnosticResultVersion> getDiagnosticResultVersions() {
        return diagnosticResultVersions;
    }

    public void setDiagnosticResultVersions(Set<DiagnosticResultVersion> diagnosticResultVersions) {
        this.diagnosticResultVersions = diagnosticResultVersions;
    }

    public Set<IncidentalResultVersionX> getIncidentalResultVersions() {
        return incidentalResultVersions;
    }

    public void setIncidentalResultVersions(Set<IncidentalResultVersionX> incidentalResultVersions) {
        this.incidentalResultVersions = incidentalResultVersions;
    }

    @Override
    public String toString() {
        return String.format("Versions [id=%s, release=%s]", id, release);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((release == null) ? 0 : release.hashCode());
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
        Versions other = (Versions) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (release == null) {
            if (other.release != null)
                return false;
        } else if (!release.equals(other.release))
            return false;
        return true;
    }

}

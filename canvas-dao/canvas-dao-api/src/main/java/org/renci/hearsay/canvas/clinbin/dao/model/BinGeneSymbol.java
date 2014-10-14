package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "bin_gene_symbol")
public class BinGeneSymbol implements Persistable {

    private static final long serialVersionUID = -2225220140087770254L;

    @EmbeddedId
    private BinGeneSymbolPK key;

    @MapsId
    @Column(name = "project_id")
    private Integer projectId;

    @MapsId
    @Column(name = "version_id")
    private Integer versionId;

    @MapsId
    @Column(name = "bin_id")
    private Integer binId;

    @MapsId
    @Column(name = "gene_type_id")
    private Integer geneTypeId;

    @MapsId
    @Column(name = "gene_symbol")
    private String geneSymbol;

    public BinGeneSymbol() {
        super();
    }

    public BinGeneSymbolPK getKey() {
        return key;
    }

    public void setKey(BinGeneSymbolPK key) {
        this.key = key;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getBinId() {
        return binId;
    }

    public void setBinId(Integer binId) {
        this.binId = binId;
    }

    public Integer getGeneTypeId() {
        return geneTypeId;
    }

    public void setGeneTypeId(Integer geneTypeId) {
        this.geneTypeId = geneTypeId;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    @Override
    public String toString() {
        return String.format("BinGeneSymbol [projectId=%s, versionId=%s, binId=%s, geneTypeId=%s, geneSymbol=%s]",
                projectId, versionId, binId, geneTypeId, geneSymbol);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binId == null) ? 0 : binId.hashCode());
        result = prime * result + ((geneSymbol == null) ? 0 : geneSymbol.hashCode());
        result = prime * result + ((geneTypeId == null) ? 0 : geneTypeId.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
        result = prime * result + ((versionId == null) ? 0 : versionId.hashCode());
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
        BinGeneSymbol other = (BinGeneSymbol) obj;
        if (binId == null) {
            if (other.binId != null)
                return false;
        } else if (!binId.equals(other.binId))
            return false;
        if (geneSymbol == null) {
            if (other.geneSymbol != null)
                return false;
        } else if (!geneSymbol.equals(other.geneSymbol))
            return false;
        if (geneTypeId == null) {
            if (other.geneTypeId != null)
                return false;
        } else if (!geneTypeId.equals(other.geneTypeId))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        if (versionId == null) {
            if (other.versionId != null)
                return false;
        } else if (!versionId.equals(other.versionId))
            return false;
        return true;
    }

}

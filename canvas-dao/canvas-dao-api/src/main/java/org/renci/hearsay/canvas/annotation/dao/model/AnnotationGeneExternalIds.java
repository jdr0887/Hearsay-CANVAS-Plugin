package org.renci.hearsay.canvas.annotation.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.renci.hearsay.canvas.dao.Persistable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@Entity
@Table(schema = "annot", name = "gene_external_ids")
@NamedQueries({
        @NamedQuery(name = "AnnotationGeneExternalIds.findByNamespace", query = "SELECT a FROM AnnotationGeneExternalIds a where a.namespace = :namespace"),
        @NamedQuery(name = "AnnotationGeneExternalIds.findByNamespaceAndNamespaceVersion", query = "SELECT a FROM AnnotationGeneExternalIds a where a.namespace = :namespace and a.namespaceVer = :version") })
public class AnnotationGeneExternalIds implements Persistable {

    private static final long serialVersionUID = 5179600096320755261L;

    @Id
    @Column(name = "gene_external_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gene_id")
    private AnnotationGene gene;

    @Column(name = "namespace", length = 31)
    private String namespace;

    @Column(name = "namespace_ver", length = 31)
    private String namespaceVer;

    public AnnotationGeneExternalIds() {
        super();
    }

    public AnnotationGene getGene() {
        return gene;
    }

    public void setGene(AnnotationGene gene) {
        this.gene = gene;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespaceVer() {
        return namespaceVer;
    }

    public void setNamespaceVer(String namespaceVer) {
        this.namespaceVer = namespaceVer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("AnnotationGeneExternalIds [namespace=%s, namespaceVer=%s, id=%s]", namespace,
                namespaceVer, id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gene == null) ? 0 : gene.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((namespaceVer == null) ? 0 : namespaceVer.hashCode());
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
        AnnotationGeneExternalIds other = (AnnotationGeneExternalIds) obj;
        if (gene == null) {
            if (other.gene != null)
                return false;
        } else if (!gene.equals(other.gene))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (namespace == null) {
            if (other.namespace != null)
                return false;
        } else if (!namespace.equals(other.namespace))
            return false;
        if (namespaceVer == null) {
            if (other.namespaceVer != null)
                return false;
        } else if (!namespaceVer.equals(other.namespaceVer))
            return false;
        return true;
    }

}

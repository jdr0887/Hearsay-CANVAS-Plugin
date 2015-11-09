package org.renci.hearsay.canvas.annotation.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@Entity
@Table(schema = "annot", name = "cds_external_ids")
public class AnnotationCodingSequenceExternalIds {

    @Id
    @Column(name = "cds_id")
    private Integer cdsId;

    @Id
    @Column(name = "namespace", length = 31)
    private String namespace;

    @Id
    @Column(name = "namespace_ver", length = 31)
    private String namespaceVer;

    @Id
    @Column(name = "gene_external_id")
    private Integer geneExternalId;

    public AnnotationCodingSequenceExternalIds() {
        super();
    }

    public Integer getCdsId() {
        return cdsId;
    }

    public void setCdsId(Integer cdsId) {
        this.cdsId = cdsId;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Integer getGeneExternalId() {
        return geneExternalId;
    }

    public void setGeneExternalId(Integer geneExternalId) {
        this.geneExternalId = geneExternalId;
    }

}

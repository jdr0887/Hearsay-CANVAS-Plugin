package org.renci.hearsay.canvas.annotation.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "cds_id")
    private Integer cdsId;

    @Column(name = "namespace", length = 31)
    private String namespace;

    @Column(name = "namespace_ver", length = 31)
    private String namespaceVer;

    @Column(name = "gene_external_id")
    private Integer geneExternalId;

    public AnnotationCodingSequenceExternalIds() {
        super();
    }

}

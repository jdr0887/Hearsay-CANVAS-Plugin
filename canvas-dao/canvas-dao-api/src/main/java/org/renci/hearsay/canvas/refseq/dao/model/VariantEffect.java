package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariantEffect")
@Entity
@Table(schema = "refseq", name = "variant_effect")
public class VariantEffect {

    @Lob
    @Column(name = "variant_effect")
    private String variantEffect;

    @Column(name = "priority")
    private Integer priority;

    public VariantEffect() {
        super();
    }

}

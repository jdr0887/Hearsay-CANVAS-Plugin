package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "refseq", name = "variant_effect")
public class VariantEffect implements Persistable {

    private static final long serialVersionUID = 8661033855192807541L;

    @Id
    @Lob
    @Column(name = "variant_effect")
    private String variantEffect;

    @Column(name = "priority")
    private Integer priority;

    public VariantEffect() {
        super();
    }

    public String getVariantEffect() {
        return variantEffect;
    }

    public void setVariantEffect(String variantEffect) {
        this.variantEffect = variantEffect;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("VariantEffect [variantEffect=%s, priority=%s]", variantEffect, priority);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((variantEffect == null) ? 0 : variantEffect.hashCode());
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
        VariantEffect other = (VariantEffect) obj;
        if (priority == null) {
            if (other.priority != null)
                return false;
        } else if (!priority.equals(other.priority))
            return false;
        if (variantEffect == null) {
            if (other.variantEffect != null)
                return false;
        } else if (!variantEffect.equals(other.variantEffect))
            return false;
        return true;
    }

}

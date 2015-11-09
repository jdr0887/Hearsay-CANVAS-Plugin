package org.renci.hearsay.canvas.var.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;

@Entity
@Table(schema = "var", name = "var_set")
public class VariantSet implements Persistable {

    private static final long serialVersionUID = 6821062280221341220L;

    @Id
    @Column(name = "var_set_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ref_id")
    private GenomeRef genomeRef;

    public VariantSet() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenomeRef getGenomeRef() {
        return genomeRef;
    }

    public void setGenomeRef(GenomeRef genomeRef) {
        this.genomeRef = genomeRef;
    }

    @Override
    public String toString() {
        return "VariantSet [id=" + id + ", genomeRef=" + genomeRef + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genomeRef == null) ? 0 : genomeRef.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        VariantSet other = (VariantSet) obj;
        // if (genomeRef == null) {
        // if (other.genomeRef != null)
        // return false;
        // } else if (!genomeRef.equals(other.genomeRef))
        // return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}

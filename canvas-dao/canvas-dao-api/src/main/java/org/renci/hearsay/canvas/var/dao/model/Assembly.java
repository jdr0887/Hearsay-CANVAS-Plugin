package org.renci.hearsay.canvas.var.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "var", name = "asm")
public class Assembly implements Persistable {

    private static final long serialVersionUID = 6349711189938113203L;

    @Id
    @Column(name = "asm_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @ManyToOne
    @JoinColumn(name = "var_set_id")
    private VariantSet variantSet;

    public Assembly() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public VariantSet getVariantSet() {
        return variantSet;
    }

    public void setVariantSet(VariantSet variantSet) {
        this.variantSet = variantSet;
    }

    @Override
    public String toString() {
        return "Assembly [id=" + id + ", library=" + library + ", variantSet=" + variantSet + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((library == null) ? 0 : library.hashCode());
        result = prime * result + ((variantSet == null) ? 0 : variantSet.hashCode());
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
        Assembly other = (Assembly) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (library == null) {
            if (other.library != null)
                return false;
        } else if (!library.equals(other.library))
            return false;
        if (variantSet == null) {
            if (other.variantSet != null)
                return false;
        } else if (!variantSet.equals(other.variantSet))
            return false;
        return true;
    }

}

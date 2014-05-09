package org.renci.hearsay.canvas.var.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;

@Entity
@Table(schema = "var", name = "var_set_loc")
public class VariantSetLocation implements Persistable {

    private static final long serialVersionUID = 8718903481376803091L;

    @MapsId("varSetId")
    @ManyToOne
    @JoinColumn(name = "var_set_id")
    private VariantSet varSet;

    @MapsId("versionAccession")
    @ManyToOne
    @JoinColumn(name = "ref_ver_accession")
    private GenomeRefSeq genomeRefSeq;

    @MapsId("pos")
    @Column(name = "pos")
    private Integer pos;

    @EmbeddedId
    private VariantSetLocationPK key;

    @Column(name = "vcffilter")
    private String vcfFilter;

    @Column(name = "qual")
    private Float qual;

    public VariantSetLocation() {
        super();
    }

    public VariantSet getVarSet() {
        return varSet;
    }

    public void setVarSet(VariantSet varSet) {
        this.varSet = varSet;
    }

    public GenomeRefSeq getGenomeRefSeq() {
        return genomeRefSeq;
    }

    public void setGenomeRefSeq(GenomeRefSeq genomeRefSeq) {
        this.genomeRefSeq = genomeRefSeq;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public VariantSetLocationPK getKey() {
        return key;
    }

    public void setKey(VariantSetLocationPK key) {
        this.key = key;
    }

    public String getVcfFilter() {
        return vcfFilter;
    }

    public void setVcfFilter(String vcfFilter) {
        this.vcfFilter = vcfFilter;
    }

    public Float getQual() {
        return qual;
    }

    public void setQual(Float qual) {
        this.qual = qual;
    }

    @Override
    public String toString() {
        return "VariantSetLocation [varSet=" + varSet + ", genomeRefSeq=" + genomeRefSeq + ", pos=" + pos + ", key="
                + key + ", vcfFilter=" + vcfFilter + ", qual=" + qual + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genomeRefSeq == null) ? 0 : genomeRefSeq.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((qual == null) ? 0 : qual.hashCode());
        result = prime * result + ((varSet == null) ? 0 : varSet.hashCode());
        result = prime * result + ((vcfFilter == null) ? 0 : vcfFilter.hashCode());
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
        VariantSetLocation other = (VariantSetLocation) obj;
        if (genomeRefSeq == null) {
            if (other.genomeRefSeq != null)
                return false;
        } else if (!genomeRefSeq.equals(other.genomeRefSeq))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (qual == null) {
            if (other.qual != null)
                return false;
        } else if (!qual.equals(other.qual))
            return false;
        if (varSet == null) {
            if (other.varSet != null)
                return false;
        } else if (!varSet.equals(other.varSet))
            return false;
        if (vcfFilter == null) {
            if (other.vcfFilter != null)
                return false;
        } else if (!vcfFilter.equals(other.vcfFilter))
            return false;
        return true;
    }

}

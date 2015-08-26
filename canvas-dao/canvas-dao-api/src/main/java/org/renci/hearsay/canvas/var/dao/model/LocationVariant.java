package org.renci.hearsay.canvas.var.dao.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;

@Entity
@Table(schema = "var", name = "loc_var", uniqueConstraints = { @UniqueConstraint(columnNames = { "ref_id",
        "ref_ver_accession", "pos", "type", "seq", "end_pos" }) })
public class LocationVariant implements Persistable {

    private static final long serialVersionUID = 3259272023352164114L;

    @Id
    @Column(name = "loc_var_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ref_id")
    private GenomeRef genomeRef;

    @ManyToOne
    @JoinColumn(name = "ref_ver_accession")
    private GenomeRefSeq referenceVersionAccession;

    @Column(name = "pos")
    private Integer position;

    @Lob
    @Column(name = "ref")
    private String ref;

    @Column(name = "end_pos")
    private Integer endPosition;

    @ManyToOne
    @JoinColumn(name = "type")
    private VariantType type;

    @Column(name = "seq", length = 65535)
    private String seq;

    @OneToMany(mappedBy = "locationVariant", fetch = FetchType.LAZY)
    private List<MaxVariantFrequency> maxVariantFrequencies;

    @OneToMany(mappedBy = "locationVariant", fetch = FetchType.LAZY)
    private List<MaxFreq> maxFreqs;

    public LocationVariant() {
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

    public GenomeRefSeq getReferenceVersionAccession() {
        return referenceVersionAccession;
    }

    public void setReferenceVersionAccession(GenomeRefSeq referenceVersionAccession) {
        this.referenceVersionAccession = referenceVersionAccession;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    public VariantType getType() {
        return type;
    }

    public void setType(VariantType type) {
        this.type = type;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public List<MaxVariantFrequency> getMaxVariantFrequencies() {
        return maxVariantFrequencies;
    }

    public void setMaxVariantFrequencies(List<MaxVariantFrequency> maxVariantFrequencies) {
        this.maxVariantFrequencies = maxVariantFrequencies;
    }

    public List<MaxFreq> getMaxFreqs() {
        return maxFreqs;
    }

    public void setMaxFreqs(List<MaxFreq> maxFreqs) {
        this.maxFreqs = maxFreqs;
    }

    @Override
    public String toString() {
        return String.format("LocationVariant [id=%s, position=%s, ref=%s, endPosition=%s, seq=%s]", id, position, ref,
                endPosition, seq);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endPosition == null) ? 0 : endPosition.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((ref == null) ? 0 : ref.hashCode());
        result = prime * result + ((seq == null) ? 0 : seq.hashCode());
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
        LocationVariant other = (LocationVariant) obj;
        if (endPosition == null) {
            if (other.endPosition != null)
                return false;
        } else if (!endPosition.equals(other.endPosition))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (ref == null) {
            if (other.ref != null)
                return false;
        } else if (!ref.equals(other.ref))
            return false;
        if (seq == null) {
            if (other.seq != null)
                return false;
        } else if (!seq.equals(other.seq))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.refseq.dao.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.RefSeqStrandTypeConverter;

@Entity
@Table(schema = "refseq", name = "transcr_maps")
// @TypeDefs({ @TypeDef(name = StrandUserType.NAME, typeClass = StrandBasicType.class) })
public class TranscriptMaps implements Persistable {

    private static final long serialVersionUID = 8175717803443861686L;

    @Id
    @Column(name = "refseq_transcr_maps_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "refseq_transcr_ver_id")
    private Transcript transcript;

    @Column(name = "genome_ref_id")
    private Integer genomeRefId;

    @Column(name = "map_count")
    private Integer mapCount;

    @Column(name = "strand", columnDefinition = "strand_type")
    // @Type(type = "org.renci.hearsay.canvas.refseq.dao.StrandBasicType")
    @Convert(converter = RefSeqStrandTypeConverter.class)
    private RefSeqStrandType strand;

    @Column(name = "score")
    private Double score;

    @Column(name = "ident")
    private Double ident;

    @ManyToOne
    @JoinColumn(name = "seq_ver_accession")
    private GenomeRefSeq genomeRefSeq;

    @Column(name = "exon_count")
    private Integer exonCount;

    @OneToMany(mappedBy = "transcriptMaps", fetch = FetchType.LAZY)
    protected Set<TranscriptMapsExons> transcriptMapsExons;

    public TranscriptMaps() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

    public Integer getMapCount() {
        return mapCount;
    }

    public void setMapCount(Integer mapCount) {
        this.mapCount = mapCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getIdent() {
        return ident;
    }

    public void setIdent(Double ident) {
        this.ident = ident;
    }

    public GenomeRefSeq getGenomeRefSeq() {
        return genomeRefSeq;
    }

    public void setGenomeRefSeq(GenomeRefSeq genomeRefSeq) {
        this.genomeRefSeq = genomeRefSeq;
    }

    public Integer getExonCount() {
        return exonCount;
    }

    public void setExonCount(Integer exonCount) {
        this.exonCount = exonCount;
    }

    public RefSeqStrandType getStrand() {
        return strand;
    }

    public void setStrand(RefSeqStrandType strand) {
        this.strand = strand;
    }

    public Set<TranscriptMapsExons> getTranscriptMapsExons() {
        if (this.transcriptMapsExons == null) {
            this.transcriptMapsExons = new HashSet<TranscriptMapsExons>();
        }
        return transcriptMapsExons;
    }

    public void setTranscriptMapsExons(Set<TranscriptMapsExons> transcriptMapsExons) {
        this.transcriptMapsExons = transcriptMapsExons;
    }

    @Override
    public String toString() {
        return String.format(
                "TranscriptMaps [id=%s, transcript=%s, genomeRefId=%s, mapCount=%s, strand=%s, score=%s, ident=%s, exonCount=%s]",
                id, transcript, genomeRefId, mapCount, strand, score, ident, exonCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exonCount == null) ? 0 : exonCount.hashCode());
        result = prime * result + ((genomeRefId == null) ? 0 : genomeRefId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ident == null) ? 0 : ident.hashCode());
        result = prime * result + ((mapCount == null) ? 0 : mapCount.hashCode());
        result = prime * result + ((score == null) ? 0 : score.hashCode());
        result = prime * result + ((strand == null) ? 0 : strand.hashCode());
        result = prime * result + ((transcript == null) ? 0 : transcript.hashCode());
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
        TranscriptMaps other = (TranscriptMaps) obj;
        if (exonCount == null) {
            if (other.exonCount != null)
                return false;
        } else if (!exonCount.equals(other.exonCount))
            return false;
        if (genomeRefId == null) {
            if (other.genomeRefId != null)
                return false;
        } else if (!genomeRefId.equals(other.genomeRefId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ident == null) {
            if (other.ident != null)
                return false;
        } else if (!ident.equals(other.ident))
            return false;
        if (mapCount == null) {
            if (other.mapCount != null)
                return false;
        } else if (!mapCount.equals(other.mapCount))
            return false;
        if (score == null) {
            if (other.score != null)
                return false;
        } else if (!score.equals(other.score))
            return false;
        if (strand == null) {
            if (other.strand != null)
                return false;
        } else if (!strand.equals(other.strand))
            return false;
        if (transcript == null) {
            if (other.transcript != null)
                return false;
        } else if (!transcript.equals(other.transcript))
            return false;
        return true;
    }

}

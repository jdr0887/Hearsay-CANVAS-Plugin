package org.renci.hearsay.canvas.ref.dao.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "ref", name = "genome_ref")
@NamedQueries({
        @NamedQuery(name = "GenomeRef.findByShortName", query = "SELECT a FROM GenomeRef a where a.shortName = :shortName"),
        @NamedQuery(name = "GenomeRef.findByName", query = "SELECT a FROM GenomeRef a where a.name = :name"),
        @NamedQuery(name = "GenomeRef.findBySeqTypeAndContig", query = "SELECT a FROM GenomeRef a where a.genomeRefSeqs.seqType = :seqType and a.genomeRefSeqs.contig = :contig") })
public class GenomeRef implements Persistable {

    private static final long serialVersionUID = -6241264265732175194L;

    @Id
    @Column(name = "ref_id")
    protected Long id;

    @Column(name = "ref_source")
    protected String refSource;

    @Column(name = "ref_ver")
    protected String refVer;

    @Column(name = "ref_shortname", length = 50)
    protected String shortName;

    @Column(name = "basic_fasta_url", length = 1023)
    protected String basicFastaURL;

    @Column(name = "extras_fasta_url", length = 1023)
    protected String extrasFastaURL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "ref", name = "genome_ref_seqs", joinColumns = @JoinColumn(name = "ref_id"), inverseJoinColumns = @JoinColumn(name = "seq_ver_accession"))
    protected Set<GenomeRefSeq> genomeRefSeqs;

    public GenomeRef() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefSource() {
        return refSource;
    }

    public void setRefSource(String refSource) {
        this.refSource = refSource;
    }

    public String getRefVer() {
        return refVer;
    }

    public void setRefVer(String refVer) {
        this.refVer = refVer;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getBasicFastaURL() {
        return basicFastaURL;
    }

    public void setBasicFastaURL(String basicFastaURL) {
        this.basicFastaURL = basicFastaURL;
    }

    public String getExtrasFastaURL() {
        return extrasFastaURL;
    }

    public void setExtrasFastaURL(String extrasFastaURL) {
        this.extrasFastaURL = extrasFastaURL;
    }

    public Set<GenomeRefSeq> getGenomeRefSeqs() {
        return genomeRefSeqs;
    }

    public void setGenomeRefSeqs(Set<GenomeRefSeq> genomeRefSeqs) {
        this.genomeRefSeqs = genomeRefSeqs;
    }

    @Override
    public String toString() {
        return String.format(
                "GenomeRef [id=%s, refSource=%s, refVer=%s, shortName=%s, basicFastaURL=%s, extrasFastaURL=%s]", id,
                refSource, refVer, shortName, basicFastaURL, extrasFastaURL);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((basicFastaURL == null) ? 0 : basicFastaURL.hashCode());
        result = prime * result + ((extrasFastaURL == null) ? 0 : extrasFastaURL.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((refSource == null) ? 0 : refSource.hashCode());
        result = prime * result + ((refVer == null) ? 0 : refVer.hashCode());
        result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
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
        GenomeRef other = (GenomeRef) obj;
        if (basicFastaURL == null) {
            if (other.basicFastaURL != null)
                return false;
        } else if (!basicFastaURL.equals(other.basicFastaURL))
            return false;
        if (extrasFastaURL == null) {
            if (other.extrasFastaURL != null)
                return false;
        } else if (!extrasFastaURL.equals(other.extrasFastaURL))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (refSource == null) {
            if (other.refSource != null)
                return false;
        } else if (!refSource.equals(other.refSource))
            return false;
        if (refVer == null) {
            if (other.refVer != null)
                return false;
        } else if (!refVer.equals(other.refVer))
            return false;
        if (shortName == null) {
            if (other.shortName != null)
                return false;
        } else if (!shortName.equals(other.shortName))
            return false;
        return true;
    }

}

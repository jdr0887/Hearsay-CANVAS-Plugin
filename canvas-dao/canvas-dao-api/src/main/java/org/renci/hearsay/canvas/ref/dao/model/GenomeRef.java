package org.renci.hearsay.canvas.ref.dao.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "ref", name = "genome_ref")
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
    protected String refShortname;

    @Column(name = "basic_fasta_url", length = 1023)
    protected String basicFastaUrl;

    @Column(name = "extras_fasta_url", length = 1023)
    protected String extrasFastaUrl;

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

    public String getRefShortname() {
        return refShortname;
    }

    public void setRefShortname(String refShortname) {
        this.refShortname = refShortname;
    }

    public String getBasicFastaUrl() {
        return basicFastaUrl;
    }

    public void setBasicFastaUrl(String basicFastaUrl) {
        this.basicFastaUrl = basicFastaUrl;
    }

    public String getExtrasFastaUrl() {
        return extrasFastaUrl;
    }

    public void setExtrasFastaUrl(String extrasFastaUrl) {
        this.extrasFastaUrl = extrasFastaUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "GenomeRef [id=%s, refSource=%s, refVer=%s, refShortname=%s, basicFastaUrl=%s, extrasFastaUrl=%s]", id,
                refSource, refVer, refShortname, basicFastaUrl, extrasFastaUrl);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((basicFastaUrl == null) ? 0 : basicFastaUrl.hashCode());
        result = prime * result + ((extrasFastaUrl == null) ? 0 : extrasFastaUrl.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((refShortname == null) ? 0 : refShortname.hashCode());
        result = prime * result + ((refSource == null) ? 0 : refSource.hashCode());
        result = prime * result + ((refVer == null) ? 0 : refVer.hashCode());
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
        if (basicFastaUrl == null) {
            if (other.basicFastaUrl != null)
                return false;
        } else if (!basicFastaUrl.equals(other.basicFastaUrl))
            return false;
        if (extrasFastaUrl == null) {
            if (other.extrasFastaUrl != null)
                return false;
        } else if (!extrasFastaUrl.equals(other.extrasFastaUrl))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (refShortname == null) {
            if (other.refShortname != null)
                return false;
        } else if (!refShortname.equals(other.refShortname))
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
        return true;
    }

}

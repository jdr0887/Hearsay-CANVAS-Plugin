package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "cds_transl_exceptions")
public class CodingSequenceTranslExceptions {

    @Id
    @Column(name = "refseq_cds_id")
    private Integer refseqCdsId;

    @Id
    @Column(name = "start_loc")
    private Integer startLoc;

    @Column(name = "stop_loc")
    private Integer stopLoc;

    @Column(name = "amino_acid", length = 31)
    private String aminoAcid;

    public CodingSequenceTranslExceptions() {
        super();
    }

    public Integer getRefseqCdsId() {
        return refseqCdsId;
    }

    public void setRefseqCdsId(Integer refseqCdsId) {
        this.refseqCdsId = refseqCdsId;
    }

    public Integer getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(Integer startLoc) {
        this.startLoc = startLoc;
    }

    public String getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(String aminoAcid) {
        this.aminoAcid = aminoAcid;
    }

    
}

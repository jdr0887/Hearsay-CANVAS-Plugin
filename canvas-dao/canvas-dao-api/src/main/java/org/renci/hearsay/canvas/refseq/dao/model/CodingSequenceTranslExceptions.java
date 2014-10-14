package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "cds_transl_exceptions")
public class CodingSequenceTranslExceptions {

    @Column(name = "refseq_cds_id")
    private Integer refseqCdsId;

    @Column(name = "start_loc")
    private Integer startLoc;

    @Column(name = "stop_loc")
    private Integer stopLoc;

    @Column(name = "amino_acid", length = 31)
    private String aminoAcid;

    public CodingSequenceTranslExceptions() {
        super();
    }

}

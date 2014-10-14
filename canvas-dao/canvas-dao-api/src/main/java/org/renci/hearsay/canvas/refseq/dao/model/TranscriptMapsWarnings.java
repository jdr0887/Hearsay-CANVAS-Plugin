package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "transcr_maps_warnings")
public class TranscriptMapsWarnings {

    @Column(name = "refseq_transcr_maps_id")
    private Integer refseqTranscrMapsId;

    @Column(name = "warning_warning_name")
    private String warningWarningName;

    public TranscriptMapsWarnings() {
        super();
    }

}

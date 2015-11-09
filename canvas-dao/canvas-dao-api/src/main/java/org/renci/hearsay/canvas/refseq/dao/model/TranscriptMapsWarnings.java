package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "refseq", name = "transcr_maps_warnings")
public class TranscriptMapsWarnings {

    @Id
    @Column(name = "refseq_transcr_maps_id")
    private Integer refseqTranscrMapsId;

    @Id
    @Column(name = "warning_warning_name")
    private String warningWarningName;

    public TranscriptMapsWarnings() {
        super();
    }

    public Integer getRefseqTranscrMapsId() {
        return refseqTranscrMapsId;
    }

    public void setRefseqTranscrMapsId(Integer refseqTranscrMapsId) {
        this.refseqTranscrMapsId = refseqTranscrMapsId;
    }

    public String getWarningWarningName() {
        return warningWarningName;
    }

    public void setWarningWarningName(String warningWarningName) {
        this.warningWarningName = warningWarningName;
    }

}

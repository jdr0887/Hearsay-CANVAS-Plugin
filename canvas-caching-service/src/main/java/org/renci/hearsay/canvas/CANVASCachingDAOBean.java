package org.renci.hearsay.canvas;

import org.renci.hearsay.canvas.hgnc.dao.HGNCGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqCodingSequenceDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsExonsDAO;
import org.renci.hearsay.dao.TranscriptIntervalDAO;

public class CANVASCachingDAOBean {

    private org.renci.hearsay.dao.TranscriptDAO hearsayTranscriptDAO;

    private TranscriptIntervalDAO hearsayTranscriptIntervalDAO;

    private RefSeqCodingSequenceDAO refSeqCodingSequenceDAO;

    private RefSeqGeneDAO refSeqGeneDAO;

    private HGNCGeneDAO HGNCGeneDAO;

    private TranscriptDAO transcriptDAO;

    private TranscriptMapsDAO transcriptMapsDAO;

    private TranscriptMapsExonsDAO transcriptMapsExonsDAO;

    public CANVASCachingDAOBean() {
        super();
    }

    public org.renci.hearsay.dao.TranscriptDAO getHearsayTranscriptDAO() {
        return hearsayTranscriptDAO;
    }

    public void setHearsayTranscriptDAO(org.renci.hearsay.dao.TranscriptDAO hearsayTranscriptDAO) {
        this.hearsayTranscriptDAO = hearsayTranscriptDAO;
    }

    public TranscriptIntervalDAO getHearsayTranscriptIntervalDAO() {
        return hearsayTranscriptIntervalDAO;
    }

    public void setHearsayTranscriptIntervalDAO(TranscriptIntervalDAO hearsayTranscriptIntervalDAO) {
        this.hearsayTranscriptIntervalDAO = hearsayTranscriptIntervalDAO;
    }

    public RefSeqCodingSequenceDAO getRefSeqCodingSequenceDAO() {
        return refSeqCodingSequenceDAO;
    }

    public void setRefSeqCodingSequenceDAO(RefSeqCodingSequenceDAO refSeqCodingSequenceDAO) {
        this.refSeqCodingSequenceDAO = refSeqCodingSequenceDAO;
    }

    public RefSeqGeneDAO getRefSeqGeneDAO() {
        return refSeqGeneDAO;
    }

    public void setRefSeqGeneDAO(RefSeqGeneDAO refSeqGeneDAO) {
        this.refSeqGeneDAO = refSeqGeneDAO;
    }

    public HGNCGeneDAO getHGNCGeneDAO() {
        return HGNCGeneDAO;
    }

    public void setHGNCGeneDAO(HGNCGeneDAO hGNCGeneDAO) {
        HGNCGeneDAO = hGNCGeneDAO;
    }

    public TranscriptDAO getTranscriptDAO() {
        return transcriptDAO;
    }

    public void setTranscriptDAO(TranscriptDAO transcriptDAO) {
        this.transcriptDAO = transcriptDAO;
    }

    public TranscriptMapsDAO getTranscriptMapsDAO() {
        return transcriptMapsDAO;
    }

    public void setTranscriptMapsDAO(TranscriptMapsDAO transcriptMapsDAO) {
        this.transcriptMapsDAO = transcriptMapsDAO;
    }

    public TranscriptMapsExonsDAO getTranscriptMapsExonsDAO() {
        return transcriptMapsExonsDAO;
    }

    public void setTranscriptMapsExonsDAO(TranscriptMapsExonsDAO transcriptMapsExonsDAO) {
        this.transcriptMapsExonsDAO = transcriptMapsExonsDAO;
    }

}

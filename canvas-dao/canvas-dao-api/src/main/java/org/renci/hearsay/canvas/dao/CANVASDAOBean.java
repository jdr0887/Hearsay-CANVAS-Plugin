package org.renci.hearsay.canvas.dao;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneDAO;
import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneExternalIdsDAO;
import org.renci.hearsay.canvas.hgnc.dao.HGNCGeneDAO;
import org.renci.hearsay.canvas.ref.dao.GenomeRefDAO;
import org.renci.hearsay.canvas.refseq.dao.FeatureDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqCodingSequenceDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsExonsDAO;

public class CANVASDAOBean {

    private AnnotationGeneDAO annotationGeneDAO;

    private AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO;

    private RefSeqCodingSequenceDAO refSeqCodingSequenceDAO;

    private RefSeqGeneDAO refSeqGeneDAO;

    private FeatureDAO featureDAO;

    private HGNCGeneDAO HGNCGeneDAO;

    private GenomeRefDAO genomeRefDAO;

    private TranscriptDAO transcriptDAO;

    private TranscriptMapsDAO transcriptMapsDAO;

    private TranscriptMapsExonsDAO transcriptMapsExonsDAO;

    public CANVASDAOBean() {
        super();
    }

    public FeatureDAO getFeatureDAO() {
        return featureDAO;
    }

    public void setFeatureDAO(FeatureDAO featureDAO) {
        this.featureDAO = featureDAO;
    }

    public AnnotationGeneDAO getAnnotationGeneDAO() {
        return annotationGeneDAO;
    }

    public void setAnnotationGeneDAO(AnnotationGeneDAO annotationGeneDAO) {
        this.annotationGeneDAO = annotationGeneDAO;
    }

    public AnnotationGeneExternalIdsDAO getAnnotationGeneExternalIdsDAO() {
        return annotationGeneExternalIdsDAO;
    }

    public void setAnnotationGeneExternalIdsDAO(AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO) {
        this.annotationGeneExternalIdsDAO = annotationGeneExternalIdsDAO;
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

    public GenomeRefDAO getGenomeRefDAO() {
        return genomeRefDAO;
    }

    public void setGenomeRefDAO(GenomeRefDAO genomeRefDAO) {
        this.genomeRefDAO = genomeRefDAO;
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

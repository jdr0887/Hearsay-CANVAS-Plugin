package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.dao.HearsayDAOException;

public interface RefSeqCodingSequenceDAO extends BaseDAO<RefSeqCodingSequence, Long> {

    public List<RefSeqCodingSequence> findByVersion(String version) throws HearsayDAOException;

    public List<RefSeqCodingSequence> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId)
            throws HearsayDAOException;

    public List<RefSeqCodingSequence> findByRefSeqVersionAndTranscriptId(String fetchPlan, String refSeqVersion, String transcriptId)
            throws HearsayDAOException;

}

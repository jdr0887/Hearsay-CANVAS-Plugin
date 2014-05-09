package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.dao.HearsayDAOException;

public interface TranscriptDAO extends BaseDAO<Transcript, String> {

    public List<Transcript> findByRefSeqVersionAndGenomeRefId(String refSeqVersion, Integer genomeRefId)
            throws HearsayDAOException;

}

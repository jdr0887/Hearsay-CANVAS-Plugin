package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.dao.HearsayDAOException;

public interface TranscriptMapsDAO extends BaseDAO<TranscriptMaps, Integer> {

    public List<TranscriptMaps> findByTranscriptId(String versionId) throws HearsayDAOException;

    public List<TranscriptMaps> findByGenomeRefIdAndRefSeqVersion(Integer genomeRefId, String refSeqVersion) throws HearsayDAOException;

    public List<TranscriptMaps> findByGenomeRefIdAndRefSeqVersion(String fetchGroup, Integer genomeRefId, String refSeqVersion)
            throws HearsayDAOException;

    public TranscriptMaps findById(String fetchGroup, Integer id) throws HearsayDAOException;

}

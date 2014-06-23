package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;

public interface TranscriptMapsExonsDAO extends BaseDAO<TranscriptMapsExons, Integer> {

    public List<TranscriptMapsExons> findByGenomeRefIdAndRefSeqVersion(Integer genomeRefId, String refSeqVersion)
            throws HearsayDAOException;

    public List<TranscriptMapsExons> findByGenomeRefIdAndRefSeqVersionAndAccession(Integer genomeRefId,
            String refSeqVersion, String accession) throws HearsayDAOException;

    public List<TranscriptMapsExons> findByTranscriptMapsId(Integer id) throws HearsayDAOException;

    public List<TranscriptMapsExons> findByTranscriptVersionIdAndTranscriptMapsMapCount(String versionId,
            Integer mapCount) throws HearsayDAOException;

}

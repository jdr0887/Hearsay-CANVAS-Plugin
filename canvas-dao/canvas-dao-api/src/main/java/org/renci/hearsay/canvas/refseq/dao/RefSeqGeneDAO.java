package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOException;

public interface RefSeqGeneDAO extends BaseDAO<RefSeqGene, Long> {

    public List<RefSeqGene> findByRefSeqVersion(String refSeqVersion) throws HearsayDAOException;

    public List<RefSeqGene> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId) throws HearsayDAOException;

}

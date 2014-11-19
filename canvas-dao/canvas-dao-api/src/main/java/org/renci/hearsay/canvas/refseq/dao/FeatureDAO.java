package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.dao.HearsayDAOException;

public interface FeatureDAO extends BaseDAO<Feature, Long> {

    public List<Feature> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId)
            throws HearsayDAOException;

}

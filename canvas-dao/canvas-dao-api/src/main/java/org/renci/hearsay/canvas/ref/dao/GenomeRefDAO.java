package org.renci.hearsay.canvas.ref.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.dao.HearsayDAOException;

public interface GenomeRefDAO extends BaseDAO<GenomeRef, Long> {

    public List<GenomeRef> findByShortName(String shortName) throws HearsayDAOException;

    public List<GenomeRef> findByName(String name) throws HearsayDAOException;

    public List<GenomeRef> findBySeqTypeAndContig(String seqType, String contig) throws HearsayDAOException;

}

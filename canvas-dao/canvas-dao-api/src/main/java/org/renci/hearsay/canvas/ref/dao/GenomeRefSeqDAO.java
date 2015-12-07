package org.renci.hearsay.canvas.ref.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.dao.HearsayDAOException;

public interface GenomeRefSeqDAO extends BaseDAO<GenomeRefSeq, String> {

    public List<GenomeRefSeq> findAll() throws HearsayDAOException;

}

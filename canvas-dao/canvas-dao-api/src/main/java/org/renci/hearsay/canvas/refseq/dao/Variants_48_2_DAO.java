package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_48_2;
import org.renci.hearsay.dao.HearsayDAOException;

public interface Variants_48_2_DAO extends BaseDAO<Variants_48_2, Long> {

    public List<Variants_48_2> findByLocationVariantId(Long id) throws HearsayDAOException;

}

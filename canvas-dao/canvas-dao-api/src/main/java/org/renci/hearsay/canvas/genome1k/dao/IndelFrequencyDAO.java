package org.renci.hearsay.canvas.genome1k.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.IndelFrequency;
import org.renci.hearsay.dao.HearsayDAOException;

public interface IndelFrequencyDAO extends BaseDAO<IndelFrequency, Long> {

    public List<IndelFrequency> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

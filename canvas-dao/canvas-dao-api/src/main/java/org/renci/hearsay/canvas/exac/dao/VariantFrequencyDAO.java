package org.renci.hearsay.canvas.exac.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.exac.dao.model.VariantFrequency;
import org.renci.hearsay.dao.HearsayDAOException;

public interface VariantFrequencyDAO extends BaseDAO<VariantFrequency, Long> {

    public List<VariantFrequency> findByLocationVariantIdAndVersion(Long locVarId, String version) throws HearsayDAOException;

}

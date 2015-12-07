package org.renci.hearsay.canvas.exac.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.dao.HearsayDAOException;

public interface MaxVariantFrequencyDAO extends BaseDAO<MaxVariantFrequency, Long> {

    public List<MaxVariantFrequency> findByLocationVariantIdAndVersion(Long locVarId, String version) throws HearsayDAOException;

    public List<MaxVariantFrequency> findByLocationVariantId(Long locVarId) throws HearsayDAOException;

    public List<MaxVariantFrequency> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold) throws HearsayDAOException;

}

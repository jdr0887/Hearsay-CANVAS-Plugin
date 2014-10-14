package org.renci.hearsay.canvas.genome1k.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.SNPFrequencySubpopulation;
import org.renci.hearsay.dao.HearsayDAOException;

public interface SNPFrequencySubpopulationDAO extends BaseDAO<SNPFrequencySubpopulation, Long> {

    public List<SNPFrequencySubpopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

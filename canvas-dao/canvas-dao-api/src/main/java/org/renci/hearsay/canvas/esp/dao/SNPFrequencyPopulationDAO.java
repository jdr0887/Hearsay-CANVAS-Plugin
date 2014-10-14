package org.renci.hearsay.canvas.esp.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.esp.dao.model.SNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;

public interface SNPFrequencyPopulationDAO extends BaseDAO<SNPFrequencyPopulation, Long> {

    public List<SNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

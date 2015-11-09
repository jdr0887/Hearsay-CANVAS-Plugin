package org.renci.hearsay.canvas.esp.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.esp.dao.model.ESPSNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;

public interface ESPSNPFrequencyPopulationDAO extends BaseDAO<ESPSNPFrequencyPopulation, Long> {

    public List<ESPSNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

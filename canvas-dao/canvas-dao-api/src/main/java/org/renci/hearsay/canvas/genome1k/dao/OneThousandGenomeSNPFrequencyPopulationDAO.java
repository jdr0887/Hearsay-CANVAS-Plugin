package org.renci.hearsay.canvas.genome1k.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeSNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;

public interface OneThousandGenomeSNPFrequencyPopulationDAO extends BaseDAO<OneThousandGenomeSNPFrequencyPopulation, Long> {

    public List<OneThousandGenomeSNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

package org.renci.hearsay.canvas.genome1k.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeSNPFrequencySubpopulation;
import org.renci.hearsay.dao.HearsayDAOException;

public interface OneThousandGenomeSNPFrequencySubpopulationDAO extends BaseDAO<OneThousandGenomeSNPFrequencySubpopulation, Long> {

    public List<OneThousandGenomeSNPFrequencySubpopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

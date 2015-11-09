package org.renci.hearsay.canvas.esp.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.esp.dao.ESPSNPFrequencyPopulationDAO;
import org.renci.hearsay.canvas.esp.dao.model.ESPSNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNPFrequencyPopulationDAOImpl extends BaseDAOImpl<ESPSNPFrequencyPopulation, Long> implements
        ESPSNPFrequencyPopulationDAO {

    private final Logger logger = LoggerFactory.getLogger(SNPFrequencyPopulationDAOImpl.class);

    public SNPFrequencyPopulationDAOImpl() {
        super();
    }

    @Override
    public Class<ESPSNPFrequencyPopulation> getPersistentClass() {
        return ESPSNPFrequencyPopulation.class;
    }

    @Override
    public List<ESPSNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<ESPSNPFrequencyPopulation> query = getEntityManager().createNamedQuery(
                "esp.SNPFrequencyPopulation.findByLocationVariantIdAndVersion", ESPSNPFrequencyPopulation.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<ESPSNPFrequencyPopulation> ret = query.getResultList();
        return ret;
    }

}

package org.renci.hearsay.canvas.esp.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.esp.dao.SNPFrequencyPopulationDAO;
import org.renci.hearsay.canvas.esp.dao.model.SNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNPFrequencyPopulationDAOImpl extends BaseDAOImpl<SNPFrequencyPopulation, Long> implements
        SNPFrequencyPopulationDAO {

    private final Logger logger = LoggerFactory.getLogger(SNPFrequencyPopulationDAOImpl.class);

    public SNPFrequencyPopulationDAOImpl() {
        super();
    }

    @Override
    public Class<SNPFrequencyPopulation> getPersistentClass() {
        return SNPFrequencyPopulation.class;
    }

    @Override
    public List<SNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<SNPFrequencyPopulation> query = getEntityManager().createNamedQuery(
                "esp.SNPFrequencyPopulation.findByLocationVariantIdAndVersion", SNPFrequencyPopulation.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<SNPFrequencyPopulation> ret = query.getResultList();
        return ret;
    }

}

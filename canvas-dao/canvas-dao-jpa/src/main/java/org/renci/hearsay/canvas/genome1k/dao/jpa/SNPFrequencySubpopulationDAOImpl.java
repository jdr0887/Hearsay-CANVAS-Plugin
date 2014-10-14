package org.renci.hearsay.canvas.genome1k.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.genome1k.dao.SNPFrequencySubpopulationDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.SNPFrequencySubpopulation;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SNPFrequencySubpopulationDAOImpl extends BaseDAOImpl<SNPFrequencySubpopulation, Long> implements
        SNPFrequencySubpopulationDAO {

    private final Logger logger = LoggerFactory.getLogger(SNPFrequencySubpopulationDAOImpl.class);

    public SNPFrequencySubpopulationDAOImpl() {
        super();
    }

    @Override
    public Class<SNPFrequencySubpopulation> getPersistentClass() {
        return SNPFrequencySubpopulation.class;
    }

    @Override
    public List<SNPFrequencySubpopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<SNPFrequencySubpopulation> query = getEntityManager().createNamedQuery(
                "SNPFrequencySubpopulation.findByLocationVariantIdAndVersion", SNPFrequencySubpopulation.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<SNPFrequencySubpopulation> ret = query.getResultList();
        return ret;
    }

}

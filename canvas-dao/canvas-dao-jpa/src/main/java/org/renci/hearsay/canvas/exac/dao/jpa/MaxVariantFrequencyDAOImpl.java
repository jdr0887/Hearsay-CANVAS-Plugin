package org.renci.hearsay.canvas.exac.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.exac.dao.MaxVariantFrequencyDAO;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaxVariantFrequencyDAOImpl extends BaseDAOImpl<MaxVariantFrequency, Long> implements
        MaxVariantFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(MaxVariantFrequencyDAOImpl.class);

    public MaxVariantFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<MaxVariantFrequency> getPersistentClass() {
        return MaxVariantFrequency.class;
    }

    @Override
    public List<MaxVariantFrequency> findByLocationVariantIdAndVersion(Long locVarId, String version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, String)");
        TypedQuery<MaxVariantFrequency> query = getEntityManager().createNamedQuery(
                "exac.MaxVariantFrequency.findByLocationVariantIdAndVersion", MaxVariantFrequency.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<MaxVariantFrequency> ret = query.getResultList();
        return ret;
    }

}

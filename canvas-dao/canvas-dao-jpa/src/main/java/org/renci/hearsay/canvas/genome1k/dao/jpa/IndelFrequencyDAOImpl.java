package org.renci.hearsay.canvas.genome1k.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.genome1k.dao.IndelFrequencyDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.IndelFrequency;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndelFrequencyDAOImpl extends BaseDAOImpl<IndelFrequency, Long> implements IndelFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(IndelFrequencyDAOImpl.class);

    public IndelFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<IndelFrequency> getPersistentClass() {
        return IndelFrequency.class;
    }

    @Override
    public List<IndelFrequency> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<IndelFrequency> query = getEntityManager().createNamedQuery(
                "IndelFrequency.findByLocationVariantIdAndVersion", IndelFrequency.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<IndelFrequency> ret = query.getResultList();
        return ret;
    }

}

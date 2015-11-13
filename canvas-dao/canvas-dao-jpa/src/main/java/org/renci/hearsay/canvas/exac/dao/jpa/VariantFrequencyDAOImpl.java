package org.renci.hearsay.canvas.exac.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.exac.dao.VariantFrequencyDAO;
import org.renci.hearsay.canvas.exac.dao.model.VariantFrequency;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class VariantFrequencyDAOImpl extends BaseDAOImpl<VariantFrequency, Long> implements VariantFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantFrequencyDAOImpl.class);

    public VariantFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<VariantFrequency> getPersistentClass() {
        return VariantFrequency.class;
    }

    @Override
    public List<VariantFrequency> findByLocationVariantIdAndVersion(Long locVarId, String version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, String)");
        TypedQuery<VariantFrequency> query = getEntityManager().createNamedQuery(
                "exac.VariantFrequency.findByLocationVariantIdAndVersion", VariantFrequency.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<VariantFrequency> ret = query.getResultList();
        return ret;
    }

}

package org.renci.hearsay.canvas.genome1k.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.genome1k.dao.OneThousandGenomeIndelFrequencyDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeIndelFrequency;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class OneThousandGenomeIndelFrequencyDAOImpl extends BaseDAOImpl<OneThousandGenomeIndelFrequency, Long>
        implements OneThousandGenomeIndelFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(OneThousandGenomeIndelFrequencyDAOImpl.class);

    public OneThousandGenomeIndelFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<OneThousandGenomeIndelFrequency> getPersistentClass() {
        return OneThousandGenomeIndelFrequency.class;
    }

    @Override
    public List<OneThousandGenomeIndelFrequency> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<OneThousandGenomeIndelFrequency> query = getEntityManager()
                .createNamedQuery("IndelFrequency.findByLocationVariantIdAndVersion", OneThousandGenomeIndelFrequency.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<OneThousandGenomeIndelFrequency> ret = query.getResultList();
        return ret;
    }

}

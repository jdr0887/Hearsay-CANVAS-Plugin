package org.renci.hearsay.canvas.genome1k.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.genome1k.dao.OneThousandGenomeSNPFrequencySubpopulationDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeSNPFrequencySubpopulation;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class OneThousandGenomeSNPFrequencySubpopulationDAOImpl extends BaseDAOImpl<OneThousandGenomeSNPFrequencySubpopulation, Long>
        implements OneThousandGenomeSNPFrequencySubpopulationDAO {

    private final Logger logger = LoggerFactory.getLogger(OneThousandGenomeSNPFrequencySubpopulationDAOImpl.class);

    public OneThousandGenomeSNPFrequencySubpopulationDAOImpl() {
        super();
    }

    @Override
    public Class<OneThousandGenomeSNPFrequencySubpopulation> getPersistentClass() {
        return OneThousandGenomeSNPFrequencySubpopulation.class;
    }

    @Override
    public List<OneThousandGenomeSNPFrequencySubpopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<OneThousandGenomeSNPFrequencySubpopulation> query = getEntityManager().createNamedQuery(
                "SNPFrequencySubpopulation.findByLocationVariantIdAndVersion", OneThousandGenomeSNPFrequencySubpopulation.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<OneThousandGenomeSNPFrequencySubpopulation> ret = query.getResultList();
        return ret;
    }

}

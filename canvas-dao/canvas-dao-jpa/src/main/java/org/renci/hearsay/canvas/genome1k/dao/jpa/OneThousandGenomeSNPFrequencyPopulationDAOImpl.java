package org.renci.hearsay.canvas.genome1k.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.genome1k.dao.OneThousandGenomeSNPFrequencyPopulationDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeSNPFrequencyPopulation;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class OneThousandGenomeSNPFrequencyPopulationDAOImpl extends BaseDAOImpl<OneThousandGenomeSNPFrequencyPopulation, Long>
        implements OneThousandGenomeSNPFrequencyPopulationDAO {

    private final Logger logger = LoggerFactory.getLogger(OneThousandGenomeSNPFrequencyPopulationDAOImpl.class);

    public OneThousandGenomeSNPFrequencyPopulationDAOImpl() {
        super();
    }

    @Override
    public Class<OneThousandGenomeSNPFrequencyPopulation> getPersistentClass() {
        return OneThousandGenomeSNPFrequencyPopulation.class;
    }

    @Override
    public List<OneThousandGenomeSNPFrequencyPopulation> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");
        TypedQuery<OneThousandGenomeSNPFrequencyPopulation> query = getEntityManager().createNamedQuery(
                "genome1k.SNPFrequencyPopulation.findByLocationVariantIdAndVersion", OneThousandGenomeSNPFrequencyPopulation.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<OneThousandGenomeSNPFrequencyPopulation> ret = query.getResultList();
        return ret;
    }

}

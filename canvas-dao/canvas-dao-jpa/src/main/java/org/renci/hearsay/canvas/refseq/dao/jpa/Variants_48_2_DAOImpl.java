package org.renci.hearsay.canvas.refseq.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.Variants_48_2_DAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_48_2;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class Variants_48_2_DAOImpl extends BaseDAOImpl<Variants_48_2, Long> implements Variants_48_2_DAO {

    private final Logger logger = LoggerFactory.getLogger(Variants_48_2_DAOImpl.class);

    public Variants_48_2_DAOImpl() {
        super();
    }

    @Override
    public Class<Variants_48_2> getPersistentClass() {
        return Variants_48_2.class;
    }

    @Override
    public List<Variants_48_2> findByLocationVariantId(Long id) throws HearsayDAOException {
        logger.debug("ENTERING findByName()");
        TypedQuery<Variants_48_2> query = getEntityManager().createNamedQuery("Variants_48_2.findByLocationVariantId", Variants_48_2.class);
        query.setParameter("locationVariantId", id);
        List<Variants_48_2> ret = query.getResultList();
        return ret;
    }

}

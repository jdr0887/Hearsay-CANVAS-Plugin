package org.renci.hearsay.canvas.refseq.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.Variants_61_2_DAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Variants_61_2_DAOImpl extends BaseDAOImpl<Variants_61_2, Long> implements Variants_61_2_DAO {

    private final Logger logger = LoggerFactory.getLogger(Variants_61_2_DAOImpl.class);

    public Variants_61_2_DAOImpl() {
        super();
    }

    @Override
    public Class<Variants_61_2> getPersistentClass() {
        return Variants_61_2.class;
    }

    @Override
    public List<Variants_61_2> findByLocationVariantId(Long id) throws HearsayDAOException {
        logger.debug("ENTERING findByName()");
        TypedQuery<Variants_61_2> query = getEntityManager().createNamedQuery("Variants_61_2.findByLocationVariantId",
                Variants_61_2.class);
        query.setParameter("locationVariantId", id);
        List<Variants_61_2> ret = query.getResultList();
        return ret;
    }

}

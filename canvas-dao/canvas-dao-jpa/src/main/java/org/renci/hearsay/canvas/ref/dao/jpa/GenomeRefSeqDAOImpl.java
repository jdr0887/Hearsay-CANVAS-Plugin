package org.renci.hearsay.canvas.ref.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.ref.dao.GenomeRefSeqDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class GenomeRefSeqDAOImpl extends BaseDAOImpl<GenomeRefSeq, String> implements GenomeRefSeqDAO {

    private final Logger logger = LoggerFactory.getLogger(GenomeRefSeqDAOImpl.class);

    public GenomeRefSeqDAOImpl() {
        super();
    }

    @Override
    public Class<GenomeRefSeq> getPersistentClass() {
        return GenomeRefSeq.class;
    }

    @Override
    public List<GenomeRefSeq> findAll() throws HearsayDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<GenomeRefSeq> query = getEntityManager().createNamedQuery("GenomeRefSeq.findAll", GenomeRefSeq.class);
        List<GenomeRefSeq> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRefSeq> findBySeqType(String seqType) throws HearsayDAOException {
        logger.debug("ENTERING findBySeqType(String)");
        List<GenomeRefSeq> ret = new ArrayList<GenomeRefSeq>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<GenomeRefSeq> crit = critBuilder.createQuery(getPersistentClass());
            Root<GenomeRefSeq> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(critBuilder.equal(root.get(GenomeRefSeq_.seqType), seqType));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            crit.distinct(true);
            TypedQuery<GenomeRefSeq> query = getEntityManager().createQuery(crit);
            ret.addAll(query.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }

}

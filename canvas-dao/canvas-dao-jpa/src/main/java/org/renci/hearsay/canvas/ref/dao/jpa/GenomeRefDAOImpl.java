package org.renci.hearsay.canvas.ref.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.ref.dao.GenomeRefDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq_;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class GenomeRefDAOImpl extends BaseDAOImpl<GenomeRef, Integer> implements GenomeRefDAO {

    private final Logger logger = LoggerFactory.getLogger(GenomeRefDAOImpl.class);

    public GenomeRefDAOImpl() {
        super();
    }

    @Override
    public Class<GenomeRef> getPersistentClass() {
        return GenomeRef.class;
    }

    @Override
    public List<GenomeRef> findByShortName(String shortName) throws HearsayDAOException {
        logger.debug("ENTERING findByShortName()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findByShortName", GenomeRef.class);
        query.setParameter("shortName", shortName);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRef> findAll() throws HearsayDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findAll", GenomeRef.class);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRef> findByGenomeRefSeqVersionAccession(String versionAccession) throws HearsayDAOException {
        logger.debug("ENTERING findByGenomeRefSeqVersionAccession(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GenomeRef> crit = critBuilder.createQuery(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        Root<GenomeRef> fromGenomeRef = crit.from(getPersistentClass());
        Join<GenomeRef, GenomeRefSeq> genomeRefGenomeRefSeqJoin = fromGenomeRef.join(GenomeRef_.genomeRefSeqs);
        predicates.add(critBuilder.equal(genomeRefGenomeRefSeqJoin.get(GenomeRefSeq_.verAccession), versionAccession));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<GenomeRef> query = getEntityManager().createQuery(crit);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRef> findBySeqTypeAndContig(String seqType, String contig) throws HearsayDAOException {
        logger.debug("ENTERING findBySeqTypeAndContig()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findBySeqTypeAndContig", GenomeRef.class);
        query.setParameter("seqType", seqType);
        query.setParameter("contig", contig);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

}

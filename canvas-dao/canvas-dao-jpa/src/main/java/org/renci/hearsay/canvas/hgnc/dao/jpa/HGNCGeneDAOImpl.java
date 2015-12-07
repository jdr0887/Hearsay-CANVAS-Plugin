package org.renci.hearsay.canvas.hgnc.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.hgnc.dao.HGNCGeneDAO;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class HGNCGeneDAOImpl extends BaseDAOImpl<HGNCGene, Integer> implements HGNCGeneDAO {

    private final Logger logger = LoggerFactory.getLogger(HGNCGeneDAOImpl.class);

    public HGNCGeneDAOImpl() {
        super();
    }

    @Override
    public Class<HGNCGene> getPersistentClass() {
        return HGNCGene.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HGNCGene> findByName(String name) throws HearsayDAOException {
        logger.debug("ENTERING findByName(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HGNCGene> crit = critBuilder.createQuery(getPersistentClass());
        Root<HGNCGene> fromHGNCGene = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(fromHGNCGene.get(HGNCGene_.name), name));
        crit.select(fromHGNCGene);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        crit.orderBy(critBuilder.asc(fromHGNCGene.get(HGNCGene_.id)));
        Query query = getEntityManager().createQuery(crit);
        List<HGNCGene> ret = query.getResultList();
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HGNCGene> findBySymbol(String symbol) throws HearsayDAOException {
        logger.debug("ENTERING findBySymbol(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HGNCGene> crit = critBuilder.createQuery(getPersistentClass());
        Root<HGNCGene> fromHGNCGene = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(fromHGNCGene.get(HGNCGene_.symbol), symbol));
        crit.select(fromHGNCGene);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        crit.orderBy(critBuilder.asc(fromHGNCGene.get(HGNCGene_.id)));
        Query query = getEntityManager().createQuery(crit);
        List<HGNCGene> ret = query.getResultList();
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HGNCGene> findByAnnotationGeneExternalIdsNamespace(String namespace) throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersion(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HGNCGene> crit = critBuilder.createQuery(getPersistentClass());

        Root<HGNCGene> fromHGNCGene = crit.from(getPersistentClass());
        Root<AnnotationGeneExternalIds> fromAnnotationGeneExternalIds = crit.from(AnnotationGeneExternalIds.class);

        Predicate condition1 = critBuilder.equal(fromHGNCGene.get(HGNCGene_.id),
                fromAnnotationGeneExternalIds.get(AnnotationGeneExternalIds_.id));
        Predicate condition2 = critBuilder.equal(fromAnnotationGeneExternalIds.get(AnnotationGeneExternalIds_.namespace), namespace);

        crit.select(fromHGNCGene);
        crit.where(condition1, condition2);
        crit.orderBy(critBuilder.asc(fromHGNCGene.get(HGNCGene_.id)));
        Query query = getEntityManager().createQuery(crit);
        // query.setMaxResults(10);
        List<HGNCGene> ret = query.getResultList();
        return ret;
    }
}

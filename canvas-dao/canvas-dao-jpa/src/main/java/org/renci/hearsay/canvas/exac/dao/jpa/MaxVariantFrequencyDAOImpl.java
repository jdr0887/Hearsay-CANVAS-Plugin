package org.renci.hearsay.canvas.exac.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.exac.dao.MaxVariantFrequencyDAO;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency_;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2_;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class MaxVariantFrequencyDAOImpl extends BaseDAOImpl<MaxVariantFrequency, Long> implements MaxVariantFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(MaxVariantFrequencyDAOImpl.class);

    public MaxVariantFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<MaxVariantFrequency> getPersistentClass() {
        return MaxVariantFrequency.class;
    }

    @Override
    public List<MaxVariantFrequency> findByLocationVariantIdAndVersion(Long locVarId, String version) throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, String)");
        TypedQuery<MaxVariantFrequency> query = getEntityManager()
                .createNamedQuery("exac.MaxVariantFrequency.findByLocationVariantIdAndVersion", MaxVariantFrequency.class);
        query.setParameter("locationVariantId", locVarId);
        query.setParameter("version", version);
        List<MaxVariantFrequency> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<MaxVariantFrequency> findByLocationVariantId(Long locVarId) throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantId(Long)");
        List<MaxVariantFrequency> ret = new ArrayList<MaxVariantFrequency>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<MaxVariantFrequency> crit = critBuilder.createQuery(getPersistentClass());
            Root<MaxVariantFrequency> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<MaxVariantFrequency, LocationVariant> maxVariantFrequencyLocationVariantJoin = root
                    .join(MaxVariantFrequency_.locationVariant);
            predicates.add(critBuilder.equal(maxVariantFrequencyLocationVariantJoin.get(LocationVariant_.id), locVarId));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<MaxVariantFrequency> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<MaxVariantFrequency> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup("includeManyToOnes");
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<MaxVariantFrequency> findByLocationVariantIdAndFrequencyThreshold(Long locVarId, Double threshold)
            throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantId(Long)");
        List<MaxVariantFrequency> ret = new ArrayList<MaxVariantFrequency>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<MaxVariantFrequency> crit = critBuilder.createQuery(getPersistentClass());
            Root<MaxVariantFrequency> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<MaxVariantFrequency, LocationVariant> maxVariantFrequencyLocationVariantJoin = root
                    .join(MaxVariantFrequency_.locationVariant);
            predicates.add(critBuilder.equal(maxVariantFrequencyLocationVariantJoin.get(LocationVariant_.id), locVarId));
            Coalesce<Double> maxVariantFrequencyCoalesce = critBuilder.coalesce();
            maxVariantFrequencyCoalesce.value(root.get(MaxVariantFrequency_.maxAlleleFrequency));
            maxVariantFrequencyCoalesce.value(0D);
            predicates.add(critBuilder.lessThanOrEqualTo(maxVariantFrequencyCoalesce, threshold));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<MaxVariantFrequency> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<MaxVariantFrequency> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup("includeManyToOnes");
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<MaxVariantFrequency> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold) throws HearsayDAOException {
        logger.debug("ENTERING findByGeneNameAndMaxAlleleFrequency(String, Double)");
        List<MaxVariantFrequency> ret = new ArrayList<MaxVariantFrequency>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<MaxVariantFrequency> crit = critBuilder.createQuery(getPersistentClass());
            Root<MaxVariantFrequency> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<MaxVariantFrequency, LocationVariant> maxVariantFrequencyLocationVariantJoin = root
                    .join(MaxVariantFrequency_.locationVariant);
            Join<LocationVariant, Variants_61_2> locationVariantVariantsJoin = maxVariantFrequencyLocationVariantJoin
                    .join(LocationVariant_.variants_61_2);
            predicates.add(critBuilder.equal(locationVariantVariantsJoin.get(Variants_61_2_.hgncGene), name));
            Coalesce<Double> maxVariantFrequencyCoalesce = critBuilder.coalesce();
            maxVariantFrequencyCoalesce.value(root.get(MaxVariantFrequency_.maxAlleleFrequency));
            maxVariantFrequencyCoalesce.value(0D);
            predicates.add(critBuilder.lessThanOrEqualTo(maxVariantFrequencyCoalesce, threshold));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<MaxVariantFrequency> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<MaxVariantFrequency> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup("includeManyToOnes");
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}

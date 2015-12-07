package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.clinbin.dao.MaxFreqDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2_;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class MaxFreqDAOImpl extends BaseDAOImpl<MaxFreq, Long> implements MaxFreqDAO {

    private final Logger logger = LoggerFactory.getLogger(MaxFreqDAOImpl.class);

    public MaxFreqDAOImpl() {
        super();
    }

    @Override
    public Class<MaxFreq> getPersistentClass() {
        return MaxFreq.class;
    }

    @Override
    public List<MaxFreq> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold) throws HearsayDAOException {
        logger.debug("ENTERING findByGeneNameAndMaxAlleleFrequency(String, Double)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MaxFreq> crit = critBuilder.createQuery(getPersistentClass());
        Root<MaxFreq> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Join<MaxFreq, LocationVariant> maxVariantFrequencyLocationVariantJoin = root.join(MaxFreq_.locationVariant, JoinType.LEFT);

        Join<LocationVariant, Variants_61_2> locationVariantVariantsJoin = maxVariantFrequencyLocationVariantJoin
                .join(LocationVariant_.variants_61_2, JoinType.LEFT);

        predicates.add(critBuilder.equal(locationVariantVariantsJoin.get(Variants_61_2_.hgncGene), name));

        Coalesce<Double> maxFreqCoalesce = critBuilder.coalesce();
        maxFreqCoalesce.value(root.get(MaxFreq_.maxAlleleFreq));
        maxFreqCoalesce.value(0D);

        predicates.add(critBuilder.lessThanOrEqualTo(maxFreqCoalesce, threshold));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<MaxFreq> query = getEntityManager().createQuery(crit);
        List<MaxFreq> ret = query.getResultList();
        return ret;
    }

}

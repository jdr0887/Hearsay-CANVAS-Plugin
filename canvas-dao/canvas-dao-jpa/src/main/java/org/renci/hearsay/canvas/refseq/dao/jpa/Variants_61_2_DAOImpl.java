package org.renci.hearsay.canvas.refseq.dao.jpa;

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

import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency_;
import org.renci.hearsay.canvas.refseq.dao.Variants_61_2_DAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2_;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant_;
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
        logger.debug("ENTERING findByLocationVariantId(Long)");
        TypedQuery<Variants_61_2> query = getEntityManager().createNamedQuery("Variants_61_2.findByLocationVariantId",
                Variants_61_2.class);
        query.setParameter("locationVariantId", id);
        List<Variants_61_2> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Variants_61_2> findByGeneName(String name) throws HearsayDAOException {
        logger.debug("ENTERING findByGeneName(String)");
        TypedQuery<Variants_61_2> query = getEntityManager().createNamedQuery("Variants_61_2.findByGeneName",
                Variants_61_2.class);
        query.setParameter("geneName", name);
        List<Variants_61_2> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Variants_61_2> findByTranscriptAccession(String accession) throws HearsayDAOException {
        logger.debug("ENTERING findByTranscriptAccession(String)");
        TypedQuery<Variants_61_2> query = getEntityManager().createNamedQuery(
                "Variants_61_2.findByTranscriptAccession", Variants_61_2.class);
        query.setParameter("transcr", accession);
        List<Variants_61_2> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<Variants_61_2> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold)
            throws HearsayDAOException {
        logger.debug("ENTERING findByGeneNameAndMaxAlleleFrequency(String, Double)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Variants_61_2> crit = critBuilder.createQuery(getPersistentClass());
        Root<Variants_61_2> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(critBuilder.equal(root.get(Variants_61_2_.hgncGene), name));

        // Join<Variants_61_2, LocationVariant> variantLocationVariantJoin = root.join(Variants_61_2_.locationVariant,
        // JoinType.LEFT);

        // Join<LocationVariant, MaxVariantFrequency> locationVariantEXACMaxVariantFrequencyJoin =
        // variantLocationVariantJoin
        // .join(LocationVariant_.exacMaxVariantFrequencies, JoinType.LEFT);
        //
        // Coalesce<Double> exacMaxVariantFrequencyCoalesce = critBuilder.coalesce();
        // exacMaxVariantFrequencyCoalesce.value(locationVariantEXACMaxVariantFrequencyJoin
        // .get(MaxVariantFrequency_.maxAlleleFrequency));
        // exacMaxVariantFrequencyCoalesce.value(0D);
        //
        // predicates.add(critBuilder.lessThanOrEqualTo(exacMaxVariantFrequencyCoalesce, threshold));

        // Join<LocationVariant, MaxFreq> locationVariantCLINBINMaxVariantFrequencyJoin =
        // variantLocationVariantJoin.join(
        // LocationVariant_.clinbinMaxVariantFrequencies, JoinType.LEFT);
        //
        // Coalesce<Double> clinbinMaxVariantFrequencyCoalesce = critBuilder.coalesce();
        // clinbinMaxVariantFrequencyCoalesce.value(locationVariantCLINBINMaxVariantFrequencyJoin
        // .get(MaxFreq_.maxAlleleFreq));
        // clinbinMaxVariantFrequencyCoalesce.value(0D);
        //
        // predicates.add(critBuilder.lessThanOrEqualTo(clinbinMaxVariantFrequencyCoalesce, threshold));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<Variants_61_2> query = getEntityManager().createQuery(crit);
        List<Variants_61_2> ret = query.getResultList();
        return ret;
    }

}

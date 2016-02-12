package org.renci.hearsay.canvas.var.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2_;
import org.renci.hearsay.canvas.var.dao.LocationVariantDAO;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class LocationVariantDAOImpl extends BaseDAOImpl<LocationVariant, Long> implements LocationVariantDAO {

    private final Logger logger = LoggerFactory.getLogger(LocationVariantDAOImpl.class);

    public LocationVariantDAOImpl() {
        super();
    }

    @Override
    public Class<LocationVariant> getPersistentClass() {
        return LocationVariant.class;
    }

    @Override
    public List<LocationVariant> findByVersionAccesionAndPosition(String versionAccession, Integer position) throws HearsayDAOException {
        return null;
    }

    @Override
    public List<LocationVariant> findByGeneSymbol(String symbol) throws HearsayDAOException {
        List<LocationVariant> ret = new ArrayList<LocationVariant>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<LocationVariant> crit = critBuilder.createQuery(getPersistentClass());
            Root<LocationVariant> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<LocationVariant, Variants_61_2> locationVariantVariants_61_2Join = root.join(LocationVariant_.variants_61_2);
            predicates.add(critBuilder.equal(locationVariantVariants_61_2Join.get(Variants_61_2_.hgncGene), symbol));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<LocationVariant> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<LocationVariant> openjpaQuery = OpenJPAPersistence.cast(query);
            // openjpaQuery.getFetchPlan().addFetchGroup("includeVariants_61_2");
            openjpaQuery.getFetchPlan().addFetchGroup("includeManyToOnes");
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}

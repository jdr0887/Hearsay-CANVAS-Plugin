package org.renci.hearsay.canvas.refseq.dao.jpa;

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
import org.renci.hearsay.canvas.refseq.dao.RegionGroupRegionDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion_;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class RegionGroupRegionDAOImpl extends BaseDAOImpl<RegionGroupRegion, Integer> implements RegionGroupRegionDAO {

    private static final Logger logger = LoggerFactory.getLogger(RegionGroupRegionDAOImpl.class);

    public RegionGroupRegionDAOImpl() {
        super();
    }

    @Override
    public Class<RegionGroupRegion> getPersistentClass() {
        return RegionGroupRegion.class;
    }

    @Override
    public List<RegionGroupRegion> findByRegionGroupId(Integer regionGroupId) throws HearsayDAOException {
        logger.debug("ENTERING findByRegionGroupId(Long)");
        List<RegionGroupRegion> ret = new ArrayList<RegionGroupRegion>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<RegionGroupRegion> crit = critBuilder.createQuery(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Root<RegionGroupRegion> fromRegionGroupRegion = crit.from(getPersistentClass());
            Join<RegionGroupRegion, RegionGroup> regionGroupRegionGroupRegionJoin = fromRegionGroupRegion
                    .join(RegionGroupRegion_.regionGroup);
            predicates.add(critBuilder.equal(regionGroupRegionGroupRegionJoin.get(RegionGroup_.regionGroupId), regionGroupId));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<RegionGroupRegion> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<RegionGroupRegion> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup("includeManyToOnes");
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }

}

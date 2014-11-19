package org.renci.hearsay.canvas.refseq.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.FeatureDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.Feature_;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureDAOImpl extends BaseDAOImpl<Feature, Long> implements FeatureDAO {

    private final Logger logger = LoggerFactory.getLogger(FeatureDAOImpl.class);

    public FeatureDAOImpl() {
        super();
    }

    @Override
    public Class<Feature> getPersistentClass() {
        return Feature.class;
    }

    @Override
    public List<Feature> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String versionId)
            throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndTranscriptId(String, String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Feature> crit = critBuilder.createQuery(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<Feature> fromFeature = crit.from(getPersistentClass());

        predicates.add(critBuilder.equal(fromFeature.get(Feature_.refseqVer), refSeqVersion));

        Join<Feature, RegionGroup> featureRegionGroupJoin = fromFeature.join(Feature_.regionGroup);
        Join<RegionGroup, Transcript> regionGroupTranscriptJoin = featureRegionGroupJoin.join(RegionGroup_.transcript);
        predicates.add(critBuilder.equal(regionGroupTranscriptJoin.get(Transcript_.versionId), versionId));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Feature> query = getEntityManager().createQuery(crit);
        List<Feature> ret = query.getResultList();

        return ret;
    }

}

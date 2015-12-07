package org.renci.hearsay.canvas.refseq.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.RegionGroupDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.Feature_;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class RegionGroupDAOImpl extends BaseDAOImpl<RegionGroup, Long> implements RegionGroupDAO {

    private final Logger logger = LoggerFactory.getLogger(RegionGroupDAOImpl.class);

    public RegionGroupDAOImpl() {
        super();
    }

    @Override
    public Class<RegionGroup> getPersistentClass() {
        return RegionGroup.class;
    }

    @Override
    public List<RegionGroup> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId) throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndTranscriptId(String, String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RegionGroup> crit = critBuilder.createQuery(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<RegionGroup> fromRegionGroup = crit.from(getPersistentClass());

        Join<RegionGroup, Transcript> regionGroupTranscriptJoin = fromRegionGroup.join(RegionGroup_.transcript);
        predicates.add(critBuilder.equal(regionGroupTranscriptJoin.get(Transcript_.versionId), transcriptId));

        SetJoin<RegionGroup, Feature> regionGroupFeatureJoin = fromRegionGroup.join(RegionGroup_.features);
        predicates.add(critBuilder.equal(regionGroupFeatureJoin.get(Feature_.refseqVer), refSeqVersion));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<RegionGroup> query = getEntityManager().createQuery(crit);
        List<RegionGroup> ret = query.getResultList();

        return ret;
    }

}

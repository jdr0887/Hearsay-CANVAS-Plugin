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
import org.renci.hearsay.canvas.refseq.dao.RefSeqGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene_;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefSeqGeneDAOImpl extends BaseDAOImpl<RefSeqGene, Long> implements RefSeqGeneDAO {

    private final Logger logger = LoggerFactory.getLogger(RefSeqGeneDAOImpl.class);

    public RefSeqGeneDAOImpl() {
        super();
    }

    @Override
    public Class<RefSeqGene> getPersistentClass() {
        return RefSeqGene.class;
    }

    @Override
    public List<RefSeqGene> findByRefSeqVersion(String refSeqVersion) throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersion(String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RefSeqGene> crit = critBuilder.createQuery(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        Root<RefSeqGene> fromRefSeqGene = crit.from(getPersistentClass());
        predicates.add(critBuilder.equal(fromRefSeqGene.get(RefSeqGene_.refseqVersion), refSeqVersion));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<RefSeqGene> query = getEntityManager().createQuery(crit);
        List<RefSeqGene> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<RefSeqGene> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId)
            throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndTranscriptId(String, String)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RefSeqGene> crit = critBuilder.createQuery(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        Root<RefSeqGene> fromRefSeqGene = crit.from(getPersistentClass());
        predicates.add(critBuilder.equal(fromRefSeqGene.get(RefSeqGene_.refseqVersion), refSeqVersion));
        Join<RefSeqGene, RegionGroup> refSeqGeneRegionGroupJoin = fromRefSeqGene.join(RefSeqGene_.locations);
        Join<RegionGroup, Transcript> regionGroupTranscriptJoin = refSeqGeneRegionGroupJoin
                .join(RegionGroup_.transcript);
        predicates.add(critBuilder.equal(regionGroupTranscriptJoin.get(Transcript_.versionId), transcriptId));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<RefSeqGene> query = getEntityManager().createQuery(crit);
        List<RefSeqGene> ret = query.getResultList();
        return ret;
    }
}

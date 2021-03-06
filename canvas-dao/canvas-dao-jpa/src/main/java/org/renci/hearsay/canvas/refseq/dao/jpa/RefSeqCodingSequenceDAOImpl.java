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

import org.apache.commons.lang3.StringUtils;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.RefSeqCodingSequenceDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence_;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class RefSeqCodingSequenceDAOImpl extends BaseDAOImpl<RefSeqCodingSequence, Long> implements RefSeqCodingSequenceDAO {

    private final Logger logger = LoggerFactory.getLogger(RefSeqCodingSequenceDAOImpl.class);

    public RefSeqCodingSequenceDAOImpl() {
        super();
    }

    @Override
    public Class<RefSeqCodingSequence> getPersistentClass() {
        return RefSeqCodingSequence.class;
    }

    @Override
    public List<RefSeqCodingSequence> findByRefSeqVersionAndTranscriptId(String refSeqVersion, String transcriptId)
            throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndTranscriptId(String, String)");
        return findByRefSeqVersionAndTranscriptId(null, refSeqVersion, transcriptId);
    }

    @Override
    public List<RefSeqCodingSequence> findByRefSeqVersionAndTranscriptId(String fetchPlan, String refSeqVersion, String transcriptId)
            throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndTranscriptId(String, String)");
        List<RefSeqCodingSequence> ret = new ArrayList<RefSeqCodingSequence>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<RefSeqCodingSequence> crit = critBuilder.createQuery(getPersistentClass());
            Root<RefSeqCodingSequence> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(critBuilder.equal(root.get(RefSeqCodingSequence_.version), refSeqVersion));
            Join<RefSeqCodingSequence, RegionGroup> locationJoin = root.join(RefSeqCodingSequence_.locations);
            Join<RegionGroup, Transcript> transcriptJoin = locationJoin.join(RegionGroup_.transcript);
            predicates.add(critBuilder.equal(transcriptJoin.get(Transcript_.versionId), transcriptId));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            TypedQuery<RefSeqCodingSequence> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<RefSeqCodingSequence> openjpaQuery = OpenJPAPersistence.cast(query);
            if (StringUtils.isNotEmpty(fetchPlan)) {
                openjpaQuery.getFetchPlan().addFetchGroup(fetchPlan);
            }
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }

    @Override
    public List<RefSeqCodingSequence> findByVersion(String version) throws HearsayDAOException {
        logger.debug("ENTERING findByVersion(String)");
        List<RefSeqCodingSequence> ret = new ArrayList<RefSeqCodingSequence>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<RefSeqCodingSequence> crit = critBuilder.createQuery(getPersistentClass());
            Root<RefSeqCodingSequence> root = crit.from(getPersistentClass());
            Predicate condition1 = critBuilder.equal(root.get(RefSeqCodingSequence_.version), version);
            crit.where(condition1);
            TypedQuery<RefSeqCodingSequence> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<RefSeqCodingSequence> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup("includeAll");
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }
}

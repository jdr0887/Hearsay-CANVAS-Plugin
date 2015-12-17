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
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq_;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps_;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptRefSeqVers;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptRefSeqVers_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class TranscriptMapsDAOImpl extends BaseDAOImpl<TranscriptMaps, Integer> implements TranscriptMapsDAO {

    private final Logger logger = LoggerFactory.getLogger(TranscriptMapsDAOImpl.class);

    public TranscriptMapsDAOImpl() {
        super();
    }

    @Override
    public Class<TranscriptMaps> getPersistentClass() {
        return TranscriptMaps.class;
    }

    @Override
    public List<TranscriptMaps> findByTranscriptId(String versionId) throws HearsayDAOException {
        logger.debug("ENTERING findByTranscriptId(String)");
        List<TranscriptMaps> ret = new ArrayList<TranscriptMaps>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<TranscriptMaps> crit = critBuilder.createQuery(getPersistentClass());
            Root<TranscriptMaps> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<TranscriptMaps, Transcript> transcriptMapsTranscriptJoin = root.join(TranscriptMaps_.transcript);
            predicates.add(critBuilder.equal(transcriptMapsTranscriptJoin.get(Transcript_.versionId), versionId));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            crit.distinct(true);
            TypedQuery<TranscriptMaps> query = getEntityManager().createQuery(crit);
            ret.addAll(query.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }

    @Override
    public List<TranscriptMaps> findByGenomeRefIdAndRefSeqVersion(Integer genomeRefId, String refSeqVersion) throws HearsayDAOException {
        logger.info("ENTERING findByGenomeRefIdAndRefSeqVersion(Integer, String)");
        return findByGenomeRefIdAndRefSeqVersion("includeManyToOnes", genomeRefId, refSeqVersion);
    }

    @Override
    public TranscriptMaps findById(String fetchGroup, Integer id) throws HearsayDAOException {
        logger.debug("ENTERING findById(String, Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TranscriptMaps> crit = critBuilder.createQuery(getPersistentClass());
        Root<TranscriptMaps> root = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(root.get(TranscriptMaps_.id), id));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        TypedQuery<TranscriptMaps> query = getEntityManager().createQuery(crit);
        OpenJPAQuery<TranscriptMaps> openjpaQuery = OpenJPAPersistence.cast(query);
        openjpaQuery.getFetchPlan().addFetchGroup(fetchGroup);
        TranscriptMaps ret = query.getSingleResult();
        return ret;
    }

    @Override
    public List<TranscriptMaps> findByGenomeRefIdAndRefSeqVersion(String fetchGroup, Integer genomeRefId, String refSeqVersion)
            throws HearsayDAOException {
        logger.info("ENTERING findByGenomeRefIdAndRefSeqVersion(String, Integer, String)");
        List<TranscriptMaps> ret = new ArrayList<TranscriptMaps>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<TranscriptMaps> crit = critBuilder.createQuery(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Root<TranscriptMaps> root = crit.from(getPersistentClass());
            predicates.add(critBuilder.equal(root.get(TranscriptMaps_.genomeRefId), genomeRefId));
            Join<TranscriptMaps, GenomeRefSeq> transcriptMapsGenomeRefSeqJoin = root.join(TranscriptMaps_.genomeRefSeq);
            predicates.add(critBuilder.equal(transcriptMapsGenomeRefSeqJoin.get(GenomeRefSeq_.seqType), "Chromosome"));
            predicates.add(
                    critBuilder.equal(critBuilder.substring(transcriptMapsGenomeRefSeqJoin.get(GenomeRefSeq_.verAccession), 1, 3), "NC_"));
            Join<TranscriptMaps, Transcript> transcriptMapsTranscriptJoin = root.join(TranscriptMaps_.transcript);
            predicates.add(critBuilder.or(
                    critBuilder.equal(critBuilder.substring(transcriptMapsTranscriptJoin.get(Transcript_.versionId), 1, 3), "NM_"),
                    critBuilder.equal(critBuilder.substring(transcriptMapsTranscriptJoin.get(Transcript_.versionId), 1, 3), "NR_")));
            Join<Transcript, TranscriptRefSeqVers> transcriptTranscriptRefseqVersJoin = transcriptMapsTranscriptJoin
                    .join(Transcript_.refseqVersions);
            predicates.add(critBuilder.equal(transcriptTranscriptRefseqVersJoin.get(TranscriptRefSeqVers_.refseqVer), refSeqVersion));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            crit.distinct(true);
            TypedQuery<TranscriptMaps> query = getEntityManager().createQuery(crit);
            OpenJPAQuery<TranscriptMaps> openjpaQuery = OpenJPAPersistence.cast(query);
            openjpaQuery.getFetchPlan().addFetchGroup(fetchGroup);
            ret.addAll(openjpaQuery.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }

}

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

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsExonsDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons_;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps_;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptRefSeqVers;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptRefSeqVers_;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class TranscriptMapsExonsDAOImpl extends BaseDAOImpl<TranscriptMapsExons, Integer> implements TranscriptMapsExonsDAO {

    private final Logger logger = LoggerFactory.getLogger(TranscriptMapsExonsDAOImpl.class);

    public TranscriptMapsExonsDAOImpl() {
        super();
    }

    @Override
    public Class<TranscriptMapsExons> getPersistentClass() {
        return TranscriptMapsExons.class;
    }

    @Override
    public List<TranscriptMapsExons> findByTranscriptMapsId(Integer id) throws HearsayDAOException {
        logger.debug("ENTERING findByTranscriptMapsId(Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TranscriptMapsExons> crit = critBuilder.createQuery(getPersistentClass());
        Root<TranscriptMapsExons> root = crit.from(getPersistentClass());
        Join<TranscriptMapsExons, TranscriptMaps> join = root.join(TranscriptMapsExons_.transcriptMaps);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(join.get(TranscriptMaps_.id), id));
        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        // crit.distinct(true);
        TypedQuery<TranscriptMapsExons> query = getEntityManager().createQuery(crit);
        List<TranscriptMapsExons> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<TranscriptMapsExons> findByTranscriptVersionIdAndTranscriptMapsMapCount(String versionId, Integer mapCount)
            throws HearsayDAOException {
        logger.debug("ENTERING findByTranscriptVersionIdAndTranscriptMapsMapCount(String, Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TranscriptMapsExons> crit = critBuilder.createQuery(getPersistentClass());
        Root<TranscriptMapsExons> root = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();

        Join<TranscriptMapsExons, TranscriptMaps> transcriptMapsExonsTranscriptMapsJoin = root.join(TranscriptMapsExons_.transcriptMaps);
        predicates.add(critBuilder.equal(transcriptMapsExonsTranscriptMapsJoin.get(TranscriptMaps_.mapCount), mapCount));

        Join<TranscriptMaps, Transcript> transcriptMapsTranscriptjoin = transcriptMapsExonsTranscriptMapsJoin
                .join(TranscriptMaps_.transcript);
        predicates.add(critBuilder.equal(transcriptMapsTranscriptjoin.get(Transcript_.versionId), versionId));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        // crit.distinct(true);
        TypedQuery<TranscriptMapsExons> query = getEntityManager().createQuery(crit);
        List<TranscriptMapsExons> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<TranscriptMapsExons> findByGenomeRefIdAndRefSeqVersion(Integer genomeRefId, String refSeqVersion)
            throws HearsayDAOException {
        logger.debug("ENTERING findByGenomeRefIdAndRefSeqVersion(Integer, String)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TranscriptMapsExons> crit = critBuilder.createQuery(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<TranscriptMapsExons> root = crit.from(getPersistentClass());
        Join<TranscriptMapsExons, TranscriptMaps> transcriptMapsExonsTranscriptMapsJoin = root.join(TranscriptMapsExons_.transcriptMaps);

        predicates.add(critBuilder.equal(transcriptMapsExonsTranscriptMapsJoin.get(TranscriptMaps_.genomeRefId), genomeRefId));

        Join<TranscriptMaps, Transcript> transcriptMapsTranscriptJoin = transcriptMapsExonsTranscriptMapsJoin
                .join(TranscriptMaps_.transcript);

        Join<Transcript, TranscriptRefSeqVers> TranscriptTranscriptRefseqVersJoin = transcriptMapsTranscriptJoin
                .join(Transcript_.refseqVersions);
        predicates.add(critBuilder.equal(TranscriptTranscriptRefseqVersJoin.get(TranscriptRefSeqVers_.refseqVer), refSeqVersion));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        // crit.distinct(true);

        TypedQuery<TranscriptMapsExons> query = getEntityManager().createQuery(crit);
        List<TranscriptMapsExons> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<TranscriptMapsExons> findByGenomeRefIdAndRefSeqVersionAndAccession(Integer genomeRefId, String refSeqVersion,
            String accession) throws HearsayDAOException {
        logger.debug("ENTERING findByGenomeRefIdAndRefSeqVersionAndAccession(Integer, String, String)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TranscriptMapsExons> crit = critBuilder.createQuery(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<TranscriptMapsExons> root = crit.from(getPersistentClass());
        Join<TranscriptMapsExons, TranscriptMaps> transcriptMapsExonsTranscriptMapsJoin = root.join(TranscriptMapsExons_.transcriptMaps);

        predicates.add(critBuilder.equal(transcriptMapsExonsTranscriptMapsJoin.get(TranscriptMaps_.genomeRefId), genomeRefId));

        Join<TranscriptMaps, Transcript> transcriptMapsTranscriptJoin = transcriptMapsExonsTranscriptMapsJoin
                .join(TranscriptMaps_.transcript);

        predicates.add(critBuilder.equal(transcriptMapsTranscriptJoin.get(Transcript_.versionId), accession));

        Join<Transcript, TranscriptRefSeqVers> transcriptTranscriptRefseqVersJoin = transcriptMapsTranscriptJoin
                .join(Transcript_.refseqVersions);

        predicates.add(critBuilder.equal(transcriptTranscriptRefseqVersJoin.get(TranscriptRefSeqVers_.refseqVer), refSeqVersion));

        crit.where(predicates.toArray(new Predicate[predicates.size()]));
        // crit.distinct(true);

        TypedQuery<TranscriptMapsExons> query = getEntityManager().createQuery(crit);
        List<TranscriptMapsExons> ret = query.getResultList();
        return ret;
    }

}

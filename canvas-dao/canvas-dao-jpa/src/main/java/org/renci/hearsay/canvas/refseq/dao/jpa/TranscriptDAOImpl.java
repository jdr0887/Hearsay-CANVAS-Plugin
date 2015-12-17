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
import org.renci.hearsay.canvas.refseq.dao.TranscriptDAO;
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
public class TranscriptDAOImpl extends BaseDAOImpl<Transcript, String> implements TranscriptDAO {

    private final Logger logger = LoggerFactory.getLogger(TranscriptDAOImpl.class);

    public TranscriptDAOImpl() {
        super();
    }

    @Override
    public Class<Transcript> getPersistentClass() {
        return Transcript.class;
    }

    @Override
    public List<Transcript> findByGenomeRefIdAndRefSeqVersion(Integer genomeRefId, String refSeqVersion) throws HearsayDAOException {
        logger.debug("ENTERING findByGenomeRefAndRefSeqVersion(Integer, String)");
        List<Transcript> ret = new ArrayList<Transcript>();
        try {
            CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Transcript> crit = critBuilder.createQuery(getPersistentClass());
            Root<Transcript> root = crit.from(getPersistentClass());
            List<Predicate> predicates = new ArrayList<Predicate>();
            Join<Transcript, TranscriptMaps> transcriptTranscriptMapsJoin = root.join(Transcript_.transcriptMaps);
            predicates.add(critBuilder.equal(transcriptTranscriptMapsJoin.get(TranscriptMaps_.genomeRefId), genomeRefId));
            Join<Transcript, TranscriptRefSeqVers> transcriptTranscriptRefseqVersJoin = root.join(Transcript_.refseqVersions);
            predicates.add(critBuilder.equal(transcriptTranscriptRefseqVersJoin.get(TranscriptRefSeqVers_.refseqVer), refSeqVersion));
            Join<TranscriptMaps, GenomeRefSeq> transcriptMapsGenomeRefSeqJoin = transcriptTranscriptMapsJoin
                    .join(TranscriptMaps_.genomeRefSeq);
            predicates.add(critBuilder.equal(transcriptMapsGenomeRefSeqJoin.get(GenomeRefSeq_.seqType), "Chromosome"));
            predicates.add(
                    critBuilder.equal(critBuilder.substring(transcriptMapsGenomeRefSeqJoin.get(GenomeRefSeq_.verAccession), 1, 3), "NC_"));
            Join<TranscriptMaps, Transcript> transcriptMapsTranscriptJoin = transcriptTranscriptMapsJoin.join(TranscriptMaps_.transcript);
            predicates.add(critBuilder.or(
                    critBuilder.equal(critBuilder.substring(transcriptMapsTranscriptJoin.get(Transcript_.versionId), 1, 3), "NM_"),
                    critBuilder.equal(critBuilder.substring(transcriptMapsTranscriptJoin.get(Transcript_.versionId), 1, 3), "NR_")));
            crit.where(predicates.toArray(new Predicate[predicates.size()]));
            crit.distinct(true);
            TypedQuery<Transcript> query = getEntityManager().createQuery(crit);
            ret.addAll(query.getResultList());
        } catch (Exception e) {
            throw new HearsayDAOException(e);
        }
        return ret;
    }
}

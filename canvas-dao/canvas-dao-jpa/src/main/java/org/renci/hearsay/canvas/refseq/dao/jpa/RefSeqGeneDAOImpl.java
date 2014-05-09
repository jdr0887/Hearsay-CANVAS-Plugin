package org.renci.hearsay.canvas.refseq.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGene;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds_;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGene_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene_;
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
    public List<RefSeqGene> findByVersion(String version) throws HearsayDAOException {
        logger.debug("ENTERING findByVersion(String)");
        return null;
    }

    @Override
    public List<RefSeqGene> findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndTranscriptId(
            String refSeqVersion, String namespace, String transcriptId) throws HearsayDAOException {
        logger.debug("ENTERING findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndGeneId(String, String, Integer)");
        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RefSeqGene> crit = critBuilder.createQuery(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Root<RefSeqGene> fromRefSeqGene = crit.from(getPersistentClass());

        predicates.add(critBuilder.equal(fromRefSeqGene.get(RefSeqGene_.refseqVersion), refSeqVersion));

        // begin craziness
        Join<RefSeqGene, RegionGroup> refSeqGeneRegionGroupJoin = fromRefSeqGene.join(RefSeqGene_.locations);
        Join<RegionGroup, Transcript> regionGroupTranscriptJoin = refSeqGeneRegionGroupJoin
                .join(RegionGroup_.transcript);
        predicates.add(critBuilder.equal(regionGroupTranscriptJoin.get(Transcript_.versionId), transcriptId));
        Join<Transcript, RegionGroup> transcriptRegionGroupJoin = regionGroupTranscriptJoin
                .join(Transcript_.regionGroups);
        Join<RegionGroup, RefSeqGene> regionGroupRefSeqGeneJoin = transcriptRegionGroupJoin
                .join(RegionGroup_.refSeqGenes);
        predicates.add(critBuilder.equal(regionGroupRefSeqGeneJoin.get(RefSeqGene_.id),
                fromRefSeqGene.get(RefSeqGene_.id)));
        // end craziness

        Root<AnnotationGene> fromAnnotationGene = crit.from(AnnotationGene.class);
        Join<AnnotationGene, AnnotationGeneExternalIds> geneGeneExternalIdsJoin = fromAnnotationGene
                .join(AnnotationGene_.externals);

        predicates.add(critBuilder.equal(geneGeneExternalIdsJoin.get(AnnotationGeneExternalIds_.namespace), namespace));
        predicates.add(critBuilder.equal(geneGeneExternalIdsJoin.get(AnnotationGeneExternalIds_.namespaceVer),
                fromRefSeqGene.get(RefSeqGene_.refseqVersion)));

        predicates.add(critBuilder.equal(fromRefSeqGene.get(RefSeqGene_.id),
                geneGeneExternalIdsJoin.get(AnnotationGeneExternalIds_.id)));

        List<Predicate> subQueryPredicates = new ArrayList<Predicate>();

        Subquery<Long> subquery = crit.subquery(Long.class);
        Root<AnnotationGene> fromAnnotationGeneSub = subquery.from(AnnotationGene.class);
        Join<AnnotationGene, AnnotationGeneExternalIds> fromAnnotationGeneExternalIdsSub = fromAnnotationGeneSub
                .join(AnnotationGene_.externals);
        Root<HGNCGene> fromHGNCGeneSub = subquery.from(HGNCGene.class);
        subquery.select(fromAnnotationGeneSub.get(AnnotationGene_.id));
        subQueryPredicates.add(critBuilder.equal(fromHGNCGeneSub.get(HGNCGene_.id),
                fromAnnotationGeneSub.get(AnnotationGene_.id)));
        subQueryPredicates.add(critBuilder.equal(
                fromAnnotationGeneExternalIdsSub.get(AnnotationGeneExternalIds_.namespace), "HGNC"));
        subquery.where(subQueryPredicates.toArray(new Predicate[subQueryPredicates.size()]));

        predicates.add(critBuilder.in(fromAnnotationGene.get(AnnotationGene_.id)).value(subquery));

        crit.select(fromRefSeqGene).where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<RefSeqGene> query = getEntityManager().createQuery(crit);
        List<RefSeqGene> ret = query.getResultList();

        return ret;
    }
}

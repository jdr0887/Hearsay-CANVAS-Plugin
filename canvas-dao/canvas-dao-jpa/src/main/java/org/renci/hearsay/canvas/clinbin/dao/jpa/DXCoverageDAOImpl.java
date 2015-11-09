package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.renci.hearsay.canvas.clinbin.dao.DXCoverageDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.DX;
import org.renci.hearsay.canvas.clinbin.dao.model.DXCoverage;
import org.renci.hearsay.canvas.clinbin.dao.model.DXCoveragePK;
import org.renci.hearsay.canvas.clinbin.dao.model.DXCoveragePK_;
import org.renci.hearsay.canvas.clinbin.dao.model.DXCoverage_;
import org.renci.hearsay.canvas.clinbin.dao.model.DXExons;
import org.renci.hearsay.canvas.clinbin.dao.model.DXExons_;
import org.renci.hearsay.canvas.clinbin.dao.model.DX_;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticGene;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticGene_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DXCoverageDAOImpl extends BaseDAOImpl<DXCoverage, Long> implements DXCoverageDAO {

    private final Logger logger = LoggerFactory.getLogger(DXCoverageDAOImpl.class);

    public DXCoverageDAOImpl() {
        super();
    }

    @Override
    public Class<DXCoverage> getPersistentClass() {
        return DXCoverage.class;
    }

    @Override
    public List<DXCoverage> findByDXIdAndParticipantAndListVersion(Long dxId, String participant, Integer listVersion)
            throws HearsayDAOException {
        logger.debug("ENTERING findByDXIdAndParticipantAndListVersion(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DXCoverage> crit = critBuilder.createQuery(getPersistentClass());
        Root<DXCoverage> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();
        Join<DXCoverage, DXCoveragePK> dxCoverageDXCoveragePKJoin = root.join(DXCoverage_.key);
        predicates.add(critBuilder.equal(dxCoverageDXCoveragePKJoin.get(DXCoveragePK_.participant), participant));

        Join<DXCoverage, DXExons> coverageExonJoin = root.join(DXCoverage_.exon);
        predicates.add(critBuilder.equal(coverageExonJoin.get(DXExons_.listVersion), listVersion));

        Join<DXExons, DiagnosticGene> coverageDiagnosticGeneJoin = coverageExonJoin.join(DXExons_.gene);
        Join<DiagnosticGene, DX> diagnosticGeneDXJoin = coverageDiagnosticGeneJoin.join(DiagnosticGene_.dx);
        predicates.add(critBuilder.equal(diagnosticGeneDXJoin.get(DX_.id), dxId));
        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<DXCoverage> query = getEntityManager().createQuery(crit);
        List<DXCoverage> ret = query.getResultList();
        return ret;
    }

}

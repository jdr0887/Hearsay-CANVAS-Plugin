package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.renci.hearsay.canvas.clinbin.dao.BinResultsFinalDiagnosticDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic_;
import org.renci.hearsay.canvas.clinbin.dao.model.DX;
import org.renci.hearsay.canvas.clinbin.dao.model.DX_;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticResultVersion;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticResultVersion_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinResultsFinalDiagnosticDAOImpl extends BaseDAOImpl<BinResultsFinalDiagnostic, Long> implements
        BinResultsFinalDiagnosticDAO {

    private final Logger logger = LoggerFactory.getLogger(BinResultsFinalDiagnosticDAOImpl.class);

    public BinResultsFinalDiagnosticDAOImpl() {
        super();
    }

    @Override
    public Class<BinResultsFinalDiagnostic> getPersistentClass() {
        return BinResultsFinalDiagnostic.class;
    }

    @Override
    public List<BinResultsFinalDiagnostic> findByDXIdAndParticipantAndVersion(Long dxId, String participant,
            Integer version) throws HearsayDAOException {
        logger.debug("ENTERING findByDXIdAndParticipantAndListVersion(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BinResultsFinalDiagnostic> crit = critBuilder.createQuery(getPersistentClass());
        Root<BinResultsFinalDiagnostic> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(root.get(BinResultsFinalDiagnostic_.participant), participant));

        Join<BinResultsFinalDiagnostic, DX> binResultsFinalDiagnosticDXJoin = root.join(BinResultsFinalDiagnostic_.dx);
        predicates.add(critBuilder.equal(binResultsFinalDiagnosticDXJoin.get(DX_.id), dxId));

        Join<BinResultsFinalDiagnostic, DiagnosticResultVersion> coverageExonJoin = root
                .join(BinResultsFinalDiagnostic_.diagnosticResultVersion);
        predicates.add(critBuilder.equal(coverageExonJoin.get(DiagnosticResultVersion_.diagnosticResultVersion),
                version));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<BinResultsFinalDiagnostic> query = getEntityManager().createQuery(crit);
        List<BinResultsFinalDiagnostic> ret = query.getResultList();
        return ret;
    }
}

package org.renci.hearsay.canvas.clinvar.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnosticPK;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnosticPK_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalX;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalXPK;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalXPK_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalX_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskX;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskXPK;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskXPK_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskX_;
import org.renci.hearsay.canvas.clinbin.dao.model.DX;
import org.renci.hearsay.canvas.clinbin.dao.model.DX_;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticResultVersion;
import org.renci.hearsay.canvas.clinbin.dao.model.DiagnosticResultVersion_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX_;
import org.renci.hearsay.canvas.clinvar.dao.ReferenceClinicalAssertionsDAO;
import org.renci.hearsay.canvas.clinvar.dao.model.ReferenceClinicalAssertions;
import org.renci.hearsay.canvas.clinvar.dao.model.ReferenceClinicalAssertions_;
import org.renci.hearsay.canvas.clinvar.dao.model.Versions;
import org.renci.hearsay.canvas.clinvar.dao.model.Versions_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant_;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class ReferenceClinicalAssertionsDAOImpl extends BaseDAOImpl<ReferenceClinicalAssertions, Long>
        implements ReferenceClinicalAssertionsDAO {

    private final Logger logger = LoggerFactory.getLogger(ReferenceClinicalAssertionsDAOImpl.class);

    public ReferenceClinicalAssertionsDAOImpl() {
        super();
    }

    @Override
    public Class<ReferenceClinicalAssertions> getPersistentClass() {
        return ReferenceClinicalAssertions.class;
    }

    @Override
    public List<ReferenceClinicalAssertions> findDiagnostic(Long dxId, String participant, Integer resultVersion)
            throws HearsayDAOException {
        logger.debug("ENTERING findDiagnostic(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ReferenceClinicalAssertions> crit = critBuilder.createQuery(getPersistentClass());
        Root<ReferenceClinicalAssertions> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        SetJoin<ReferenceClinicalAssertions, Versions> referenceClinicalAssertionsVersionsJoin = root
                .join(ReferenceClinicalAssertions_.versions, JoinType.LEFT);

        SetJoin<Versions, DiagnosticResultVersion> versionsDiagnosticResultVersionJoin = referenceClinicalAssertionsVersionsJoin
                .join(Versions_.diagnosticResultVersions, JoinType.LEFT);

        predicates.add(critBuilder.equal(versionsDiagnosticResultVersionJoin.get(DiagnosticResultVersion_.diagnosticResultVersion),
                resultVersion));

        Join<DiagnosticResultVersion, BinResultsFinalDiagnostic> diagnosticResultVersionBinResultsFinalDiagnosticJoin = versionsDiagnosticResultVersionJoin
                .join(DiagnosticResultVersion_.binResultsFinalDiagnostics);

        Join<BinResultsFinalDiagnostic, BinResultsFinalDiagnosticPK> binResultsFinalDiagnosticBinResultsFinalDiagnosticPKJoin = diagnosticResultVersionBinResultsFinalDiagnosticJoin
                .join(BinResultsFinalDiagnostic_.key);

        predicates.add(critBuilder.equal(
                binResultsFinalDiagnosticBinResultsFinalDiagnosticPKJoin.get(BinResultsFinalDiagnosticPK_.participant), participant));

        Join<BinResultsFinalDiagnostic, DX> binResultsFinalDiagnosticDXJoin = diagnosticResultVersionBinResultsFinalDiagnosticJoin
                .join(BinResultsFinalDiagnostic_.dx);

        predicates.add(critBuilder.equal(binResultsFinalDiagnosticDXJoin.get(DX_.id), dxId));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<ReferenceClinicalAssertions> query = getEntityManager().createQuery(crit);
        List<ReferenceClinicalAssertions> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<ReferenceClinicalAssertions> findIncidental(Long incidentalBinId, String participant, Integer resultVersion)
            throws HearsayDAOException {
        logger.debug("ENTERING findByIncidental(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ReferenceClinicalAssertions> crit = critBuilder.createQuery(getPersistentClass());
        Root<ReferenceClinicalAssertions> root = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();

        SetJoin<ReferenceClinicalAssertions, Versions> referenceClinicalAssertionsVersionsJoin = root
                .join(ReferenceClinicalAssertions_.versions, JoinType.LEFT);

        SetJoin<Versions, IncidentalResultVersionX> versionsIncidentalResultVersionXJoin = referenceClinicalAssertionsVersionsJoin
                .join(Versions_.incidentalResultVersions, JoinType.LEFT);

        predicates.add(
                critBuilder.equal(versionsIncidentalResultVersionXJoin.get(IncidentalResultVersionX_.binningResultVersion), resultVersion));

        Join<IncidentalResultVersionX, BinResultsFinalIncidentalX> incidentalResultVersionXBinResultsFinalIncidentalXJoin = versionsIncidentalResultVersionXJoin
                .join(IncidentalResultVersionX_.binResultsFinalIncidentals, JoinType.LEFT);
        Join<BinResultsFinalIncidentalX, BinResultsFinalIncidentalXPK> binResultsFinalIncidentalXBinResultsFinalIncidentalXPKJoin = incidentalResultVersionXBinResultsFinalIncidentalXJoin
                .join(BinResultsFinalIncidentalX_.key, JoinType.LEFT);
        predicates.add(critBuilder.equal(
                binResultsFinalIncidentalXBinResultsFinalIncidentalXPKJoin.get(BinResultsFinalIncidentalXPK_.participant), participant));

        Join<BinResultsFinalIncidentalX, IncidentalBinX> binResultsFinalIncidentalXIncidentalBinXJoin = incidentalResultVersionXBinResultsFinalIncidentalXJoin
                .join(BinResultsFinalIncidentalX_.incidentalBin);

        predicates.add(critBuilder.equal(binResultsFinalIncidentalXIncidentalBinXJoin.get(IncidentalBinX_.id), incidentalBinId));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<ReferenceClinicalAssertions> query = getEntityManager().createQuery(crit);
        List<ReferenceClinicalAssertions> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<ReferenceClinicalAssertions> findRisk(Long incidentalBinId, String participant, Integer resultVersion)
            throws HearsayDAOException {
        logger.debug("ENTERING findRisk(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ReferenceClinicalAssertions> crit = critBuilder.createQuery(getPersistentClass());
        Root<ReferenceClinicalAssertions> root = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();

        SetJoin<ReferenceClinicalAssertions, Versions> referenceClinicalAssertionsVersionsJoin = root
                .join(ReferenceClinicalAssertions_.versions, JoinType.LEFT);
        SetJoin<Versions, IncidentalResultVersionX> versionsIncidentalResultVersionXJoin = referenceClinicalAssertionsVersionsJoin
                .join(Versions_.incidentalResultVersions, JoinType.LEFT);
        predicates.add(
                critBuilder.equal(versionsIncidentalResultVersionXJoin.get(IncidentalResultVersionX_.binningResultVersion), resultVersion));

        Join<IncidentalResultVersionX, BinResultsFinalRiskX> incidentalResultVersionXBinResultsFinalRiskXJoin = versionsIncidentalResultVersionXJoin
                .join(IncidentalResultVersionX_.binResultsFinalRisks, JoinType.LEFT);
        Join<BinResultsFinalRiskX, BinResultsFinalRiskXPK> binResultsFinalRiskXBinResultsFinalRiskXPKJoin = incidentalResultVersionXBinResultsFinalRiskXJoin
                .join(BinResultsFinalRiskX_.key, JoinType.LEFT);
        predicates.add(
                critBuilder.equal(binResultsFinalRiskXBinResultsFinalRiskXPKJoin.get(BinResultsFinalRiskXPK_.participant), participant));

        Join<BinResultsFinalRiskX, IncidentalBinX> binResultsFinalIncidentalXIncidentalBinXJoin = incidentalResultVersionXBinResultsFinalRiskXJoin
                .join(BinResultsFinalRiskX_.incidentalBin);

        predicates.add(critBuilder.equal(binResultsFinalIncidentalXIncidentalBinXJoin.get(IncidentalBinX_.id), incidentalBinId));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<ReferenceClinicalAssertions> query = getEntityManager().createQuery(crit);
        List<ReferenceClinicalAssertions> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<ReferenceClinicalAssertions> findByLocationVariantIdAndVersion(Long locVarId, Integer version) throws HearsayDAOException {
        logger.debug("ENTERING findByLocationVariantIdAndVersion(Long, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ReferenceClinicalAssertions> crit = critBuilder.createQuery(getPersistentClass());
        Root<ReferenceClinicalAssertions> root = crit.from(getPersistentClass());
        List<Predicate> predicates = new ArrayList<Predicate>();

        Join<ReferenceClinicalAssertions, LocationVariant> referenceClinicalAssertionsLocationVariantJoin = root
                .join(ReferenceClinicalAssertions_.locationVariant);

        predicates.add(critBuilder.equal(referenceClinicalAssertionsLocationVariantJoin.get(LocationVariant_.id), locVarId));

        SetJoin<ReferenceClinicalAssertions, Versions> referenceClinicalAssertionsVersionsJoin = root
                .join(ReferenceClinicalAssertions_.versions, JoinType.LEFT);

        predicates.add(critBuilder.equal(referenceClinicalAssertionsVersionsJoin.get(Versions_.id), version));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<ReferenceClinicalAssertions> query = getEntityManager().createQuery(crit);
        List<ReferenceClinicalAssertions> ret = query.getResultList();
        return ret;
    }

}

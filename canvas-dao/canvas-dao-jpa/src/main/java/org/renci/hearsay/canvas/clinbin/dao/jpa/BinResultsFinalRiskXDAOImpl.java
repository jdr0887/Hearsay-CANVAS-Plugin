package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.renci.hearsay.canvas.clinbin.dao.BinResultsFinalRiskXDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskX;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskXPK;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskXPK_;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskX_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinResultsFinalRiskXDAOImpl extends BaseDAOImpl<BinResultsFinalRiskX, Long>
        implements BinResultsFinalRiskXDAO {

    private final Logger logger = LoggerFactory.getLogger(BinResultsFinalRiskXDAOImpl.class);

    public BinResultsFinalRiskXDAOImpl() {
        super();
    }

    @Override
    public Class<BinResultsFinalRiskX> getPersistentClass() {
        return BinResultsFinalRiskX.class;
    }

    @Override
    public List<BinResultsFinalRiskX> findByParticipantAndIndicentalBinIdAndResultVersion(String participant,
            Integer incidentalBinId, Integer resultVersion) throws HearsayDAOException {
        logger.debug("ENTERING findByDXIdAndParticipantAndListVersion(Long, String, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BinResultsFinalRiskX> crit = critBuilder.createQuery(getPersistentClass());
        Root<BinResultsFinalRiskX> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();

        Join<BinResultsFinalRiskX, BinResultsFinalRiskXPK> binResultsFinalRiskXBinResultsFinalRiskXPKJoin = root
                .join(BinResultsFinalRiskX_.key);
        predicates.add(critBuilder.equal(
                binResultsFinalRiskXBinResultsFinalRiskXPKJoin.get(BinResultsFinalRiskXPK_.participant), participant));

        Join<BinResultsFinalRiskX, IncidentalBinX> binResultsFinalRiskXIncidentalBinXJoin = root
                .join(BinResultsFinalRiskX_.incidentalBin);
        predicates.add(
                critBuilder.equal(binResultsFinalRiskXIncidentalBinXJoin.get(IncidentalBinX_.id), incidentalBinId));

        Join<BinResultsFinalRiskX, IncidentalResultVersionX> binResultsFinalRiskXIncidentalResultVersionXJoin = root
                .join(BinResultsFinalRiskX_.incidentalResultVersion);
        predicates.add(critBuilder.equal(
                binResultsFinalRiskXIncidentalResultVersionXJoin.get(IncidentalResultVersionX_.binningResultVersion),
                resultVersion));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<BinResultsFinalRiskX> query = getEntityManager().createQuery(crit);
        List<BinResultsFinalRiskX> ret = query.getResultList();
        return ret;
    }
}

package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.renci.hearsay.canvas.clinbin.dao.BinResultsFinalIncidentalXDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalX;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalX_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalBinX_;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX;
import org.renci.hearsay.canvas.clinbin.dao.model.IncidentalResultVersionX_;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinResultsFinalIncidentalXDAOImpl extends BaseDAOImpl<BinResultsFinalIncidentalX, Long> implements
        BinResultsFinalIncidentalXDAO {

    private final Logger logger = LoggerFactory.getLogger(BinResultsFinalIncidentalXDAOImpl.class);

    public BinResultsFinalIncidentalXDAOImpl() {
        super();
    }

    @Override
    public Class<BinResultsFinalIncidentalX> getPersistentClass() {
        return BinResultsFinalIncidentalX.class;
    }

    @Override
    public List<BinResultsFinalIncidentalX> findByParticipantAndIncidentalBinIdAndResultVersion(String participant,
            Integer incidentalBinId, Integer resultVersion) throws HearsayDAOException {
        logger.debug("ENTERING findByParticipantAndIncidentalBinIdAndResultVersion(String, Integer, Integer)");

        CriteriaBuilder critBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BinResultsFinalIncidentalX> crit = critBuilder.createQuery(getPersistentClass());
        Root<BinResultsFinalIncidentalX> root = crit.from(getPersistentClass());

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(critBuilder.equal(root.get(BinResultsFinalIncidentalX_.participant), participant));

        Join<BinResultsFinalIncidentalX, IncidentalBinX> binResultsFinalIncidentalXIncidentalBinXJoin = root
                .join(BinResultsFinalIncidentalX_.incidentalBin);
        predicates.add(critBuilder.equal(binResultsFinalIncidentalXIncidentalBinXJoin.get(IncidentalBinX_.id),
                incidentalBinId));

        Join<BinResultsFinalIncidentalX, IncidentalResultVersionX> binResultsFinalIncidentalXIncidentalResultVersionXJoin = root
                .join(BinResultsFinalIncidentalX_.incidentalResultVersion);
        predicates.add(critBuilder.equal(binResultsFinalIncidentalXIncidentalResultVersionXJoin
                .get(IncidentalResultVersionX_.binningResultVersion), resultVersion));

        crit.distinct(true);
        crit.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<BinResultsFinalIncidentalX> query = getEntityManager().createQuery(crit);
        List<BinResultsFinalIncidentalX> ret = query.getResultList();
        return ret;
    }
}

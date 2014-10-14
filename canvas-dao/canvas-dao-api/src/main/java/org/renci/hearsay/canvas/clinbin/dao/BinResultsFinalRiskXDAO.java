package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalRiskX;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface BinResultsFinalRiskXDAO extends BaseDAO<BinResultsFinalRiskX, Long> {

    public List<BinResultsFinalRiskX> findByParticipantAndIndicentalBinIdAndResultVersion(String participant,
            Integer incidentalBinId, Integer resultVersion) throws HearsayDAOException;

}

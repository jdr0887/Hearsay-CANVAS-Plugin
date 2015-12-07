package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalIncidentalX;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface BinResultsFinalIncidentalXDAO extends BaseDAO<BinResultsFinalIncidentalX, Long> {

    public List<BinResultsFinalIncidentalX> findByParticipantAndIncidentalBinIdAndResultVersion(String participant, Integer incidentalBinId,
            Integer version) throws HearsayDAOException;

}

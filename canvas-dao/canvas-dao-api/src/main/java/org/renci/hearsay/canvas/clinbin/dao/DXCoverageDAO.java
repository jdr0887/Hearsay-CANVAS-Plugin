package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.DXCoverage;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface DXCoverageDAO extends BaseDAO<DXCoverage, Long> {

    public List<DXCoverage> findByDXIdAndParticipantAndListVersion(Long dxId, String participant, Integer listVersion)
            throws HearsayDAOException;

}

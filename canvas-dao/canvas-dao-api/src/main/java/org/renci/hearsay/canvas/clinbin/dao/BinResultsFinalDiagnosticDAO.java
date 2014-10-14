package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface BinResultsFinalDiagnosticDAO extends BaseDAO<BinResultsFinalDiagnostic, Long> {

    public List<BinResultsFinalDiagnostic> findByDXIdAndParticipantAndVersion(Long dxId, String participant,
            Integer version) throws HearsayDAOException;

}

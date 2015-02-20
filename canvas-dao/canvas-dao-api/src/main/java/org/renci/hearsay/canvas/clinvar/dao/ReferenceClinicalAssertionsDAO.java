package org.renci.hearsay.canvas.clinvar.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinvar.dao.model.ReferenceClinicalAssertions;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface ReferenceClinicalAssertionsDAO extends BaseDAO<ReferenceClinicalAssertions, Long> {

    public List<ReferenceClinicalAssertions> findDiagnostic(Long dxId, String participant, Integer resultVersion)
            throws HearsayDAOException;

    public List<ReferenceClinicalAssertions> findIncidental(Long incidentalBinId, String participant,
            Integer resultVersion) throws HearsayDAOException;

    public List<ReferenceClinicalAssertions> findRisk(Long incidentalBinId, String participant, Integer resultVersion)
            throws HearsayDAOException;

    public List<ReferenceClinicalAssertions> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

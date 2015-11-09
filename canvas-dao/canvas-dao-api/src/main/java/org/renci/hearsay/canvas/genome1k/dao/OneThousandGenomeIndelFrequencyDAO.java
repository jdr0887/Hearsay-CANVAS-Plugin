package org.renci.hearsay.canvas.genome1k.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.genome1k.dao.model.OneThousandGenomeIndelFrequency;
import org.renci.hearsay.dao.HearsayDAOException;

public interface OneThousandGenomeIndelFrequencyDAO extends BaseDAO<OneThousandGenomeIndelFrequency, Long> {

    public List<OneThousandGenomeIndelFrequency> findByLocationVariantIdAndVersion(Long locVarId, Integer version)
            throws HearsayDAOException;

}

package org.renci.hearsay.canvas.exac.dao.jpa;

import java.util.List;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.exac.dao.VariantFrequencyDAO;
import org.renci.hearsay.canvas.exac.dao.model.VariantFrequency;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariantFrequencyDAOImpl extends BaseDAOImpl<VariantFrequency, Long> implements VariantFrequencyDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantFrequencyDAOImpl.class);

    public VariantFrequencyDAOImpl() {
        super();
    }

    @Override
    public Class<VariantFrequency> getPersistentClass() {
        return VariantFrequency.class;
    }

    @Override
    public List<VariantFrequency> findByLocationVariantIdAndVersionAndPopulation(Long locVarId, String version,
            String population) throws HearsayDAOException {
        return null;
    }

}

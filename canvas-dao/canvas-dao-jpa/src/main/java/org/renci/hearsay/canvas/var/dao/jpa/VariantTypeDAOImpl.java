package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.VariantTypeDAO;
import org.renci.hearsay.canvas.var.dao.model.VariantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class VariantTypeDAOImpl extends BaseDAOImpl<VariantType, Long> implements VariantTypeDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantTypeDAOImpl.class);

    public VariantTypeDAOImpl() {
        super();
    }

    @Override
    public Class<VariantType> getPersistentClass() {
        return VariantType.class;
    }

}

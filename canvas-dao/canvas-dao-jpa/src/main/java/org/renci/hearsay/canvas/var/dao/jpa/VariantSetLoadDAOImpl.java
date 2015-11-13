package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.VariantSetLoadDAO;
import org.renci.hearsay.canvas.var.dao.model.VariantSetLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class VariantSetLoadDAOImpl extends BaseDAOImpl<VariantSetLoad, Long> implements VariantSetLoadDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantSetLoadDAOImpl.class);

    public VariantSetLoadDAOImpl() {
        super();
    }

    @Override
    public Class<VariantSetLoad> getPersistentClass() {
        return VariantSetLoad.class;
    }

}

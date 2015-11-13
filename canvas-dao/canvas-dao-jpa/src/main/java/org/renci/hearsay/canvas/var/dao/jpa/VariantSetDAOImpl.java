package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.VariantSetDAO;
import org.renci.hearsay.canvas.var.dao.model.VariantSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class VariantSetDAOImpl extends BaseDAOImpl<VariantSet, Long> implements VariantSetDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantSetDAOImpl.class);

    public VariantSetDAOImpl() {
        super();
    }

    @Override
    public Class<VariantSet> getPersistentClass() {
        return VariantSet.class;
    }

}

package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.VariantSetLocationDAO;
import org.renci.hearsay.canvas.var.dao.model.VariantSetLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class VariantSetLocationDAOImpl extends BaseDAOImpl<VariantSetLocation, Long> implements VariantSetLocationDAO {

    private final Logger logger = LoggerFactory.getLogger(VariantSetLocationDAOImpl.class);

    public VariantSetLocationDAOImpl() {
        super();
    }

    @Override
    public Class<VariantSetLocation> getPersistentClass() {
        return VariantSetLocation.class;
    }

}

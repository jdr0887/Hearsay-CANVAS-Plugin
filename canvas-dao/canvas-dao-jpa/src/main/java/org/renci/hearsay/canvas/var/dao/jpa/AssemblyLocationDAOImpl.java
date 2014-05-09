package org.renci.hearsay.canvas.var.dao.jpa;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.AssemblyLocationDAO;
import org.renci.hearsay.canvas.var.dao.model.AssemblyLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssemblyLocationDAOImpl extends BaseDAOImpl<AssemblyLocation, Long> implements AssemblyLocationDAO {

    private final Logger logger = LoggerFactory.getLogger(AssemblyLocationDAOImpl.class);

    public AssemblyLocationDAOImpl() {
        super();
    }

    @Override
    public Class<AssemblyLocation> getPersistentClass() {
        return AssemblyLocation.class;
    }

}

package org.renci.hearsay.canvas.ref.dao.jpa;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.ref.dao.GenomeRefDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenomeRefDAOImpl extends BaseDAOImpl<GenomeRef, Long> implements GenomeRefDAO {

    private final Logger logger = LoggerFactory.getLogger(GenomeRefDAOImpl.class);

    public GenomeRefDAOImpl() {
        super();
    }

    @Override
    public Class<GenomeRef> getPersistentClass() {
        return GenomeRef.class;
    }

}

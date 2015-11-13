package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.AssemblyDAO;
import org.renci.hearsay.canvas.var.dao.model.Assembly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class AssemblyDAOImpl extends BaseDAOImpl<Assembly, Long> implements AssemblyDAO {

    private final Logger logger = LoggerFactory.getLogger(AssemblyDAOImpl.class);

    public AssemblyDAOImpl() {
        super();
    }

    @Override
    public Class<Assembly> getPersistentClass() {
        return Assembly.class;
    }

}

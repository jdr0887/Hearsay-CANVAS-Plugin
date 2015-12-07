package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.LabDAO;
import org.renci.hearsay.canvas.var.dao.model.Lab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class LabDAOImpl extends BaseDAOImpl<Lab, Long> implements LabDAO {

    private final Logger logger = LoggerFactory.getLogger(LabDAOImpl.class);

    public LabDAOImpl() {
        super();
    }

    @Override
    public Class<Lab> getPersistentClass() {
        return Lab.class;
    }

}

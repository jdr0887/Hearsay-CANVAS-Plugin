package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.SampleDAO;
import org.renci.hearsay.canvas.var.dao.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class SampleDAOImpl extends BaseDAOImpl<Sample, Long> implements SampleDAO {

    private final Logger logger = LoggerFactory.getLogger(SampleDAOImpl.class);

    public SampleDAOImpl() {
        super();
    }

    @Override
    public Class<Sample> getPersistentClass() {
        return Sample.class;
    }

}

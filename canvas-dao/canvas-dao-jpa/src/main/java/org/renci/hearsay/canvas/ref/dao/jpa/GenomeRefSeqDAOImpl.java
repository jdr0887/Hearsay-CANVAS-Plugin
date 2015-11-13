package org.renci.hearsay.canvas.ref.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.ref.dao.GenomeRefSeqDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class GenomeRefSeqDAOImpl extends BaseDAOImpl<GenomeRefSeq, Long> implements GenomeRefSeqDAO {

    private final Logger logger = LoggerFactory.getLogger(GenomeRefSeqDAOImpl.class);

    public GenomeRefSeqDAOImpl() {
        super();
    }

    @Override
    public Class<GenomeRefSeq> getPersistentClass() {
        return GenomeRefSeq.class;
    }

    @Override
    public List<GenomeRefSeq> findAll() throws HearsayDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<GenomeRefSeq> query = getEntityManager().createNamedQuery("GenomeRefSeq.findAll",
                GenomeRefSeq.class);
        List<GenomeRefSeq> ret = query.getResultList();
        return ret;
    }

}

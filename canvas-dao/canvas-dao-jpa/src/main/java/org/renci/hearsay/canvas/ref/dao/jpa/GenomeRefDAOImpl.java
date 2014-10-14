package org.renci.hearsay.canvas.ref.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.ref.dao.GenomeRefDAO;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.dao.HearsayDAOException;
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

    @Override
    public List<GenomeRef> findByShortName(String shortName) throws HearsayDAOException {
        logger.debug("ENTERING findByShortName()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findByShortName", GenomeRef.class);
        query.setParameter("shortName", shortName);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRef> findByName(String name) throws HearsayDAOException {
        logger.debug("ENTERING findByName()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findByName", GenomeRef.class);
        query.setParameter("name", name);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

    @Override
    public List<GenomeRef> findBySeqTypeAndContig(String seqType, String contig) throws HearsayDAOException {
        logger.debug("ENTERING findBySeqTypeAndContig()");
        TypedQuery<GenomeRef> query = getEntityManager().createNamedQuery("GenomeRef.findBySeqTypeAndContig",
                GenomeRef.class);
        query.setParameter("seqType", seqType);
        query.setParameter("contig", contig);
        List<GenomeRef> ret = query.getResultList();
        return ret;
    }

}

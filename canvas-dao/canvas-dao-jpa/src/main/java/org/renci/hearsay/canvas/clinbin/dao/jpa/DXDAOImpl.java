package org.renci.hearsay.canvas.clinbin.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.clinbin.dao.DXDAO;
import org.renci.hearsay.canvas.clinbin.dao.model.DX;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public class DXDAOImpl extends BaseDAOImpl<DX, Long> implements DXDAO {

    private final Logger logger = LoggerFactory.getLogger(DXDAOImpl.class);

    public DXDAOImpl() {
        super();
    }

    @Override
    public Class<DX> getPersistentClass() {
        return DX.class;
    }

    @Override
    public List<DX> findAll() throws HearsayDAOException {
        logger.debug("ENTERING findAll()");
        TypedQuery<DX> query = getEntityManager().createNamedQuery("DX.findAll", DX.class);
        List<DX> ret = query.getResultList();
        return ret;
    }

}

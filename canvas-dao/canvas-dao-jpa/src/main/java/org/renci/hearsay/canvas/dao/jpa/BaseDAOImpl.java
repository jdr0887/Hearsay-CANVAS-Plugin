package org.renci.hearsay.canvas.dao.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public abstract class BaseDAOImpl<T extends Persistable, ID extends Serializable> implements BaseDAO<T, ID> {

    private final Logger logger = LoggerFactory.getLogger(BaseDAOImpl.class);

    @PersistenceContext(name = "canvas", unitName = "canvas")
    private EntityManager entityManager;

    public BaseDAOImpl() {
        super();
    }

    public abstract Class<T> getPersistentClass();

    @Override
    public T findById(ID id) throws HearsayDAOException {
        logger.debug("ENTERING findById(T)");
        T ret = entityManager.find(getPersistentClass(), id);
        return ret;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

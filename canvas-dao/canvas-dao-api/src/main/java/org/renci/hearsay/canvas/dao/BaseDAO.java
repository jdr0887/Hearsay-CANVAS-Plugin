package org.renci.hearsay.canvas.dao;

import java.io.Serializable;

import org.renci.hearsay.dao.HearsayDAOException;

public interface BaseDAO<T extends Persistable, ID extends Serializable> {
    
    /**
     * 
     * @param id
     * @return
     */
    public abstract T findById(ID id) throws HearsayDAOException;

}

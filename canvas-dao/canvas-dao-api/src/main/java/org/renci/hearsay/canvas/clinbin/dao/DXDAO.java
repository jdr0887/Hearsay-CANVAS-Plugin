package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.DX;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface DXDAO extends BaseDAO<DX, Long> {

    public List<DX> findAll() throws HearsayDAOException;

}

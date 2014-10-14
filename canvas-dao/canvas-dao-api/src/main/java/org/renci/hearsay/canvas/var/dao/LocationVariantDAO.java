package org.renci.hearsay.canvas.var.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOException;

public interface LocationVariantDAO extends BaseDAO<LocationVariant, Long> {

    public List<LocationVariant> findByVersionAccesionAndPosition(String versionAccession, Integer position)
            throws HearsayDAOException;

}

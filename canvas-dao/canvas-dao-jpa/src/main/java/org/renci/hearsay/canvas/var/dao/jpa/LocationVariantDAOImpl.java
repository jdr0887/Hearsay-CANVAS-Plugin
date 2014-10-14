package org.renci.hearsay.canvas.var.dao.jpa;

import java.util.List;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.LocationVariantDAO;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationVariantDAOImpl extends BaseDAOImpl<LocationVariant, Long> implements LocationVariantDAO {

    private final Logger logger = LoggerFactory.getLogger(LocationVariantDAOImpl.class);

    public LocationVariantDAOImpl() {
        super();
    }

    @Override
    public Class<LocationVariant> getPersistentClass() {
        return LocationVariant.class;
    }

    @Override
    public List<LocationVariant> findByVersionAccesionAndPosition(String versionAccession, Integer position)
            throws HearsayDAOException {
        return null;
    }

}

package org.renci.hearsay.canvas.annotation.dao;

import java.util.List;

import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface AnnotationGeneExternalIdsDAO extends BaseDAO<AnnotationGeneExternalIds, Integer> {

    public List<AnnotationGeneExternalIds> findByNamespace(String namespace) throws HearsayDAOException;

    public List<AnnotationGeneExternalIds> findByNamespaceAndNamespaceVersion(String namespace, String version)
            throws HearsayDAOException;

}

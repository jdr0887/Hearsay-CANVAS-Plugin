package org.renci.hearsay.canvas.annotation.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneExternalIdsDAO;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationGeneExternalIdsDAOImpl extends BaseDAOImpl<AnnotationGeneExternalIds, Integer> implements
        AnnotationGeneExternalIdsDAO {

    private final Logger logger = LoggerFactory.getLogger(AnnotationGeneExternalIdsDAOImpl.class);

    public AnnotationGeneExternalIdsDAOImpl() {
        super();
    }

    @Override
    public Class<AnnotationGeneExternalIds> getPersistentClass() {
        return AnnotationGeneExternalIds.class;
    }

    @Override
    public List<AnnotationGeneExternalIds> findByNamespace(String namespace) throws HearsayDAOException {
        logger.debug("ENTERING findByNamespace(String)");
        TypedQuery<AnnotationGeneExternalIds> query = getEntityManager().createNamedQuery(
                "AnnotationGeneExternalIds.findByNamespace", AnnotationGeneExternalIds.class);
        query.setParameter("namespace", namespace);
        List<AnnotationGeneExternalIds> ret = query.getResultList();
        return ret;
    }

}

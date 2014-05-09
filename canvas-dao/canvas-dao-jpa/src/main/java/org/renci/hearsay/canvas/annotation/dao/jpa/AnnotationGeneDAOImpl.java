package org.renci.hearsay.canvas.annotation.dao.jpa;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneDAO;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGene;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationGeneDAOImpl extends BaseDAOImpl<AnnotationGene, Long> implements AnnotationGeneDAO {

    private final Logger logger = LoggerFactory.getLogger(AnnotationGeneDAOImpl.class);

    public AnnotationGeneDAOImpl() {
        super();
    }

    @Override
    public Class<AnnotationGene> getPersistentClass() {
        return AnnotationGene.class;
    }

}

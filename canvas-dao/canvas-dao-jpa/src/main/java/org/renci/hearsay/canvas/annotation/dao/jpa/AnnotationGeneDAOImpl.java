package org.renci.hearsay.canvas.annotation.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneDAO;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGene;
import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
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

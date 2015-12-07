package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.AssemblyLocationVariantDAO;
import org.renci.hearsay.canvas.var.dao.model.AssemblyLocationVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class AssemblyLocationVariantDAOImpl extends BaseDAOImpl<AssemblyLocationVariant, Long> implements AssemblyLocationVariantDAO {

    private final Logger logger = LoggerFactory.getLogger(AssemblyLocationVariantDAOImpl.class);

    public AssemblyLocationVariantDAOImpl() {
        super();
    }

    @Override
    public Class<AssemblyLocationVariant> getPersistentClass() {
        return AssemblyLocationVariant.class;
    }

}

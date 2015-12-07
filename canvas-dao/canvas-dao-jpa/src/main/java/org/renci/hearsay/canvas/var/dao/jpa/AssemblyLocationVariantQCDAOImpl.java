package org.renci.hearsay.canvas.var.dao.jpa;

import javax.transaction.Transactional;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.AssemblyLocationVariantQCDAO;
import org.renci.hearsay.canvas.var.dao.model.AssemblyLocationVariantQC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional(Transactional.TxType.SUPPORTS)
public class AssemblyLocationVariantQCDAOImpl extends BaseDAOImpl<AssemblyLocationVariantQC, Long> implements AssemblyLocationVariantQCDAO {

    private final Logger logger = LoggerFactory.getLogger(AssemblyLocationVariantQCDAOImpl.class);

    public AssemblyLocationVariantQCDAOImpl() {
        super();
    }

    @Override
    public Class<AssemblyLocationVariantQC> getPersistentClass() {
        return AssemblyLocationVariantQC.class;
    }

}

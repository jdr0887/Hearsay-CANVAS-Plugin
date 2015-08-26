package org.renci.hearsay.canvas.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.renci.hearsay.canvas.refseq.dao.Variants_61_2_DAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.ws.Variants_61_2_Service;
import org.renci.hearsay.dao.HearsayDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Variants_61_2_ServiceImpl implements Variants_61_2_Service {

    private static final Logger logger = LoggerFactory.getLogger(Variants_61_2_ServiceImpl.class);

    private Variants_61_2_DAO variants_61_2_DAO;

    public Variants_61_2_ServiceImpl() {
        super();
    }

    @Override
    public List<Variants_61_2> findByGeneNameAndMaxAlleleFrequency(String geneName, Double threshold) {
        logger.debug("ENTERING findByName(String)");
        List<Variants_61_2> ret = new ArrayList<Variants_61_2>();
        try {
            ret.addAll(variants_61_2_DAO.findByGeneNameAndMaxAlleleFrequency(geneName, threshold));
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Variants_61_2_DAO getVariants_61_2_DAO() {
        return variants_61_2_DAO;
    }

    public void setVariants_61_2_DAO(Variants_61_2_DAO variants_61_2_DAO) {
        this.variants_61_2_DAO = variants_61_2_DAO;
    }

}

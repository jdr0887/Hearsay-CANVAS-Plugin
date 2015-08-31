package org.renci.hearsay.canvas.refseq.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.refseq.dao.jpa.Variants_61_2_DAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOException;

public class Variant_61_2Test {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void testFindByName() {
        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);

        try {
            List<Variants_61_2> variants = variantDAO.findByGeneName("BRCA1");
            if (CollectionUtils.isNotEmpty(variants)) {
                for (Variants_61_2 variant : variants) {
                    LocationVariant locationVariant = variant.getLocationVariant();
                    
                }
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFindByGeneNameAndMaxAlleleFrequency() {

        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);

        try {
            List<Variants_61_2> variants = variantDAO.findByGeneNameAndMaxAlleleFrequency("BRCA1", 0.05);
            if (CollectionUtils.isNotEmpty(variants)) {
                int count = 0;
                for (Variants_61_2 variant : variants) {
                    LocationVariant locationVariant = variant.getLocationVariant();
                    List<MaxFreq> clinbinMaxFrequencies = locationVariant.getClinbinMaxVariantFrequencies();
                    count += clinbinMaxFrequencies.size();
                    List<MaxVariantFrequency> exacMaxFrequencies = locationVariant.getExacMaxVariantFrequencies();
                    count += exacMaxFrequencies.size();
                }
                System.out.println(count);
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

    }

}

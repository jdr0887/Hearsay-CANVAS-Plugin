package org.renci.hearsay.canvas.refseq.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections4.CollectionUtils;
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
    public void testFindByLocationVariantId() throws HearsayDAOException {
        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);
        List<Variants_61_2> variants = variantDAO.findByLocationVariantId(491812292L);
        assertTrue(CollectionUtils.isNotEmpty(variants));
        variants.forEach(a -> {
            System.out.println(a.toString());
            System.out.println(a.getLocationVariant().toString());
        });
    }

    @Test
    public void testFindByName() throws HearsayDAOException {
        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);
        List<Variants_61_2> variants = variantDAO.findByGeneName("BRCA1");
        assertTrue(CollectionUtils.isNotEmpty(variants));
        variants.forEach(a -> {
            System.out.println(a.getLocationVariant().toString());
        });
    }

    @Test
    public void testFindByGeneNameAndMaxAlleleFrequency() throws HearsayDAOException {

        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);

        List<Variants_61_2> variants = variantDAO.findByGeneNameAndMaxAlleleFrequency("BRCA1", 0.05);
        assertTrue(CollectionUtils.isNotEmpty(variants));
        int count = 0;
        for (Variants_61_2 variant : variants) {
            LocationVariant locationVariant = variant.getLocationVariant();
            List<MaxFreq> clinbinMaxFrequencies = locationVariant.getMaxFreqs();
            count += clinbinMaxFrequencies.size();
            List<MaxVariantFrequency> exacMaxFrequencies = locationVariant.getMaxVariantFrequencies();
            count += exacMaxFrequencies.size();
        }
        System.out.println(count);

    }

}

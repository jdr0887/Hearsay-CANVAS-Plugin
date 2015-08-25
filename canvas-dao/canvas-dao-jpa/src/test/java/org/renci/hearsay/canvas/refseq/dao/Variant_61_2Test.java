package org.renci.hearsay.canvas.refseq.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.Variants_61_2_DAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
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
    public void testFindByGeneNameAndMaxAlleleFrequency() {
        Variants_61_2_DAOImpl variant_61_2DAO = new Variants_61_2_DAOImpl();
        variant_61_2DAO.setEntityManager(em);
        try {
            List<Variants_61_2> results = variant_61_2DAO.findByGeneNameAndMaxAlleleFrequency("BRCA1", 0.05);
            assertTrue(CollectionUtils.isNotEmpty(results));
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
    }

}

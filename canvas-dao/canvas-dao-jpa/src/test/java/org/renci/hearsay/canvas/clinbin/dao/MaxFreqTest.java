package org.renci.hearsay.canvas.clinbin.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.clinbin.dao.jpa.MaxFreqDAOImpl;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.refseq.dao.jpa.Variants_61_2_DAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.dao.HearsayDAOException;

public class MaxFreqTest {

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
        MaxFreqDAOImpl maxFreqDAO = new MaxFreqDAOImpl();
        maxFreqDAO.setEntityManager(em);

        Variants_61_2_DAOImpl variants_61_2_DAO = new Variants_61_2_DAOImpl();
        variants_61_2_DAO.setEntityManager(em);

        try {
            List<MaxFreq> clinbinMaxFreqList = maxFreqDAO.findByGeneNameAndMaxAlleleFrequency("BRCA1", 0.05);
            assertTrue(CollectionUtils.isNotEmpty(clinbinMaxFreqList));
            int count = 0;
            System.out.println(clinbinMaxFreqList.size());
            for (MaxFreq maxFreq : clinbinMaxFreqList) {
                List<Variants_61_2> variants = variants_61_2_DAO.findByLocationVariantId(maxFreq.getLocationVariant()
                        .getId());
                assertTrue(CollectionUtils.isNotEmpty(variants));
                System.out.println(variants.size());
                count += variants.size();
            }
            System.out.println(count);
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
    }
}

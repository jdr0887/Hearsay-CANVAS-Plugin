package org.renci.hearsay.canvas.clinvar.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.clinvar.dao.jpa.ReferenceClinicalAssertionsDAOImpl;
import org.renci.hearsay.canvas.clinvar.dao.model.ReferenceClinicalAssertions;
import org.renci.hearsay.dao.HearsayDAOException;

public class ReferenceClinicalAssertionsDAOTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindDiagnostic() {
        ReferenceClinicalAssertionsDAOImpl referenceClinicalAssertionsDAO = new ReferenceClinicalAssertionsDAOImpl();
        referenceClinicalAssertionsDAO.setEntityManager(em);
        try {
            List<ReferenceClinicalAssertions> results = referenceClinicalAssertionsDAO.findDiagnostic(22L, "NCG_00064",
                    16);
            assertTrue(results != null);
            assertTrue(!results.isEmpty());
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindByLocVarIdAndVersion() {
        ReferenceClinicalAssertionsDAOImpl referenceClinicalAssertionsDAO = new ReferenceClinicalAssertionsDAOImpl();
        referenceClinicalAssertionsDAO.setEntityManager(em);
        try {
            List<ReferenceClinicalAssertions> results = referenceClinicalAssertionsDAO.findByLocationVariantIdAndVersion(404269787L, 4);
            assertTrue(results != null);
            assertTrue(!results.isEmpty());
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
    }

    
    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

}

package org.renci.hearsay.canvas.clinbin.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.clinbin.dao.jpa.BinResultsFinalDiagnosticDAOImpl;
import org.renci.hearsay.canvas.clinbin.dao.model.BinResultsFinalDiagnostic;
import org.renci.hearsay.dao.HearsayDAOException;

public class BinResultsFinalDiagnosticDAOTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindByParticipantAndListVersion() {
        BinResultsFinalDiagnosticDAOImpl binResultsFinalDiagnosticDAO = new BinResultsFinalDiagnosticDAOImpl();
        binResultsFinalDiagnosticDAO.setEntityManager(em);
        try {
            List<BinResultsFinalDiagnostic> results = binResultsFinalDiagnosticDAO.findByDXIdAndParticipantAndVersion(
                    22L, "NCG_00064", 16);
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

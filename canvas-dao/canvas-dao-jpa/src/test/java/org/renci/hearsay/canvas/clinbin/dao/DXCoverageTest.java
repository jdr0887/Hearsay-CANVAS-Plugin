package org.renci.hearsay.canvas.clinbin.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.clinbin.dao.jpa.DXCoverageDAOImpl;
import org.renci.hearsay.canvas.clinbin.dao.model.DXCoverage;
import org.renci.hearsay.dao.HearsayDAOException;

public class DXCoverageTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindByParticipantAndListVersion() {
        DXCoverageDAOImpl dxCoverageDAO = new DXCoverageDAOImpl();
        dxCoverageDAO.setEntityManager(em);
        try {
            List<DXCoverage> results = dxCoverageDAO.findByDXIdAndParticipantAndListVersion(29L, "NCG_00040", 3);
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

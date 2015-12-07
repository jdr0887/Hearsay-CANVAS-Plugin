package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOException;

public class RefSeqGeneTest {

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
    public void testFindByRefSeqVersion() throws HearsayDAOException {
        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);
        List<RefSeqGene> refSeqGeneList = refSeqGeneDAO.findByRefSeqVersion("61");
        refSeqGeneList.forEach(a -> System.out.println(a.toString()));
    }

    @Test
    public void testFindByRefSeqVersionAndTranscriptId() throws HearsayDAOException {
        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);
        List<RefSeqGene> refSeqGeneList = refSeqGeneDAO.findByRefSeqVersionAndTranscriptId("61", "NM_001101330.1");
        refSeqGeneList.forEach(a -> System.out.println(a.toString()));
    }

}

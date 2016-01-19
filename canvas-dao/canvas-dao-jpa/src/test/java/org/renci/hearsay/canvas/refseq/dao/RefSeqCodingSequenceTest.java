package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.dao.HearsayDAOException;

public class RefSeqCodingSequenceTest {

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
    public void testFindByRefSeqVersionAndTranscriptId() throws HearsayDAOException {
        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);
        List<RefSeqCodingSequence> refSeqCodingSequenceList = refSeqCodingSequenceDAO.findByRefSeqVersionAndTranscriptId("61", "NM_001101330.1");
        refSeqCodingSequenceList.forEach(a -> System.out.println(a.toString()));
    }

}

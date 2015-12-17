package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.dao.HearsayDAOException;

public class TranscriptTest {

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
    public void testFindByGenomeRefIdAndRefSeqVersion() throws HearsayDAOException {
        TranscriptDAOImpl transcriptDAO = new TranscriptDAOImpl();
        transcriptDAO.setEntityManager(em);
        List<Transcript> transcriptList = transcriptDAO.findByGenomeRefIdAndRefSeqVersion(2, "61");
        System.out.println(transcriptList.size());
        //transcriptList.forEach(a -> System.out.printf("%s%n", a.toString()));
    }

}

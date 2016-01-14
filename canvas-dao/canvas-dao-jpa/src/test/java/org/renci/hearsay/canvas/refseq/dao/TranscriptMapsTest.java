package org.renci.hearsay.canvas.refseq.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.dao.HearsayDAOException;

public class TranscriptMapsTest {

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
        TranscriptMapsDAOImpl transcriptMapsDAO = new TranscriptMapsDAOImpl();
        transcriptMapsDAO.setEntityManager(em);
        List<TranscriptMaps> transcriptMapsList = transcriptMapsDAO.findByGenomeRefIdAndRefSeqVersion("includeAll", 2, "61");
        Set<Transcript> transcriptSet = new HashSet<Transcript>();
        transcriptMapsList.forEach(a -> {
            if (!transcriptSet.contains(a.getTranscript())) {
                transcriptSet.add(a.getTranscript());
            }
        });
        System.out.println(transcriptSet.size());
    }

    @Test
    public void testFindByVersionId() throws HearsayDAOException {
        TranscriptMapsDAOImpl transcriptMapsDAO = new TranscriptMapsDAOImpl();
        transcriptMapsDAO.setEntityManager(em);
        // NM_002105.2
        List<TranscriptMaps> transcriptMapsList = transcriptMapsDAO.findByTranscriptId("XM_005274819.1");
        transcriptMapsList.forEach(a -> {
            System.out.println(a.getTranscript().toString());
            System.out.println(a.getGenomeRefSeq().toString());
            System.out.println(a.toString());
            System.out.println("----------------");
        });
    }

}

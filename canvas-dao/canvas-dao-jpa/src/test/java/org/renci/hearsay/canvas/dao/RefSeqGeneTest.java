package org.renci.hearsay.canvas.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.annotation.dao.jpa.AnnotationGeneExternalIdsDAOImpl;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
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

    @Test
    public void testFindByRefSeqVersionAndTranscriptId() {

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        AnnotationGeneExternalIdsDAOImpl annotationGeneExternalIdsDAO = new AnnotationGeneExternalIdsDAOImpl();
        annotationGeneExternalIdsDAO.setEntityManager(em);

        try {
            List<RefSeqGene> refSeqGeneList = refSeqGeneDAO.findByRefSeqVersionAndTranscriptId("61", "NM_001013354.1");
            assertTrue(refSeqGeneList != null);
            assertTrue(refSeqGeneList.size() > 0);
            AnnotationGeneExternalIds annotatedGene = annotationGeneExternalIdsDAO.findById(refSeqGeneList.get(0)
                    .getId().intValue());
            assertTrue(annotatedGene != null);

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

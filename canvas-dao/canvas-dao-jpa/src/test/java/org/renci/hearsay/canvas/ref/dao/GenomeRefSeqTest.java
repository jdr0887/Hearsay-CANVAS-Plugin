package org.renci.hearsay.canvas.ref.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.ref.dao.jpa.GenomeRefSeqDAOImpl;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.dao.HearsayDAOException;

public class GenomeRefSeqTest {

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
    public void testFindAll() throws HearsayDAOException {
        GenomeRefSeqDAOImpl genomeRefSeqDAO = new GenomeRefSeqDAOImpl();
        genomeRefSeqDAO.setEntityManager(em);
        List<GenomeRefSeq> genomeRefSeqList = genomeRefSeqDAO.findAll();
        genomeRefSeqList.forEach(a -> System.out.println(a.toString()));
    }

    @Test
    public void testFindBySeqType() throws HearsayDAOException {
        GenomeRefSeqDAOImpl genomeRefSeqDAO = new GenomeRefSeqDAOImpl();
        genomeRefSeqDAO.setEntityManager(em);
        List<GenomeRefSeq> genomeRefSeqList = genomeRefSeqDAO.findBySeqType("Chromosome");
        genomeRefSeqList.forEach(a -> System.out.println(a.toString()));
    }

}

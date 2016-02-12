package org.renci.hearsay.canvas.var.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.var.dao.jpa.LocationVariantDAOImpl;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOException;

public class LocationVariantTest {

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
    public void testFindByGeneSymbol() throws HearsayDAOException {
        LocationVariantDAOImpl locationVariantDAO = new LocationVariantDAOImpl();
        locationVariantDAO.setEntityManager(em);
        List<LocationVariant> locationVariantList = locationVariantDAO.findByGeneSymbol("BRCA1");
        locationVariantList.forEach(a -> System.out.println(a.toString()));
    }

}

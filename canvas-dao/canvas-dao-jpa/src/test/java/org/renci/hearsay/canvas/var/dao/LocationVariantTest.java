package org.renci.hearsay.canvas.var.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

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

}

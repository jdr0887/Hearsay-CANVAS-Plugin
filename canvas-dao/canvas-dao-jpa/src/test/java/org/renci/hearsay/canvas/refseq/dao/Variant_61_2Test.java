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
import org.renci.hearsay.canvas.refseq.dao.jpa.Variants_61_2_DAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOException;

public class Variant_61_2Test {

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
    public void testFindByName() {
        Variants_61_2_DAOImpl variantDAO = new Variants_61_2_DAOImpl();
        variantDAO.setEntityManager(em);

        Set<LocationVariant> locationVariantSet = new HashSet<LocationVariant>();

        try {
            List<Variants_61_2> variants = variantDAO.findByGeneName("DRD3");
            if (variants != null && !variants.isEmpty()) {
                for (Variants_61_2 variant : variants) {
                    LocationVariant locationVariant = variant.getLocationVariant();
                    if (!locationVariantSet.contains(locationVariant)) {
                        locationVariantSet.add(locationVariant);
                    }
                }

                for (LocationVariant lv : locationVariantSet) {
                    System.out.println(lv.toString());
                }

                System.out.println(variants.size());
                System.out.println(locationVariantSet.size());

            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

    }

}

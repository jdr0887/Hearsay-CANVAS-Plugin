package org.renci.hearsay.canvas.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOException;

public class RefSeqCodingSequenceTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testTranscriptionSave() {
        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);
        try {
            List<RefSeqCodingSequence> results = refSeqCodingSequenceDAO.findByVersion("48");
            if (results != null && !results.isEmpty()) {
                for (RefSeqCodingSequence cds : results) {
                    System.out.println(cds.toString());
                    Set<RegionGroup> rgSet = cds.getLocations();
                    if (rgSet != null && !rgSet.isEmpty()) {
                        for (RegionGroup rg : rgSet) {
                            System.out.println(rg.toString());
                            Set<RegionGroupRegion> rgrSet = rg.getRegionGroupRegions();
                            if (rgrSet != null && !rgrSet.isEmpty()) {
                                for (RegionGroupRegion rgr : rgrSet) {
                                    System.out.println(rgr.toString());
                                }
                            }
                        }
                    }
                }
            }
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

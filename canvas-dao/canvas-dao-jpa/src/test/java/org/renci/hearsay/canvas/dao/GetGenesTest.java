package org.renci.hearsay.canvas.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.hgnc.dao.jpa.HGNCGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;

public class GetGenesTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindByNamespace() {
        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        HGNCGeneDAOImpl hgncGeneDAO = new HGNCGeneDAOImpl();
        hgncGeneDAO.setEntityManager(em);

        List<RefSeqGene> results = null;
        // try {
        // results = refSeqGeneDAO.findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndTranscriptId("61",
        // "refseq","XM_005258393.1");
        // } catch (HearsayDAOException e) {
        // e.printStackTrace();
        // }

        if (results != null && results.size() > 0) {

            for (RefSeqGene gene : results) {

                Set<RegionGroup> rgSet = gene.getLocations();
                if (rgSet != null && !rgSet.isEmpty()) {

                    for (RegionGroup rg : rgSet) {

                        Set<RegionGroupRegion> rgrSet = rg.getRegionGroupRegions();
                        if (rgrSet != null && !rgrSet.isEmpty()) {

                            for (RegionGroupRegion rgr : rgrSet) {
                                System.out.println(String.format("%s\t%s\t%s\t%s", gene.getName(), rg.getTranscript().getVersionId(),
                                        rgr.getKey().getRegionStart().toString(), rgr.getKey().getRegionEnd().toString()));
                            }

                        }

                    }

                }

            }

        }

    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

}

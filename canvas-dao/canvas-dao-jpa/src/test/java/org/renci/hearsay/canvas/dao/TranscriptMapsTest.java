package org.renci.hearsay.canvas.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;

public class TranscriptMapsTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindByVersionId() throws HearsayDAOException {

        TranscriptMapsDAOImpl transcriptMapsDAO = new TranscriptMapsDAOImpl();
        transcriptMapsDAO.setEntityManager(em);

        List<TranscriptMaps> transcriptMapsList = transcriptMapsDAO.findByVersionId("NM_014743.2");
        transcriptMapsList.forEach(a -> System.out.println(a.toString()));

        // RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        // refSeqCodingSequenceDAO.setEntityManager(em);
        //
        // RegionGroupRegionDAOImpl regionGroupRegionDAO = new RegionGroupRegionDAOImpl();
        // regionGroupRegionDAO.setEntityManager(em);
        //
        // List<RefSeqCodingSequence> refSeqCodingSequenceList =
        // refSeqCodingSequenceDAO.findByRefSeqVersionAndTranscriptId("61",
        // "NM_014743.2");
        // for (RefSeqCodingSequence cds : refSeqCodingSequenceList) {
        // Set<RegionGroup> regionGroupSet = cds.getLocations();
        // RegionGroup regionGroup = regionGroupSet.iterator().next();
        //
        // List<RegionGroupRegion> regionGroupRegionList =
        // regionGroupRegionDAO.findByRegionGroupId(regionGroup.getRegionGroupId());
        //
        // if (CollectionUtils.isNotEmpty(regionGroupRegionList)) {
        // // should only be on here
        // RegionGroupRegion rgr = regionGroupRegionList.get(0);
        // System.out.println(rgr.toString());
        // }
        // }
    }

    @Test
    public void testFindById() throws HearsayDAOException {

        TranscriptMapsDAOImpl transcriptMapsDAO = new TranscriptMapsDAOImpl();
        transcriptMapsDAO.setEntityManager(em);

        TranscriptMaps transcriptMaps = transcriptMapsDAO.findById("includeAll", 198697);
        System.out.println(transcriptMaps.toString());
        if (CollectionUtils.isNotEmpty(transcriptMaps.getExons())) {
            List<TranscriptMapsExons> exons = transcriptMaps.getExons();
            exons.sort((a, b) -> a.getTranscrStart().compareTo(b.getTranscrStart()));
            exons.forEach(a -> System.out.println(a.toString()));
        }
        transcriptMaps = transcriptMapsDAO.findById("includeAll", 151120);
        System.out.println(transcriptMaps.toString());
        if (CollectionUtils.isNotEmpty(transcriptMaps.getExons())) {
            List<TranscriptMapsExons> exons = transcriptMaps.getExons();
            exons.sort((a, b) -> b.getTranscrStart().compareTo(a.getTranscrStart()));
            exons.forEach(a -> System.out.println(a.toString()));
        }
    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

}

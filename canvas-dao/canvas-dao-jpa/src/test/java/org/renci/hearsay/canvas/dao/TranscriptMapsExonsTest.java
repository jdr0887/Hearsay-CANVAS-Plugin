package org.renci.hearsay.canvas.dao;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.CreateTranscriptListCallable;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.MappedTranscript;
import org.renci.hearsay.dao.model.Region;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;

public class TranscriptMapsExonsTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testPlusStrand() {

        TranscriptMapsExonsDAOImpl transcriptMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptMapsExonsDAO.setEntityManager(em);

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);

        CANVASDAOBean canvasDAOBean = new CANVASDAOBean();
        canvasDAOBean.setTranscriptMapsExonsDAO(transcriptMapsExonsDAO);
        canvasDAOBean.setRefSeqGeneDAO(refSeqGeneDAO);
        canvasDAOBean.setRefSeqCodingSequenceDAO(refSeqCodingSequenceDAO);

        String refSeqVersion = "61";
        Integer genomeRefId = 2;

        try {

            List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();

            mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "XM_005277470.1"));

            Callable<List<org.renci.hearsay.dao.model.Transcript>> persistTranscriptRunnable = new CreateTranscriptListCallable(
                    refSeqVersion, canvasDAOBean, null, mapsExonsResults);
            List<org.renci.hearsay.dao.model.Transcript> results = persistTranscriptRunnable.call();

            for (org.renci.hearsay.dao.model.Transcript transcript : results) {

                assertTrue(transcript.getAccession().equals("XM_005277470.1"));
                Set<MappedTranscript> mappedTranscriptSet = transcript.getMappedTranscripts();
                for (MappedTranscript mappedTranscript : mappedTranscriptSet) {
                    TreeSet<Region> sortedRegionSet = new TreeSet<Region>(new Comparator<Region>() {

                        @Override
                        public int compare(Region a, Region b) {
                            int ret = a.getRegionStart().compareTo(b.getRegionStart());
                            return ret;
                        }

                    });
                    sortedRegionSet.addAll(mappedTranscript.getRegions());
                    for (Region region : sortedRegionSet) {
                        System.out.println(region.toString());
                    }
                    System.out.println(mappedTranscript.toString());
                    if (mappedTranscript.getStrandType().equals(StrandType.PLUS)) {
                        assertTrue(sortedRegionSet.first().getRegionStart().equals(145292225));
                        assertTrue(sortedRegionSet.first().getRegionStop().equals(145293268));
                        assertTrue(sortedRegionSet.first().getTranscriptStart().equals(1));
                        assertTrue(sortedRegionSet.first().getTranscriptStop().equals(1044));
                        // assertTrue(sortedRegionSet.last().getRegionStart().equals(146466122));
                        // assertTrue(sortedRegionSet.last().getRegionStop().equals(146467639));
                        // assertTrue(sortedRegionSet.last().getTranscriptStart().equals(12387));
                        // assertTrue(sortedRegionSet.last().getTranscriptStop().equals(13904));
                    }
                }
                System.out.println(transcript.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMinusStrand() {

        TranscriptMapsExonsDAOImpl transcriptMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptMapsExonsDAO.setEntityManager(em);

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);

        CANVASDAOBean canvasDAOBean = new CANVASDAOBean();
        canvasDAOBean.setTranscriptMapsExonsDAO(transcriptMapsExonsDAO);
        canvasDAOBean.setRefSeqGeneDAO(refSeqGeneDAO);
        canvasDAOBean.setRefSeqCodingSequenceDAO(refSeqCodingSequenceDAO);

        String refSeqVersion = "61";
        Integer genomeRefId = 2;

        try {

            List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();

            mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_033663.3"));

            Callable<List<org.renci.hearsay.dao.model.Transcript>> persistTranscriptRunnable = new CreateTranscriptListCallable(
                    refSeqVersion, canvasDAOBean, null, mapsExonsResults);
            List<org.renci.hearsay.dao.model.Transcript> results = persistTranscriptRunnable.call();

            for (org.renci.hearsay.dao.model.Transcript transcript : results) {

                assertTrue(transcript.getAccession().equals("NM_033663.3"));
                Set<MappedTranscript> mappedTranscriptSet = transcript.getMappedTranscripts();
                for (MappedTranscript mappedTranscript : mappedTranscriptSet) {
                    TreeSet<Region> sortedRegionSet = new TreeSet<Region>(new Comparator<Region>() {

                        @Override
                        public int compare(Region a, Region b) {
                            int ret = a.getRegionStart().compareTo(b.getRegionStart());
                            return ret;
                        }

                    });
                    sortedRegionSet.addAll(mappedTranscript.getRegions());
                    for (Region region : sortedRegionSet) {
                        System.out.println(region.toString());
                    }
                    System.out.println(mappedTranscript.toString());
                    if (mappedTranscript.getStrandType().equals(StrandType.MINUS)) {
                        assertTrue(sortedRegionSet.last().getRegionStart().equals(113897504));
                        assertTrue(sortedRegionSet.last().getRegionStop().equals(113897899));
                        assertTrue(sortedRegionSet.last().getTranscriptStart().equals(396));
                        assertTrue(sortedRegionSet.last().getTranscriptStop().equals(1));
                        assertTrue(sortedRegionSet.first().getRegionStart().equals(113847557));
                        assertTrue(sortedRegionSet.first().getRegionStop().equals(113847759));
                        assertTrue(sortedRegionSet.first().getTranscriptStart().equals(1541));
                        assertTrue(sortedRegionSet.first().getTranscriptStop().equals(1339));
                    }
                }
                System.out.println(transcript.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

}

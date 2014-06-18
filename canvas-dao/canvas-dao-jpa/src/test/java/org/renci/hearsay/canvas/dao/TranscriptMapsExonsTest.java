package org.renci.hearsay.canvas.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.dao.model.Exon;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.MappingResult;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;

public class TranscriptMapsExonsTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("test-canvas", null);
        em = emf.createEntityManager();
    }

    @Test
    public void testFindByGenomeRefIdAndRefSeqVersion() {

        String refSeqVersion = "61";
        Integer genomeRefId = 2;

        TranscriptMapsExonsDAOImpl transcriptionMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptionMapsExonsDAO.setEntityManager(em);

        try {
            List<TranscriptMapsExons> mapsExonsResults = transcriptionMapsExonsDAO.findByGenomeRefIdAndRefSeqVersion(
                    genomeRefId, refSeqVersion);

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                System.out.println(mapsExonsResults.size());// 1306946
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPullRefSeq() {

        TranscriptMapsExonsDAOImpl transcriptMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptMapsExonsDAO.setEntityManager(em);

        File topFile = new File("/home/jdr0887/tmp/canvas", "java-top.txt");
        File detailsFile = new File("/home/jdr0887/tmp/canvas", "java-details.txt");

        String refSeqVersion = "61";
        Integer genomeRefId = 2;

        BufferedWriter topBufferedWriter = null;
        BufferedWriter detailsBufferedWriter = null;
        try {

            topBufferedWriter = new BufferedWriter(new FileWriter(topFile));
            detailsBufferedWriter = new BufferedWriter(new FileWriter(detailsFile));

            List<TranscriptMapsExons> mapsExonsResults = transcriptMapsExonsDAO.findByGenomeRefIdAndRefSeqVersion(
                    genomeRefId, refSeqVersion);

            Set<Transcript> transcriptSet = new HashSet<Transcript>();

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();

                for (TranscriptMapsExons exon : mapsExonsResults) {

                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    transcriptSet.add(transcriptionMaps.getTranscript());

                    GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();

                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());

                    if (!map.containsKey(mappingKey)) {
//                        map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession()));
                    }
                }

                System.out.printf("map.size(): %d%n", map.size());

                for (TranscriptMapsExons exon : mapsExonsResults) {

                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();

                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());

//                    map.get(mappingKey)
//                            .getExons()
//                            .add(new Exon(exon.getKey().getExonNum(), exon.getTranscrStart(), exon.getTranscrEnd(),
//                                    exon.getContigStart(), exon.getContigEnd()));
                }

                List<MappingResult> mappingResults = new ArrayList<MappingResult>();

                for (MappingKey key : map.keySet()) {
                    Mapping mapping = map.get(key);
                    // List<Exon> exons = mapping.getExons();
                    // mappingResults.add(new MappingResult(mapping.getVersionAccession(), exons.get(0).toRange()
                    // .getMinimumInteger(), exons.get(exons.size() - 1).toRange().getMaximumInteger()));
                }

                System.out.printf("mappingResults.size(): %d%n", mappingResults.size());

                Collections.sort(mappingResults, new Comparator<MappingResult>() {

                    @Override
                    public int compare(MappingResult o1, MappingResult o2) {
                        int ret = o1.getAccession().compareTo(o2.getAccession());
                        if (ret == 0)
                            ret = o1.getStart().compareTo(o2.getStart());
                        return ret;
                    }

                });

                // for (MappingResult result : mappingResults) {
                // topBufferedWriter.write(result.toString());
                // topBufferedWriter.flush();
                // }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        } finally {
            try {
                topBufferedWriter.close();
                detailsBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @AfterClass
    public static void tearDown() {
        em.close();
        emf.close();
    }

}

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

import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.MappingResult;
import org.renci.hearsay.canvas.hgnc.dao.jpa.HGNCGeneDAOImpl;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;

public class PullRefSeq implements Runnable {

    @Override
    public void run() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-canvas", null);
        EntityManager em = emf.createEntityManager();

        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        HGNCGeneDAOImpl hgncGeneDAO = new HGNCGeneDAOImpl();
        hgncGeneDAO.setEntityManager(em);

        TranscriptDAOImpl transcriptDAO = new TranscriptDAOImpl();
        transcriptDAO.setEntityManager(em);

        TranscriptMapsDAOImpl transcriptMapsDAO = new TranscriptMapsDAOImpl();
        transcriptMapsDAO.setEntityManager(em);

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

            List<TranscriptMapsExons> mapsExonsResults = transcriptMapsExonsDAO.findByGenomeRefIdAndRefSeqVersion(genomeRefId,
                    refSeqVersion);

            Set<Transcript> transcriptSet = new HashSet<Transcript>();

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();

                for (TranscriptMapsExons exon : mapsExonsResults) {

                    TranscriptMaps transcriptMaps = exon.getTranscriptMaps();
                    transcriptSet.add(transcriptMaps.getTranscript());

                    GenomeRefSeq genomeRefSeq = transcriptMaps.getGenomeRefSeq();

                    MappingKey mappingKey = new MappingKey(transcriptMaps.getTranscript().getVersionId(), transcriptMaps.getMapCount());

                    // if (!map.containsKey(mappingKey)) {
                    // map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession()));
                    // }

                }

                for (TranscriptMapsExons exon : mapsExonsResults) {

                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();

                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());

                    // map.get(mappingKey)
                    // .getExons()
                    // .add(new Exon(exon.getKey().getExonNum(), exon.getTranscrStart(), exon.getTranscrEnd(),
                    // exon.getContigStart(), exon.getContigEnd()));
                }

                List<MappingResult> mappingResults = new ArrayList<MappingResult>();

                for (MappingKey key : map.keySet()) {
                    Mapping mapping = map.get(key);
                    // List<Exon> exons = mapping.getExons();
                    // mappingResults.add(new MappingResult(mapping.getVersionAccession(), exons.get(0).toRange()
                    // .getMinimumInteger(), exons.get(exons.size() - 1).toRange().getMaximumInteger()));

                }

                Collections.sort(mappingResults, new Comparator<MappingResult>() {

                    @Override
                    public int compare(MappingResult o1, MappingResult o2) {
                        int ret = o1.getAccession().compareTo(o2.getAccession());
                        if (ret == 0)
                            ret = o1.getStart().compareTo(o2.getStart());
                        return ret;
                    }

                });

                for (MappingResult result : mappingResults) {
                    topBufferedWriter.write(result.toString());
                    topBufferedWriter.flush();
                }

                for (Transcript transcript : transcriptSet) {

                    List<RefSeqCodingSequence> refSeqCodingSequenceResults = refSeqCodingSequenceDAO
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, transcript.getVersionId());

                    // List<RefSeqGene> refSeqGeneResults = refSeqGeneDAO
                    // .findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndTranscriptId(refSeqVersion,
                    // "refseq", transcript.getVersionId());
                    // if (refSeqGeneResults != null && !refSeqGeneResults.isEmpty()) {
                    // for (RefSeqGene refSeqGene : refSeqGeneResults) {
                    //
                    // }
                    // }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            try {
                topBufferedWriter.close();
                detailsBufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        PullRefSeq runnable = new PullRefSeq();
        runnable.run();
    }

}

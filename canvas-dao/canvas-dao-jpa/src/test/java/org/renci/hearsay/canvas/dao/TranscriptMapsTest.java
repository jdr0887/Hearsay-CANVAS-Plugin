package org.renci.hearsay.canvas.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.hgnc.dao.jpa.HGNCGeneDAOImpl;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.RegionType;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.TranscriptInterval;

public class TranscriptMapsTest {

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

        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(em);

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(em);

        HGNCGeneDAOImpl hgncGeneDAO = new HGNCGeneDAOImpl();
        hgncGeneDAO.setEntityManager(em);

        TranscriptMapsDAOImpl transcriptionMapsDAO = new TranscriptMapsDAOImpl();
        transcriptionMapsDAO.setEntityManager(em);

        TranscriptMapsExonsDAOImpl transcriptionMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptionMapsExonsDAO.setEntityManager(em);

        try {
            List<TranscriptMaps> mapResults = transcriptionMapsDAO.findByGenomeRefIdAndRefSeqVersion(genomeRefId,
                    refSeqVersion);

            System.out.printf("transcriptionMaps.size(): %d%n", mapResults.size());// 131857

            Map<String, Integer> map = new HashMap<String, Integer>();

            if (mapResults != null && mapResults.size() > 0) {
                for (TranscriptMaps transcriptionMaps : mapResults) {
                    map.put(transcriptionMaps.getTranscript().getVersionId(), transcriptionMaps.getMapCount());
                }
            }

            System.out.printf("versionId/mapCount size: %d%n", map.size());// 91179

            Map<String, List<TranscriptMapsExons>> accessionExonMap = new HashMap<String, List<TranscriptMapsExons>>();

            for (String versionId : map.keySet()) {
                Integer mapCount = map.get(versionId);
                List<TranscriptMapsExons> exons = transcriptionMapsExonsDAO
                        .findByTranscriptVersionIdAndTranscriptMapsMapCount(versionId, mapCount);

                for (TranscriptMapsExons exon : exons) {
                    String key = exon.getTranscriptMaps().getGenomeRefSeq().getVerAccession();
                    if (!accessionExonMap.containsKey(key)) {
                        accessionExonMap.put(key, new ArrayList<TranscriptMapsExons>());
                    }
                    accessionExonMap.get(key).add(exon);
                }
            }

            System.out.printf("accessionExonMap.size(): %d%n", accessionExonMap.size());
            for (String key : accessionExonMap.keySet()) {
                System.out.println(key);
            }

            for (String key : accessionExonMap.keySet()) {

                List<TranscriptMapsExons> exons = accessionExonMap.get(key);
                Collections.sort(exons, new Comparator<TranscriptMapsExons>() {

                    @Override
                    public int compare(TranscriptMapsExons first, TranscriptMapsExons second) {
                        int ret = first.getContigStart().compareTo(second.getContigStart());
                        return ret;
                    }

                });
                Transcript transcript = exons.get(0).getTranscriptMaps().getTranscript();
                Integer lowerBound = exons.get(0).toRange().getMinimumInteger();
                Integer upperBound = exons.get(exons.size() - 1).toRange().getMaximumInteger();
                String rsAccession = transcript.getVersionId();
                String strand = exons.get(0).getTranscriptMaps().getStrand();

                List<RefSeqGene> refSeqGeneResults = refSeqGeneDAO
                        .findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndTranscriptId(refSeqVersion,
                                "refseq", transcript.getVersionId());
                RefSeqGene refSeqGene = null;
                if (refSeqGeneResults != null && !refSeqGeneResults.isEmpty()) {
                    refSeqGene = refSeqGeneResults.get(0);
                }

                Long geneId = refSeqGene.getId();
                String geneName = refSeqGene.getName();
                HGNCGene hgncGene = hgncGeneDAO.findById(geneId.intValue());
                String geneSymbol = hgncGene.getSymbol();

                Integer mapCount = exons.get(0).getTranscriptMaps().getMapCount();
                Integer nMap = exons.size();

                List<RefSeqCodingSequence> refSeqCodingSequenceResults = refSeqCodingSequenceDAO
                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, transcript.getVersionId());

                if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                    String proteinId = refSeqCodingSequenceResults.get(0).getProteinId();

                    Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                    RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);

                    Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                    RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                            .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                    Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                    Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();

                    // add utr

                    int exonIndex1 = 0;
                    TranscriptMapsExons firstExon = exons.get(exonIndex1);
                    boolean foundFirst = true;

                    while (regionStart < firstExon.getTranscrStart() || regionStart > firstExon.getTranscrEnd()) {
                        firstExon.setRegionType(RegionType.UTR5);
                        exonIndex1++;
                        if (exonIndex1 == exons.size()) {
                            foundFirst = false;
                            break;
                        }
                        firstExon = exons.get(exonIndex1);
                        if (foundFirst) {
                            if (regionStart != firstExon.getTranscrStart()) {
                                int v = regionStart - firstExon.getTranscrStart();
                                int nStrand = strand.equals("+") ? 1 : -1;
                                int gc = firstExon.getContigStart() + nStrand * v;
                                TranscriptMapsExons utr5 = new TranscriptMapsExons(RegionType.UTR5,
                                        firstExon.getTranscrStart(), regionStart - 1, firstExon.getContigStart(), gc
                                                - nStrand);
                                exons.add(exonIndex1, utr5);
                                firstExon.setContigStart(gc);
                                firstExon.setTranscrStart(regionStart);
                            }
                        }
                    }

                    int exonIndex2 = exons.size() - 1;
                    TranscriptMapsExons lastExon = exons.get(exonIndex2);
                    boolean foundLast = true;

                    while (regionEnd < lastExon.getTranscrStart() || regionEnd > lastExon.getTranscrEnd()) {
                        lastExon.setRegionType(RegionType.UTR3);
                        exonIndex2--;
                        if (exonIndex2 == exons.size()) {
                            foundLast = false;
                            break;
                        }
                        lastExon = exons.get(exonIndex2);
                        if (foundLast) {
                            int v = regionEnd - lastExon.getTranscrStart();
                            int nStrand = strand.equals("+") ? 1 : -1;
                            int gc = lastExon.getContigStart() + nStrand * v;
                            TranscriptMapsExons utr3 = new TranscriptMapsExons(RegionType.UTR3, regionEnd + 1,
                                    lastExon.getTranscrEnd(), gc + nStrand, lastExon.getContigEnd());
                            exons.add(exonIndex2 + 1, utr3);
                            lastExon.setContigStart(gc);
                            lastExon.setTranscrStart(regionEnd);
                        }
                    }

                    for (TranscriptMapsExons exon : exons) {
                        if (exon.getRegionType().equals(RegionType.EXON)) {
                            exon.setContigStart(exon.getTranscrStart() - regionStart + 1);
                            exon.setContigEnd(exon.getTranscrEnd() - regionStart + 1);
                        }
                    }

                }

                for (int i = exons.size() - 1; i > 0; --i) {
                    TranscriptMapsExons exon = exons.get(i);
                    if (exon.getTranscriptMaps().getStrand().equals("+")
                            && exons.get(i - 1).getContigEnd() != exons.get(i).getContigStart()) {
                        int g1 = exons.get(i - 1).getContigEnd() + 1;
                        int g2 = exons.get(i).getContigStart() - 1;
                        TranscriptMapsExons intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, g1, g2);
                        exons.add(i, intron);
                    } else if (exon.getTranscriptMaps().getStrand().equals("-")
                            && exons.get(i + 1).getContigStart() != exons.get(i - 1).getContigEnd()) {
                        int g1 = exons.get(i).getContigStart() + 1;
                        int g2 = exons.get(i - 1).getContigEnd() - 1;
                        TranscriptMapsExons intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, g1, g2);
                        exons.add(i, intron);
                    }
                }

                if (strand.equals("+")) {
                    for (TranscriptMapsExons exon : exons) {
                        
                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
                        
                    }
                } else {
                    ReverseListIterator<TranscriptMapsExons> iter = new ReverseListIterator<TranscriptMapsExons>(exons);
                    while (iter.hasNext()) {
                        TranscriptMapsExons exon = iter.next();
                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
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

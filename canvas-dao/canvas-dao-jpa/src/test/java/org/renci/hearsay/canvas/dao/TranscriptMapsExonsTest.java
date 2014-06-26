package org.renci.hearsay.canvas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.MappedTranscript;
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
            String transcriptAccession = "NM_001029954.2";
            // String transcriptAccession = "NM_007298.3";

            List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();
            mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, transcriptAccession));

            mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_007298.3"));

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();

                for (TranscriptMapsExons exon : mapsExonsResults) {
                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();
                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());
                    StrandType sType = StrandType.POSITIVE;
                    if ("-".equals(transcriptionMaps.getStrand())) {
                        sType = StrandType.NEGATIVE;
                    }
                    if (!map.containsKey(mappingKey)) {
                        map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession(), sType));
                    }
                }

                for (TranscriptMapsExons exon : mapsExonsResults) {
                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());

                    Region e = new Region();
                    e.setNumber(exon.getKey().getExonNum());
                    e.setTranscriptStart(exon.getTranscrStart());
                    e.setTranscriptEnd(exon.getTranscrEnd());
                    e.setGenomeStart(exon.getContigStart());
                    e.setGenomeEnd(exon.getContigEnd());
                    e.setRegionType(RegionType.EXON);
                    map.get(mappingKey).getRegions().add(e);
                }

                for (MappingKey key : map.keySet()) {
                    Mapping mapping = map.get(key);

                    List<RefSeqGene> refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

                    Gene gene = null;
                    if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                        RefSeqGene refSeqGene = refSeqGeneList.get(0);
                        // List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(
                        // refSeqGene.getName());
                        // if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                        gene = new Gene(refSeqGene.getName(), refSeqGene.getDescription());
                        // Long id = hearsayDAOBean.getGeneDAO().save(gene);
                        // gene.setId(id);
                        // } else {
                        // gene = alreadyPersistedGeneList.get(0);
                        // }
                    }

                    TreeSet<Region> exons = mapping.getRegions();

                    org.renci.hearsay.dao.model.Transcript transcript = new org.renci.hearsay.dao.model.Transcript();
                    transcript.setGene(gene);
                    transcript.setAccession(key.getVersionId());
                    transcript.setGenomicStart(exons.first().toRange().getMinimumInteger());
                    transcript.setGenomicEnd(exons.last().toRange().getMaximumInteger());

                    // List<org.renci.hearsay.dao.model.Transcript> alreadyPersistedTranscriptList = hearsayDAOBean
                    // .getTranscriptDAO().findByExample(transcript);
                    // if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                    // Long id = hearsayDAOBean.getTranscriptDAO().save(transcript);
                    // transcript.setId(id);
                    // } else {
                    // transcript = alreadyPersistedTranscriptList.get(0);
                    // }

                    List<RefSeqCodingSequence> refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

                    if (refSeqCodingSequenceResults == null
                            || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
                        Iterator<Region> exonsAscendingIter = exons.iterator();
                        while (exonsAscendingIter.hasNext()) {
                            Region exon = exonsAscendingIter.next();
                            exon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR);
                        }
                    }

                    if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                        Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                        RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
                        Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                        RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                                .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                        Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                        Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
                        mapping.addUTRs(regionStart, regionEnd);
                        mapping.addCDSCoordinates(regionStart);
                    }
                    mapping.addIntrons();
                    System.out.println(transcript.toString());

                    for (Region region : mapping.getRegions()) {
                        MappedTranscript mappedTranscript = new MappedTranscript();
                        mappedTranscript.setTranscript(transcript);
                        mappedTranscript.setStrandType(mapping.getStrandType());
                        mappedTranscript.setRegionType(region.getRegionType());
                        if (region.getGenomeStart() < region.getGenomeEnd()) {
                            mappedTranscript.setRegionStart(region.getGenomeStart());
                            mappedTranscript.setRegionEnd(region.getGenomeEnd());
                        } else {
                            mappedTranscript.setRegionStart(region.getGenomeEnd());
                            mappedTranscript.setRegionEnd(region.getGenomeStart());
                        }

                        if (region.getRegionType() != RegionType.INTRON) {
                            if (region.getGenomeStart() < region.getGenomeEnd()) {
                                mappedTranscript.setTranscriptStart(region.getTranscriptStart());
                                mappedTranscript.setTranscriptEnd(region.getTranscriptEnd());
                            } else {
                                mappedTranscript.setTranscriptStart(region.getTranscriptEnd());
                                mappedTranscript.setTranscriptEnd(region.getTranscriptStart());
                            }
                        }

                        if (region.getRegionType() == RegionType.EXON) {
                            if (region.getGenomeStart() < region.getGenomeEnd()) {
                                mappedTranscript.setCdsStart(region.getContigStart());
                                mappedTranscript.setCdsEnd(region.getContigEnd());
                            } else {
                                mappedTranscript.setCdsStart(region.getContigEnd());
                                mappedTranscript.setCdsEnd(region.getContigStart());
                            }
                        }
                        System.out.println(mappedTranscript.toString());
                        // hearsayDAOBean.getMappedTranscriptDAO().save(mappedTranscript);
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

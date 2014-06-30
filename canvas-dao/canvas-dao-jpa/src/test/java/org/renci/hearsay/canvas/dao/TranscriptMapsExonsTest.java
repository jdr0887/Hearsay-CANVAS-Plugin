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

            List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();
            // mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
            // .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_001029954.2"));

            // mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
            // .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_007298.3"));

            mapsExonsResults.addAll(canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_033663.3"));

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();

                for (TranscriptMapsExons exon : mapsExonsResults) {
                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();
                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());
                    StrandType sType = StrandType.PLUS;
                    if ("-".equals(transcriptionMaps.getStrand())) {
                        sType = StrandType.MINUS;
                    }
                    if (!map.containsKey(mappingKey)) {
                        map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession(), sType));
                    }
                }
                System.out.printf("map.size(): %d", map.size());
                
                for (TranscriptMapsExons exon : mapsExonsResults) {
                    TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                    MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                            transcriptionMaps.getMapCount());

                    StrandType sType = StrandType.PLUS;
                    if ("-".equals(transcriptionMaps.getStrand())) {
                        sType = StrandType.MINUS;
                    }

                    Region e = new Region();
                    e.setNumber(exon.getKey().getExonNum());
                    e.setRegionType(RegionType.EXON);
                    if (sType.equals(StrandType.PLUS)) {
                        e.setTranscriptStart(exon.getTranscrStart());
                        e.setTranscriptStop(exon.getTranscrEnd());
                        e.setGenomeStart(exon.getContigStart());
                        e.setGenomeStop(exon.getContigEnd());
                    } else {
                        e.setTranscriptStart(exon.getTranscrEnd());
                        e.setTranscriptStop(exon.getTranscrStart());
                        e.setGenomeStart(exon.getContigEnd());
                        e.setGenomeStop(exon.getContigStart());
                    }
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
                    System.out.println(gene.toString());

                    TreeSet<Region> regions = mapping.getRegions();

                    org.renci.hearsay.dao.model.Transcript transcript = new org.renci.hearsay.dao.model.Transcript();
                    transcript.setGene(gene);
                    transcript.setAccession(key.getVersionId());
                    System.out.println(transcript.toString());

                    // List<org.renci.hearsay.dao.model.Transcript> alreadyPersistedTranscriptList = hearsayDAOBean
                    // .getTranscriptDAO().findByExample(transcript);
                    // if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                    // Long id = hearsayDAOBean.getTranscriptDAO().save(transcript);
                    // transcript.setId(id);
                    // } else {
                    // transcript = alreadyPersistedTranscriptList.get(0);
                    // }
                    System.out.println(transcript.toString());

                    MappedTranscript mappedTranscript = new MappedTranscript();
                    mappedTranscript.setTranscript(transcript);
                    mappedTranscript.setStrandType(mapping.getStrandType());
                    mappedTranscript.setGenomicAccession(mapping.getVersionAccession());
                    mappedTranscript.setGenomicStart(regions.first().toRange().getMinimumInteger());
                    mappedTranscript.setGenomicStop(regions.last().toRange().getMaximumInteger());
                    // Long id = hearsayDAOBean.getMappedTranscriptDAO().save(mappedTranscript);
                    // mappedTranscript.setId(id);
                    System.out.println(mappedTranscript.toString());

                    List<RefSeqCodingSequence> refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

                    if (refSeqCodingSequenceResults == null
                            || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
                        Iterator<Region> exonsAscendingIter = regions.iterator();
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

                    for (Region region : mapping.getRegions()) {

                        org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
                        hearsayRegion.setMappedTranscript(mappedTranscript);
                        hearsayRegion.setRegionType(region.getRegionType());
                        // if (region.getGenomeStart() < region.getGenomeStop()) {
                        hearsayRegion.setRegionStart(region.getGenomeStart());
                        hearsayRegion.setRegionStop(region.getGenomeStop());
                        // } else {
                        // hearsayRegion.setRegionStart(region.getGenomeStop());
                        // hearsayRegion.setRegionStop(region.getGenomeStart());
                        // }

                        hearsayRegion.setTranscriptStart(region.getTranscriptStart());
                        hearsayRegion.setTranscriptStop(region.getTranscriptStop());

                        hearsayRegion.setCdsStart(region.getContigStart());
                        hearsayRegion.setCdsStop(region.getContigStop());
                        System.out.println(region.toString());
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

package org.renci.hearsay.canvas.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
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
import org.renci.hearsay.canvas.ref.dao.jpa.GenomeRefDAOImpl;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.jpa.FeatureDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqCodingSequenceDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.RefSeqGeneDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.jpa.TranscriptMapsExonsDAOImpl;
import org.renci.hearsay.canvas.refseq.dao.model.Feature;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.jpa.GeneDAOImpl;
import org.renci.hearsay.dao.jpa.GenomicRefSeqDAOImpl;
import org.renci.hearsay.dao.jpa.RegionDAOImpl;
import org.renci.hearsay.dao.jpa.TranscriptAlignmentDAOImpl;
import org.renci.hearsay.dao.jpa.TranscriptRefSeqDAOImpl;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;
import org.renci.hearsay.dao.model.TranscriptAlignment;
import org.renci.hearsay.dao.model.TranscriptRefSeq;

public class Scratch {

    private static EntityManagerFactory hearsayEMFactory;

    private static EntityManager hearsayEM;

    private static EntityManagerFactory canvasEMFactory;

    private static EntityManager canvasEM;

    @BeforeClass
    public static void setup() {
        canvasEMFactory = Persistence.createEntityManagerFactory("test-canvas", null);
        canvasEM = canvasEMFactory.createEntityManager();
        hearsayEMFactory = Persistence.createEntityManagerFactory("test-hearsay", null);
        hearsayEM = hearsayEMFactory.createEntityManager();
    }

    @Test
    public void scratch() {

        // HEARSAY DAO
        HearsayDAOBean hearsayDAOBean = new HearsayDAOBean();

        GeneDAOImpl geneDAO = new GeneDAOImpl();
        geneDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setGeneDAO(geneDAO);

        GenomicRefSeqDAOImpl genomicRefSeqDAO = new GenomicRefSeqDAOImpl();
        genomicRefSeqDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setGenomicRefSeqDAO(genomicRefSeqDAO);

        TranscriptRefSeqDAOImpl transcriptRefSeqDAO = new TranscriptRefSeqDAOImpl();
        transcriptRefSeqDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setTranscriptRefSeqDAO(transcriptRefSeqDAO);

        TranscriptAlignmentDAOImpl transcriptAlignmentDAO = new TranscriptAlignmentDAOImpl();
        transcriptAlignmentDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setTranscriptAlignmentDAO(transcriptAlignmentDAO);

        RegionDAOImpl regionDAO = new RegionDAOImpl();
        regionDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setRegionDAO(regionDAO);

        org.renci.hearsay.dao.jpa.FeatureDAOImpl hearsayFeatureDAO = new org.renci.hearsay.dao.jpa.FeatureDAOImpl();
        hearsayFeatureDAO.setEntityManager(hearsayEM);
        hearsayDAOBean.setFeatureDAO(hearsayFeatureDAO);

        // CANVAS DAO
        CANVASDAOBean canvasDAOBean = new CANVASDAOBean();

        TranscriptMapsExonsDAOImpl transcriptMapsExonsDAO = new TranscriptMapsExonsDAOImpl();
        transcriptMapsExonsDAO.setEntityManager(canvasEM);
        canvasDAOBean.setTranscriptMapsExonsDAO(transcriptMapsExonsDAO);

        RefSeqGeneDAOImpl refSeqGeneDAO = new RefSeqGeneDAOImpl();
        refSeqGeneDAO.setEntityManager(canvasEM);
        canvasDAOBean.setRefSeqGeneDAO(refSeqGeneDAO);

        RefSeqCodingSequenceDAOImpl refSeqCodingSequenceDAO = new RefSeqCodingSequenceDAOImpl();
        refSeqCodingSequenceDAO.setEntityManager(canvasEM);
        canvasDAOBean.setRefSeqCodingSequenceDAO(refSeqCodingSequenceDAO);

        GenomeRefDAOImpl genomeRefDAO = new GenomeRefDAOImpl();
        genomeRefDAO.setEntityManager(canvasEM);
        canvasDAOBean.setGenomeRefDAO(genomeRefDAO);

        FeatureDAOImpl canvasFeatureDAO = new FeatureDAOImpl();
        canvasFeatureDAO.setEntityManager(canvasEM);
        canvasDAOBean.setFeatureDAO(canvasFeatureDAO);

        String refSeqVersion = "61";
        Integer genomeRefId = 2;

        try {

            List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();

            // List<TranscriptMapsExons> pulledExons = canvasDAOBean.getTranscriptMapsExonsDAO()
            // .findByGenomeRefIdAndRefSeqVersion(Integer.valueOf(genomeRefId), refSeqVersion);

            List<TranscriptMapsExons> pulledExons = canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "XM_005277470.1");

            // List<TranscriptMapsExons> pulledExons = canvasDAOBean.getTranscriptMapsExonsDAO()
            // .findByGenomeRefIdAndRefSeqVersionAndAccession(genomeRefId, refSeqVersion, "NM_000059.3");

            if (pulledExons != null && !pulledExons.isEmpty()) {
                mapsExonsResults.addAll(pulledExons);
            }

            System.out.printf("pulledExons.size() = %s%n", pulledExons.size());

            GenomeRef genomeRef = null;
            try {
                genomeRef = canvasDAOBean.getGenomeRefDAO().findById(genomeRefId.longValue());
            } catch (HearsayDAOException e1) {
                e1.printStackTrace();
            }

            if (genomeRef == null) {
                System.out.println("No GenomeRef was found");
                return;
            }

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

                System.out.printf("map.size(): %s%n", map.size());

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

                    List<RefSeqGene> refSeqGeneList = null;
                    try {
                        refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(
                                refSeqVersion, key.getVersionId());
                    } catch (HearsayDAOException e) {
                        e.printStackTrace();
                    }

                    Gene gene = null;
                    if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                        RefSeqGene refSeqGene = refSeqGeneList.get(0);
                        try {
                            List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(
                                    refSeqGene.getName());
                            if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                                gene = new Gene();
                                gene.setName(refSeqGene.getName());
                                gene.setDescription(refSeqGene.getDescription());
                                // hearsayEM.getTransaction().begin();
                                // gene.setId(hearsayDAOBean.getGeneDAO().save(gene));
                                // hearsayEM.getTransaction().commit();
                            } else {
                                gene = alreadyPersistedGeneList.get(0);
                            }
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(gene.toString());

                    TreeSet<Region> regions = mapping.getRegions();

                    TranscriptRefSeq transcriptRefSeq = new TranscriptRefSeq();
                    transcriptRefSeq.setGene(gene);
                    transcriptRefSeq.setAccession(key.getVersionId());
                    try {
                        List<TranscriptRefSeq> alreadyPersistedTranscriptList = hearsayDAOBean.getTranscriptRefSeqDAO()
                                .findByExample(transcriptRefSeq);
                        if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                            hearsayEM.getTransaction().begin();
                            Long id = hearsayDAOBean.getTranscriptRefSeqDAO().save(transcriptRefSeq);
                            hearsayEM.getTransaction().commit();
                            transcriptRefSeq.setId(id);

                            List<Feature> canvasFeatures = canvasDAOBean.getFeatureDAO()
                                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());
                            if (canvasFeatures != null && !canvasFeatures.isEmpty()) {
                                for (Feature canvasFeature : canvasFeatures) {
                                    RegionGroup regionGroup = canvasFeature.getRegionGroup();
                                    if (regionGroup != null) {
                                        Set<RegionGroupRegion> regionGroupRegions = regionGroup.getRegionGroupRegions();
                                        if (regionGroupRegions != null && !regionGroupRegions.isEmpty()) {
                                            RegionGroupRegion[] regionGroupRegionArray = regionGroupRegions
                                                    .toArray(new RegionGroupRegion[regionGroupRegions.size()]);
                                            org.renci.hearsay.dao.model.Feature hearsayFeature = new org.renci.hearsay.dao.model.Feature();
                                            hearsayFeature.setNote(canvasFeature.getNote());
                                            hearsayFeature.setRegionStart(regionGroupRegionArray[0].getRegionStart());
                                            hearsayFeature.setRegionStop(regionGroupRegionArray[0].getRegionEnd());
                                            hearsayFeature.setTranscriptRefSeq(transcriptRefSeq);
                                            // hearsayEM.getTransaction().begin();
                                            // hearsayDAOBean.getFeatureDAO().save(hearsayFeature);
                                            // hearsayEM.getTransaction().commit();
                                        }
                                    }
                                }
                            }

                        } else {
                            transcriptRefSeq = alreadyPersistedTranscriptList.get(0);
                        }
                        System.out.println(transcriptRefSeq.toString());
                    } catch (HearsayDAOException e) {
                        e.printStackTrace();
                    }

                    TranscriptAlignment transcriptAlignment = new TranscriptAlignment();

                    transcriptAlignment.setTranscriptRefSeq(transcriptRefSeq);
                    transcriptAlignment.setStrandType(mapping.getStrandType());
                    transcriptAlignment.setGenomicStart(regions.first().toRange().getMinimumInteger());
                    transcriptAlignment.setGenomicStop(regions.last().toRange().getMaximumInteger());

                    List<RefSeqCodingSequence> refSeqCodingSequenceResults = null;
                    try {
                        refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                                .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());
                    } catch (HearsayDAOException e) {
                        e.printStackTrace();
                    }

                    if (refSeqCodingSequenceResults == null
                            || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
                        Iterator<Region> exonsAscendingIter = regions.iterator();
                        while (exonsAscendingIter.hasNext()) {
                            Region exon = exonsAscendingIter.next();
                            exon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR);
                        }
                    }

                    if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                        RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceResults.get(0);
                        transcriptAlignment.setProtein(refSeqCDS.getProteinId());
                        Set<RegionGroup> regionGroupSet = refSeqCDS.getLocations();
                        RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
                        RegionGroup regionGroup = regionGroupArray[0];
                        Set<RegionGroupRegion> regionGroupRegionSet = regionGroup.getRegionGroupRegions();

                        RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                                .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);

                        RegionGroupRegion rgr = regionGroupRegionArray[0];
                        Integer regionStart = rgr.getRegionStart();
                        Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
                        transcriptAlignment.setProteinRegionStart(regionStart);
                        transcriptAlignment.setProteinRegionStop(regionEnd);

                        Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
                        while (navigableRegionIter.hasNext()) {
                            System.out.println(navigableRegionIter.next().toString());
                        }
                        System.out.println();
                        System.out.println();

                        addUTR5s(mapping, regionStart);
                        
                        navigableRegionIter = mapping.getRegions().iterator();
                        while (navigableRegionIter.hasNext()) {
                            System.out.println(navigableRegionIter.next().toString());
                        }
                        System.out.println();
                        System.out.println();
                        
                        addUTR3s(mapping, regionEnd);
                        addCDSCoordinates(mapping, regionStart);
                    }
                    addIntrons(mapping);

                    Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
                    while (navigableRegionIter.hasNext()) {
                        System.out.println(navigableRegionIter.next().toString());
                    }
                    System.out.println();
                    System.out.println();

                    // try {
                    // hearsayEM.getTransaction().begin();
                    // Long mappedTranscriptId = hearsayDAOBean.getTranscriptAlignmentDAO().save(transcriptAlignment);
                    // hearsayEM.getTransaction().commit();
                    // transcriptAlignment.setId(mappedTranscriptId);
                    // System.out.println(transcriptAlignment.toString());
                    // } catch (HearsayDAOException e) {
                    // e.printStackTrace();
                    // }

                    for (Region region : mapping.getRegions()) {
                        org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
                        hearsayRegion.setTranscriptAlignment(transcriptAlignment);
                        hearsayRegion.setRegionType(region.getRegionType());
                        // if (region.getGenomeStart() < region.getGenomeStop()) {
                        hearsayRegion.setRegionStart(region.getGenomeStart());
                        hearsayRegion.setRegionStop(region.getGenomeStop());
                        // } else {
                        // hearsayRegion.setRegionStop(region.getGenomeStart());
                        // hearsayRegion.setRegionStart(region.getGenomeStop());
                        // }
                        hearsayRegion.setTranscriptStart(region.getTranscriptStart());
                        hearsayRegion.setTranscriptStop(region.getTranscriptStop());

                        hearsayRegion.setCdsStart(region.getContigStart());
                        hearsayRegion.setCdsStop(region.getContigStop());

                        System.out.println(region.toString());

                        // try {
                        // hearsayEM.getTransaction().begin();
                        // Long id = hearsayDAOBean.getRegionDAO().save(hearsayRegion);
                        // hearsayEM.getTransaction().commit();
                        // hearsayRegion.setId(id);
                        // } catch (HearsayDAOException e) {
                        // e.printStackTrace();
                        // }
                        transcriptAlignment.getRegions().add(hearsayRegion);
                    }
                    // try {
                    // hearsayEM.getTransaction().begin();
                    // transcriptAlignmentDAO.save(transcriptAlignment);
                    // hearsayEM.getTransaction().commit();
                    // } catch (HearsayDAOException e) {
                    // e.printStackTrace();
                    // }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addCDSCoordinates(Mapping mapping, Integer regionStart) {
        for (Region exon : mapping.getRegions()) {
            if (exon.getRegionType() == RegionType.EXON) {
                exon.setContigStart(Math.max(1, exon.getTranscriptStart() - regionStart + 1));
                exon.setContigStop(exon.getTranscriptStop() - regionStart + 1);
            }
        }
    }

    public void addUTR5s(Mapping mapping, Integer regionStart) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            for (Region region : mapping.getRegions()) {
                if (regionStart > region.getTranscriptStart()) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                }
            }

            Region firstRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStart > r.getTranscriptStop()) {
                    firstRegion = r;
                    break;
                }
            }

            if (regionStart > firstRegion.getTranscriptStop()) {
                Region utr5 = new Region();
                utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                int startTranscript = firstRegion.getTranscriptStop();
                int stopTranscript = regionStart - 1;
                int diff = startTranscript - stopTranscript;
                utr5.setGenomeStart(firstRegion.getGenomeStop() + diff);
                utr5.setGenomeStop(firstRegion.getGenomeStop());
                utr5.setTranscriptStart(regionStart - 1);
                utr5.setTranscriptStop(firstRegion.getTranscriptStop());
                mapping.getRegions().add(utr5);

                firstRegion.setGenomeStop(utr5.getGenomeStart() - 1);
                firstRegion.setTranscriptStop(regionStart);

            }

        } else {

            int strand = 1;

            for (Region region : mapping.getRegions()) {
                if (regionStart > region.getTranscriptStop()) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                }
            }

            Region firstRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStart > r.getTranscriptStop()) {
                    firstRegion = r;
                    break;
                }
            }

            Region nextRegion = mapping.getRegions().ceiling(firstRegion);

            if (regionStart > firstRegion.getTranscriptStop()) {
                Region utr5 = new Region();
                utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                utr5.setGenomeStart(nextRegion.getGenomeStart());
                int startTranscript = firstRegion.getTranscriptStop() + 1;
                utr5.setTranscriptStart(startTranscript);
                int stopTranscript = regionStart - 1;
                utr5.setTranscriptStop(stopTranscript);
                int diff = startTranscript - stopTranscript;
                utr5.setGenomeStop(nextRegion.getGenomeStart() - (strand * diff));
                mapping.getRegions().add(utr5);

                nextRegion.setGenomeStart(utr5.getGenomeStop() + 1);
                nextRegion.setTranscriptStart(regionStart);
                System.out.println();
            }

        }

    }

    public void addUTR3s(Mapping mapping, Integer regionStop) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            NavigableSet<Region> navigableRegionSet = mapping.getRegions();
            for (Region region : navigableRegionSet) {
                if (regionStop < region.getTranscriptStop() && !region.equals(navigableRegionSet.first())) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                }
            }

            Region lastRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().iterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStop > r.getTranscriptStop()) {
                    lastRegion = r;
                    break;
                }
            }

            if (regionStop > lastRegion.getTranscriptStop()) {

                Region utr3 = new Region();
                utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                utr3.setGenomeStart(lastRegion.getGenomeStop());
                int startTranscript = lastRegion.getTranscriptStart();
                utr3.setTranscriptStart(startTranscript);
                int stopTranscript = regionStop + 1;
                utr3.setTranscriptStop(stopTranscript);
                int diff = startTranscript - stopTranscript;
                utr3.setGenomeStop(utr3.getGenomeStart() + diff);
                mapping.getRegions().add(utr3);

                lastRegion.setGenomeStop(lastRegion.getGenomeStart());
                lastRegion.setGenomeStart(utr3.getGenomeStop() + 1);
                lastRegion.setTranscriptStart(regionStop);

            }

        } else {

            NavigableSet<Region> navigableRegionSet = mapping.getRegions().descendingSet();
            for (Region region : navigableRegionSet) {
                if (regionStop < region.getTranscriptStart() && !region.equals(navigableRegionSet.first())) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                }
            }

            Region lastRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().descendingIterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (r.getTranscriptStop() > regionStop) {
                    lastRegion = r;
                    break;
                }
            }

            if (lastRegion != null) {

                if (lastRegion.getTranscriptStop() > regionStop) {
                    Region utr3 = new Region();
                    utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                    int startTranscript = lastRegion.getTranscriptStop();
                    utr3.setTranscriptStop(startTranscript);
                    int stopTranscript = regionStop + 1;
                    utr3.setTranscriptStart(stopTranscript);
                    int diff = startTranscript - stopTranscript;
                    utr3.setGenomeStop(lastRegion.getGenomeStop());
                    utr3.setGenomeStart(lastRegion.getGenomeStop() - diff);
                    mapping.getRegions().add(utr3);

                    lastRegion.setGenomeStop(lastRegion.getGenomeStart()
                            + (regionStop - lastRegion.getTranscriptStart()));
                    lastRegion.setTranscriptStop(regionStop);
                    System.out.println();
                }

            }

        }

    }

    public void addIntrons(Mapping mapping) {

        List<Region> regions = new ArrayList<Region>();

        if (mapping.getStrandType().equals(StrandType.MINUS)) {

            Iterator<Region> regionIter = mapping.getRegions().iterator();
            while (regionIter.hasNext()) {
                Region current = regionIter.next();
                if (current.equals(mapping.getRegions().first())) {
                    continue;
                }
                Region previous = mapping.getRegions().floor(current);

                if (previous == null || current.equals(previous)) {
                    continue;
                }

                if (previous.getGenomeStop() + 1 != current.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setRegionType(RegionType.INTRON);
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
                    regions.add(exon);
                }
            }

        } else {

            Iterator<Region> regionIter = mapping.getRegions().iterator();
            while (regionIter.hasNext()) {
                Region current = regionIter.next();
                if (current.equals(mapping.getRegions().first())) {
                    continue;
                }
                Region previous = mapping.getRegions().floor(current);

                if (current.equals(previous)) {
                    continue;
                }
                if (previous.getGenomeStop() + 1 != current.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setRegionType(RegionType.INTRON);
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
                    regions.add(exon);
                }
            }

        }

        mapping.getRegions().addAll(regions);

    }

    @AfterClass
    public static void tearDown() {
        canvasEM.close();
        canvasEMFactory.close();
        hearsayEM.close();
        hearsayEMFactory.close();
    }

}

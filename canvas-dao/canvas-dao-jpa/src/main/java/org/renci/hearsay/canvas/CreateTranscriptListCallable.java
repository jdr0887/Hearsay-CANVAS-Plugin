package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.MappedTranscript;
import org.renci.hearsay.dao.model.ReferenceGenome;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTranscriptListCallable implements Callable<List<org.renci.hearsay.dao.model.Transcript>> {

    private final Logger logger = LoggerFactory.getLogger(CreateTranscriptListCallable.class);

    private String refSeqVersion;

    private Long genomeRefId;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private List<TranscriptMapsExons> mapsExonsResults;

    public CreateTranscriptListCallable() {
        super();
    }

    @Override
    public List<org.renci.hearsay.dao.model.Transcript> call() {
        logger.info("ENTERING call()");

        List<org.renci.hearsay.dao.model.Transcript> results = new ArrayList<org.renci.hearsay.dao.model.Transcript>();
        GenomeRef genomeRef = null;
        try {
            genomeRef = canvasDAOBean.getGenomeRefDAO().findById(genomeRefId);
        } catch (HearsayDAOException e1) {
            e1.printStackTrace();
        }

        if (genomeRef == null) {
            logger.warn("No GenomeRef was found");
            return results;
        }

        ReferenceGenome referenceGenome = null;

        try {
            ReferenceGenome tmpReferenceGenome = new ReferenceGenome(genomeRef.getRefSource(), genomeRef.getRefVer());
            List<ReferenceGenome> referenceGenomeList = hearsayDAOBean.getReferenceGenomeDAO().findByExample(
                    tmpReferenceGenome);
            if (referenceGenomeList != null && !referenceGenomeList.isEmpty()) {
                referenceGenome = referenceGenomeList.get(0);
            } else {
                Long id = hearsayDAOBean.getReferenceGenomeDAO().save(tmpReferenceGenome);
                tmpReferenceGenome.setId(id);
                referenceGenome = tmpReferenceGenome;
            }
        } catch (HearsayDAOException e) {
            logger.error("Persistence Erorr", e);
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
            logger.info("map.size(): {}", map.size());

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
                } else {
                    e.setTranscriptStart(exon.getTranscrEnd());
                    e.setTranscriptStop(exon.getTranscrStart());
                }
                e.setGenomeStart(exon.getContigStart());
                e.setGenomeStop(exon.getContigEnd());
                map.get(mappingKey).getRegions().add(e);
            }

            for (MappingKey key : map.keySet()) {

                try {
                    List<ReferenceSequence> referenceSequenceList = hearsayDAOBean.getReferenceSequenceDAO()
                            .findByAccession(key.getVersionId());
                    if (referenceSequenceList == null
                            || (referenceSequenceList != null && referenceSequenceList.isEmpty())) {
                        ReferenceSequence referenceSequence = new ReferenceSequence(key.getVersionId());
                        Long id = hearsayDAOBean.getReferenceSequenceDAO().save(referenceSequence);
                        referenceSequence.setId(id);
                        referenceGenome.getReferenceSequences().add(referenceSequence);
                    }
                } catch (HearsayDAOException e) {
                    logger.error("Persistence Erorr", e);
                }

            }

            try {
                hearsayDAOBean.getReferenceGenomeDAO().save(referenceGenome);
            } catch (HearsayDAOException e) {
                logger.error("Persistence Erorr", e);
            }

            for (MappingKey key : map.keySet()) {
                Mapping mapping = map.get(key);

                List<RefSeqGene> refSeqGeneList = null;
                try {
                    refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(refSeqVersion,
                            key.getVersionId());
                } catch (HearsayDAOException e) {
                    logger.error("Persistence Erorr", e);
                }

                Gene gene = null;
                if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                    RefSeqGene refSeqGene = refSeqGeneList.get(0);
                    try {
                        if (hearsayDAOBean != null) {
                            List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(
                                    refSeqGene.getName());
                            if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                                gene = new Gene(refSeqGene.getName(), refSeqGene.getDescription());
                                Long id = hearsayDAOBean.getGeneDAO().save(gene);
                                gene.setId(id);
                            } else {
                                gene = alreadyPersistedGeneList.get(0);
                            }
                        } else {
                            gene = new Gene(refSeqGene.getName(), refSeqGene.getDescription());
                        }
                    } catch (HearsayDAOException e) {
                        logger.error("Persistence Erorr", e);
                    }
                }
                logger.debug(gene.toString());

                TreeSet<Region> regions = mapping.getRegions();

                org.renci.hearsay.dao.model.Transcript transcript = new org.renci.hearsay.dao.model.Transcript();
                transcript.setGene(gene);
                transcript.setAccession(key.getVersionId());
                if (hearsayDAOBean != null) {
                    try {
                        List<org.renci.hearsay.dao.model.Transcript> alreadyPersistedTranscriptList = hearsayDAOBean
                                .getTranscriptDAO().findByExample(transcript);
                        if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                            Long id = hearsayDAOBean.getTranscriptDAO().save(transcript);
                            transcript.setId(id);
                        } else {
                            transcript = alreadyPersistedTranscriptList.get(0);
                        }
                    } catch (HearsayDAOException e) {
                        logger.error("Persistence Erorr", e);
                    }
                }
                logger.info(transcript.toString());

                MappedTranscript mappedTranscript = new MappedTranscript();

                try {
                    List<ReferenceSequence> referenceSequenceList = hearsayDAOBean.getReferenceSequenceDAO()
                            .findByAccession(key.getVersionId());
                    if (referenceSequenceList != null && !referenceSequenceList.isEmpty()) {
                        mappedTranscript.setReferenceSequence(referenceSequenceList.get(0));
                    }
                } catch (HearsayDAOException e) {
                    logger.error("Persistence Erorr", e);
                }

                mappedTranscript.setTranscript(transcript);
                mappedTranscript.setStrandType(mapping.getStrandType());
                mappedTranscript.setGenomicAccession(mapping.getVersionAccession());
                mappedTranscript.setGenomicStart(regions.first().toRange().getMinimumInteger());
                mappedTranscript.setGenomicStop(regions.last().toRange().getMaximumInteger());
                if (hearsayDAOBean != null) {
                    try {
                        Long mappedTranscriptId = hearsayDAOBean.getMappedTranscriptDAO().save(mappedTranscript);
                        mappedTranscript.setId(mappedTranscriptId);
                    } catch (HearsayDAOException e) {
                        logger.error("Persistence Erorr", e);
                    }
                }
                logger.info(mappedTranscript.toString());

                List<RefSeqCodingSequence> refSeqCodingSequenceResults = null;
                try {
                    refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());
                } catch (HearsayDAOException e) {
                    logger.error("Persistence Erorr", e);
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
                    Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                    RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
                    Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                    RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                            .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                    Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                    Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
                    addUTR5s(mapping, regionStart);
                    addUTR3s(mapping, regionEnd);
                    addCDSCoordinates(mapping, regionStart);
                }
                addIntrons(mapping);

                for (Region region : mapping.getRegions()) {
                    org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
                    hearsayRegion.setMappedTranscript(mappedTranscript);
                    hearsayRegion.setRegionType(region.getRegionType());
                    if (region.getGenomeStart() < region.getGenomeStop()) {
                        hearsayRegion.setRegionStart(region.getGenomeStart());
                        hearsayRegion.setRegionStop(region.getGenomeStop());
                    } else {
                        hearsayRegion.setRegionStop(region.getGenomeStart());
                        hearsayRegion.setRegionStart(region.getGenomeStop());
                    }
                    hearsayRegion.setTranscriptStart(region.getTranscriptStart());
                    hearsayRegion.setTranscriptStop(region.getTranscriptStop());

                    hearsayRegion.setCdsStart(region.getContigStart());
                    hearsayRegion.setCdsStop(region.getContigStop());

                    logger.debug(region.toString());
                    mappedTranscript.getRegions().add(hearsayRegion);
                    if (hearsayDAOBean != null) {
                        try {
                            hearsayDAOBean.getRegionDAO().save(hearsayRegion);
                        } catch (HearsayDAOException e) {
                        }
                    }
                }
                transcript.getMappedTranscripts().add(mappedTranscript);

                results.add(transcript);
            }
        }
        logger.info("LEAVING call()");
        return results;
    }

    public void addCDSCoordinates(Mapping mapping, Integer regionStart) {
        for (Region exon : mapping.getRegions()) {
            if (exon.getRegionType() == RegionType.EXON) {
                exon.setContigStart(exon.getTranscriptStart() - regionStart + 1);
                exon.setContigStop(exon.getTranscriptStop() - regionStart + 1);
            }
        }
    }

    public void addUTR5s(Mapping mapping, Integer regionStart) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            int strand = -1;

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

            if (navigableRegionIter.hasNext()) {
                Region nextRegion = navigableRegionIter.next();

                if (regionStart > firstRegion.getTranscriptStop()) {
                    Region utr5 = new Region();
                    int startTranscript = firstRegion.getTranscriptStop();
                    int stopTranscript = regionStart - 1;
                    int diff = startTranscript - stopTranscript;
                    utr5.setGenomeStart(firstRegion.getGenomeStart() - (strand * diff));
                    utr5.setGenomeStop(firstRegion.getGenomeStart());
                    utr5.setTranscriptStart(regionStart - 1);
                    utr5.setTranscriptStop(firstRegion.getTranscriptStop());
                    utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                    mapping.getRegions().add(utr5);

                    firstRegion.setGenomeStart(firstRegion.getGenomeStop());
                    firstRegion.setGenomeStop(utr5.getGenomeStart() - 1);
                    firstRegion.setTranscriptStop(regionStart);

                    Integer genomeStart = nextRegion.getGenomeStart();
                    nextRegion.setGenomeStart(nextRegion.getGenomeStop());
                    nextRegion.setGenomeStop(genomeStart);

                }
            }

        } else {

            int strand = 1;

            for (Region region : mapping.getRegions()) {
                if (regionStart > region.getTranscriptStop()) {
                    region.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                }
            }

            Region firstRegion = null;
            Iterator<Region> navigableRegionIter = mapping.getRegions().descendingIterator();
            while (navigableRegionIter.hasNext()) {
                Region r = navigableRegionIter.next();
                if (regionStart > r.getTranscriptStop()) {
                    firstRegion = r;
                    break;
                }
            }

            if (navigableRegionIter.hasNext()) {
                Region nextRegion = navigableRegionIter.next();
                if (regionStart > firstRegion.getTranscriptStart()) {
                    Region utr5 = new Region();
                    utr5.setGenomeStart(nextRegion.getGenomeStart());
                    int startTranscript = firstRegion.getTranscriptStop() + 1;
                    int stopTranscript = regionStart - 1;
                    int diff = startTranscript - stopTranscript;
                    utr5.setGenomeStop(nextRegion.getGenomeStart() - (strand * diff));
                    utr5.setTranscriptStart(startTranscript);
                    utr5.setTranscriptStop(stopTranscript);
                    utr5.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR5);
                    mapping.getRegions().add(utr5);

                    nextRegion.setGenomeStart(utr5.getGenomeStop() + 1);
                    nextRegion.setTranscriptStart(regionStart);
                }
            }

        }

    }

    public void addUTR3s(Mapping mapping, Integer regionStop) {
        StrandType strandType = mapping.getStrandType();

        if (strandType.equals(StrandType.MINUS)) {

            int strand = -1;

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

            if (navigableRegionIter.hasNext()) {

                Region nextRegion = navigableRegionIter.next();

                if (regionStop > lastRegion.getTranscriptStop()) {

                    Region utr3 = new Region();
                    utr3.setGenomeStart(lastRegion.getGenomeStop());
                    int startTranscript = lastRegion.getTranscriptStart();
                    int stopTranscript = regionStop + 1;
                    int diff = startTranscript - stopTranscript;
                    utr3.setGenomeStop(utr3.getGenomeStart() + diff);
                    utr3.setTranscriptStart(startTranscript);
                    utr3.setTranscriptStop(stopTranscript);
                    utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                    mapping.getRegions().add(utr3);

                    lastRegion.setGenomeStop(lastRegion.getGenomeStart());
                    lastRegion.setGenomeStart(utr3.getGenomeStop() + 1);
                    lastRegion.setTranscriptStart(regionStop);

                    Integer genomeStop = nextRegion.getGenomeStop();
                    nextRegion.setGenomeStop(nextRegion.getGenomeStart());
                    nextRegion.setGenomeStart(genomeStop
                            - (nextRegion.getTranscriptStart() - nextRegion.getTranscriptStop()));
                }

            }

        } else {

            int strand = -1;

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

                    int v = regionStop - lastRegion.getTranscriptStart();
                    int gc = lastRegion.getGenomeStart() - strand * v;

                    Region utr3 = new Region();
                    utr3.setGenomeStart(gc + 1);
                    int startTranscript = lastRegion.getTranscriptStop();
                    int stopTranscript = regionStop + 1;
                    int diff = startTranscript - stopTranscript;
                    utr3.setGenomeStop(utr3.getGenomeStart() + diff);
                    utr3.setTranscriptStart(stopTranscript);
                    utr3.setTranscriptStop(startTranscript);
                    utr3.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR3);
                    mapping.getRegions().add(utr3);

                    lastRegion.setGenomeStop(gc);
                    lastRegion.setTranscriptStop(regionStop);
                }

            }

        }

    }

    public void addIntrons(Mapping mapping) {

        List<Region> exonsToAdd = new ArrayList<Region>();

        List<Region> regions = new ArrayList<Region>();
        regions.addAll(mapping.getRegions());
        Collections.sort(regions, new Comparator<Region>() {

            @Override
            public int compare(Region a, Region b) {
                return a.getGenomeStart().compareTo(b.getGenomeStart());
            }

        });

        for (int i = regions.size() - 1; i >= 0; --i) {

            if (i == 0 || i + 1 == regions.size()) {
                continue;
            }

            Region previous = regions.get(i - 1);
            Region current = regions.get(i);
            Region next = regions.get(i + 1);

            if (mapping.getStrandType().equals(StrandType.MINUS)) {

                if (previous.getGenomeStart() + 1 != current.getGenomeStop()) {
                    Region exon = new Region();
                    exon.setGenomeStart(previous.getGenomeStart() + 1);
                    exon.setGenomeStop(current.getGenomeStop() - 1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);
                }

            } else {

                if (previous.getGenomeStop() + 1 != current.getGenomeStart()) {
                    Region exon = new Region();
                    exon.setGenomeStart(previous.getGenomeStop() + 1);
                    exon.setGenomeStop(current.getGenomeStart() - 1);
                    exon.setRegionType(RegionType.INTRON);
                    exonsToAdd.add(exon);
                }

            }

        }

        mapping.getRegions().addAll(exonsToAdd);

    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

    public CANVASDAOBean getCanvasDAOBean() {
        return canvasDAOBean;
    }

    public void setCanvasDAOBean(CANVASDAOBean canvasDAOBean) {
        this.canvasDAOBean = canvasDAOBean;
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

    public List<TranscriptMapsExons> getMapsExonsResults() {
        return mapsExonsResults;
    }

    public void setMapsExonsResults(List<TranscriptMapsExons> mapsExonsResults) {
        this.mapsExonsResults = mapsExonsResults;
    }

    public Long getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Long genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

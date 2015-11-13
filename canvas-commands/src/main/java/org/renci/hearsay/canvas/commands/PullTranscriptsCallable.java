package org.renci.hearsay.canvas.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.renci.hearsay.dao.model.ReferenceSequenceType;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullTranscriptsCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullTranscriptsCallable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullTranscriptsCallable(CANVASDAOBeanService canvasDAOBeanService,
            HearsayDAOBeanService hearsayDAOBeanService, String refSeqVersion, Integer genomeRefId) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");

        List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();

        List<TranscriptMapsExons> pulledExons = canvasDAOBeanService.getTranscriptMapsExonsDAO()
                .findByGenomeRefIdAndRefSeqVersion(Integer.valueOf(genomeRefId), refSeqVersion);

        if (pulledExons != null && !pulledExons.isEmpty()) {
            mapsExonsResults.addAll(pulledExons);
        }
        logger.info("pulledExons.size() = {}", pulledExons.size());

        GenomeRef genomeRef = canvasDAOBeanService.getGenomeRefDAO().findById(genomeRefId.longValue());

        if (genomeRef == null) {
            logger.warn("No GenomeRef was found");
            return null;
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

                List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO()
                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

                Gene gene = null;
                if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                    RefSeqGene refSeqGene = refSeqGeneList.get(0);
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO()
                            .findBySymbol(refSeqGene.getName());
                    if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                        gene = new Gene();
                        gene.setSymbol(refSeqGene.getName());
                        gene.setDescription(refSeqGene.getDescription());
                        gene.setId(hearsayDAOBeanService.getGeneDAO().save(gene));
                        logger.info(gene.toString());
                    } else {
                        gene = alreadyPersistedGeneList.get(0);
                    }
                }

                ReferenceSequence transcriptRefSeq = new ReferenceSequence(ReferenceSequenceType.TRANSCRIPT);
                transcriptRefSeq.setGene(gene);

                String versionedRefSeqAccession = key.getVersionId();
                Identifier identifier = new Identifier("www.ncbi.nlm.nih.gov/nuccore", versionedRefSeqAccession);
                List<Identifier> possibleIdentifiers = hearsayDAOBeanService.getIdentifierDAO().findByExample(identifier);
                if (CollectionUtils.isNotEmpty(possibleIdentifiers)) {
                    identifier = possibleIdentifiers.get(0);
                } else {
                    identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                }
                transcriptRefSeq.getIdentifiers().add(identifier);

            }

            ThreadPoolExecutor tpe = new ThreadPoolExecutor(8, 8, 3, TimeUnit.DAYS,
                    new LinkedBlockingQueue<Runnable>());

            for (MappingKey key : map.keySet()) {
                PersistTranscriptsCallable runnable = new PersistTranscriptsCallable(refSeqVersion, key.getVersionId(),
                        map.get(key));
                tpe.submit(runnable);
            }

            tpe.shutdown();

        }

        return null;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

    public class PersistTranscriptsCallable implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(PersistTranscriptsCallable.class);

        private String refSeqVersion;

        private String versionId;

        private Mapping mapping;

        public PersistTranscriptsCallable(String refSeqVersion, String versionId, Mapping mapping) {
            super();
            this.refSeqVersion = refSeqVersion;
            this.versionId = versionId;
            this.mapping = mapping;
        }

        @Override
        public void run() {
            logger.debug("ENTERING call()");

            Gene gene = null;
            try {
                List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO()
                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionId);
                if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                    RefSeqGene refSeqGene = refSeqGeneList.get(0);
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO()
                            .findBySymbol(refSeqGene.getName());
                    gene = alreadyPersistedGeneList.get(0);
                }
            } catch (HearsayDAOException e) {
                e.printStackTrace();
            }

            if (gene == null) {
                logger.error("Gene not found");
                return;
            }
            logger.info(gene.toString());

            ReferenceSequence transcriptRefSeq = null;
            try {
                List<ReferenceSequence> alreadyPersistedTranscriptList = hearsayDAOBeanService.getReferenceSequenceDAO()
                        .findByIdentifierValue(versionId);
                if (alreadyPersistedTranscriptList != null && !alreadyPersistedTranscriptList.isEmpty()) {
                    transcriptRefSeq = alreadyPersistedTranscriptList.get(0);
                }
            } catch (HearsayDAOException e) {
                e.printStackTrace();
            }

            if (transcriptRefSeq == null) {
                logger.error("TranscriptRefSeq not found");
                return;
            }
            logger.info(transcriptRefSeq.toString());

            // TreeSet<Region> regions = mapping.getRegions();
            //
            // Alignment alignment = new Alignment();
            //
            // alignment.getReferenceSequences().add(transcriptRefSeq);
            //
            // alignment.setGenomicStart(regions.first().toRange().getMinimumInteger());
            // alignment.setGenomicStop(regions.last().toRange().getMaximumInteger());
            //
            // List<RefSeqCodingSequence> refSeqCodingSequenceResults = null;
            // try {
            // refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
            // .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionId);
            // } catch (HearsayDAOException e) {
            // e.printStackTrace();
            // }
            //
            // if (refSeqCodingSequenceResults == null
            // || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
            // Iterator<Region> exonsAscendingIter = regions.iterator();
            // while (exonsAscendingIter.hasNext()) {
            // Region exon = exonsAscendingIter.next();
            // exon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR);
            // }
            // }
            //
            // if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
            // RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceResults.get(0);
            // alignment.setProtein(refSeqCDS.getProteinId());
            // Set<RegionGroup> regionGroupSet = refSeqCDS.getLocations();
            // RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
            // RegionGroup regionGroup = regionGroupArray[0];
            // Set<RegionGroupRegion> regionGroupRegionSet = regionGroup.getRegionGroupRegions();
            //
            // RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
            // .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
            //
            // RegionGroupRegion rgr = regionGroupRegionArray[0];
            // Integer regionStart = rgr.getRegionStart();
            // Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
            // alignment.setProteinRegionStart(regionStart);
            // alignment.setProteinRegionStop(regionEnd);
            // TranscriptUtil.addUTR5s(mapping, regionStart);
            // TranscriptUtil.addUTR3s(mapping, regionEnd);
            // TranscriptUtil.addCDSCoordinates(mapping, regionStart);
            // }
            // TranscriptUtil.addIntrons(mapping);
            //
            // try {
            // Long mappedTranscriptId = hearsayDAOBean.getAlignmentDAO().save(alignment);
            // alignment.setId(mappedTranscriptId);
            // logger.info(alignment.toString());
            // } catch (HearsayDAOException e) {
            // e.printStackTrace();
            // }
            //
            // for (Region region : mapping.getRegions()) {
            // org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
            // hearsayRegion.setAlignment(alignment);
            // hearsayRegion.setRegionType(region.getRegionType());
            // hearsayRegion.setRegionStart(region.getGenomeStart());
            // hearsayRegion.setRegionStop(region.getGenomeStop());
            // hearsayRegion.setTranscriptStart(region.getTranscriptStart());
            // hearsayRegion.setTranscriptStop(region.getTranscriptStop());
            // hearsayRegion.setCdsStart(region.getContigStart());
            // hearsayRegion.setCdsStop(region.getContigStop());
            //
            // logger.info(region.toString());
            //
            // try {
            // Long id = hearsayDAOBean.getRegionDAO().save(hearsayRegion);
            // hearsayRegion.setId(id);
            // } catch (HearsayDAOException e) {
            // e.printStackTrace();
            // }
            // alignment.getRegions().add(hearsayRegion);
            // }
            // try {
            // hearsayDAOBean.getAlignmentDAO().save(alignment);
            // } catch (HearsayDAOException e) {
            // e.printStackTrace();
            // }

        }

        public Mapping getMapping() {
            return mapping;
        }

        public void setMapping(Mapping mapping) {
            this.mapping = mapping;
        }

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getRefSeqVersion() {
            return refSeqVersion;
        }

        public void setRefSeqVersion(String refSeqVersion) {
            this.refSeqVersion = refSeqVersion;
        }

    }

}

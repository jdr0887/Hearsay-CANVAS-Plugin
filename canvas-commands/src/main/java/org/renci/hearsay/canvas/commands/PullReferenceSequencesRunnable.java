package org.renci.hearsay.canvas.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.GeneSymbol;
import org.renci.hearsay.dao.model.GenomeReference;
import org.renci.hearsay.dao.model.Identifier;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.renci.hearsay.dao.model.ReferenceSequenceType;
import org.renci.hearsay.dao.model.StrandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullReferenceSequencesRunnable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullReferenceSequencesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullReferenceSequencesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService,
            String refSeqVersion, Integer genomeRefId) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public Void call() {
        logger.debug("ENTERING call()");

        try {

            GenomeRef genomeRef = canvasDAOBeanService.getGenomeRefDAO().findById(genomeRefId);
            logger.info(genomeRef.toString());

            List<GenomeReference> genomeReferences = hearsayDAOBeanService.getGenomeReferenceDAO()
                    .findByIdentifierSystemAndValue("canvas/ref/genomeRef/id", genomeRefId.toString());

            // assume that reference sequences can't be shared....locations & identifiers will vary from
            // datasource to datasource

            List<TranscriptMaps> foundTranscriptReferenceSequences = canvasDAOBeanService.getTranscriptMapsDAO()
                    .findByGenomeRefIdAndRefSeqVersion("includeAll", genomeRefId, refSeqVersion);

            if (CollectionUtils.isNotEmpty(foundTranscriptReferenceSequences)) {
                logger.info("foundTranscriptReferenceSequences.size() = {}", foundTranscriptReferenceSequences.size());

                ExecutorService es = Executors.newFixedThreadPool(3);

                es.submit(new Runnable() {

                    @Override
                    public void run() {
                        for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {
                            try {
                                String versionedRefSeqAccession = transcriptMaps.getTranscript().getVersionId();
                                Identifier transcriptVersionIdIdentifier = new Identifier("canvas/refseq/transcript/versionId",
                                        versionedRefSeqAccession);
                                List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                                        .findByExample(transcriptVersionIdIdentifier);
                                if (CollectionUtils.isEmpty(foundIdentifierList)) {
                                    hearsayDAOBeanService.getIdentifierDAO().save(transcriptVersionIdIdentifier);
                                }
                            } catch (HearsayDAOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                es.submit(new Runnable() {

                    @Override
                    public void run() {
                        for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {
                            try {
                                GenomeRefSeq genomeRefSeq = transcriptMaps.getGenomeRefSeq();
                                String versionedGenomicAccession = genomeRefSeq.getVerAccession();
                                Identifier versionedGenomicAccessionIdentifier = new Identifier("canvas/ref/genomeRefSeq/verAccession",
                                        versionedGenomicAccession);
                                List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                                        .findByExample(versionedGenomicAccessionIdentifier);
                                if (CollectionUtils.isEmpty(foundIdentifierList)) {
                                    hearsayDAOBeanService.getIdentifierDAO().save(versionedGenomicAccessionIdentifier);
                                }
                            } catch (HearsayDAOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });

                es.submit(new Runnable() {

                    @Override
                    public void run() {
                        for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {
                            try {
                                // set protein identifier
                                String versionedRefSeqAccession = transcriptMaps.getTranscript().getVersionId();
                                List<RefSeqCodingSequence> refSeqCodingSequenceList = canvasDAOBeanService.getRefSeqCodingSequenceDAO()
                                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
                                if (CollectionUtils.isNotEmpty(refSeqCodingSequenceList)) {
                                    RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceList.get(0);
                                    Identifier proteinIdIdentifier = new Identifier("canvas/refseq/cds/proteinId",
                                            refSeqCDS.getProteinId());
                                    List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                                            .findByExample(proteinIdIdentifier);
                                    if (CollectionUtils.isEmpty(foundIdentifierList)) {
                                        hearsayDAOBeanService.getIdentifierDAO().save(proteinIdIdentifier);
                                    }
                                }
                            } catch (HearsayDAOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });

                es.shutdown();
                es.awaitTermination(1L, TimeUnit.HOURS);

                for (TranscriptMaps transcriptMaps : foundTranscriptReferenceSequences) {
                    logger.info(transcriptMaps.toString());

                    StrandType sType = StrandType.PLUS;
                    if ("-".equals(transcriptMaps.getStrand())) {
                        sType = StrandType.MINUS;
                    }

                    List<Identifier> referenceSequenceIdentifierList = new ArrayList<Identifier>();

                    String versionedRefSeqAccession = transcriptMaps.getTranscript().getVersionId();
                    Identifier transcriptVersionIdIdentifier = new Identifier("canvas/refseq/transcript/versionId",
                            versionedRefSeqAccession);
                    List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                            .findByExample(transcriptVersionIdIdentifier);
                    if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                        transcriptVersionIdIdentifier = foundIdentifierList.get(0);
                        referenceSequenceIdentifierList.add(transcriptVersionIdIdentifier);
                    }

                    GenomeRefSeq genomeRefSeq = transcriptMaps.getGenomeRefSeq();
                    String versionedGenomicAccession = genomeRefSeq.getVerAccession();
                    Identifier versionedGenomicAccessionIdentifier = new Identifier("canvas/ref/genomeRefSeq/verAccession",
                            versionedGenomicAccession);
                    foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO().findByExample(versionedGenomicAccessionIdentifier);
                    if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                        versionedGenomicAccessionIdentifier = foundIdentifierList.get(0);
                        referenceSequenceIdentifierList.add(versionedGenomicAccessionIdentifier);
                    }

                    List<RefSeqCodingSequence> refSeqCodingSequenceList = canvasDAOBeanService.getRefSeqCodingSequenceDAO()
                            .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
                    if (CollectionUtils.isNotEmpty(refSeqCodingSequenceList)) {
                        RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceList.get(0);
                        Identifier proteinIdIdentifier = new Identifier("canvas/refseq/cds/proteinId", refSeqCDS.getProteinId());
                        foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO().findByExample(proteinIdIdentifier);
                        if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                            proteinIdIdentifier = foundIdentifierList.get(0);
                            referenceSequenceIdentifierList.add(proteinIdIdentifier);
                        }
                    }

                    referenceSequenceIdentifierList.forEach(a -> logger.info(a.toString()));

                    List<ReferenceSequence> foundReferenceSequenceList = hearsayDAOBeanService.getReferenceSequenceDAO()
                            .findByIdentifiers(referenceSequenceIdentifierList);

                    if (CollectionUtils.isEmpty(foundReferenceSequenceList)) {
                        // creating ReferenceSequence since it didn't already exist

                        ReferenceSequence referenceSequence = new ReferenceSequence();
                        referenceSequence.setStrandType(sType);

                        // set type
                        String prefix = versionedRefSeqAccession.substring(0, 3);
                        for (ReferenceSequenceType referenceSequenceType : ReferenceSequenceType.values()) {
                            if (referenceSequenceType.getPrefixes().contains(prefix)) {
                                referenceSequence.setType(referenceSequenceType);
                                break;
                            }
                        }

                        referenceSequence.setGenomeReference(genomeReferences.get(0));
                        referenceSequence.setId(hearsayDAOBeanService.getReferenceSequenceDAO().save(referenceSequence));

                        referenceSequence.getIdentifiers().addAll(referenceSequenceIdentifierList);

                        // set gene
                        List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO()
                                .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
                        if (CollectionUtils.isNotEmpty(refSeqGeneList)) {
                            RefSeqGene refSeqGene = refSeqGeneList.get(0);
                            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findByIdentifierSystemAndValue("canvas/refseq/gene/id",
                                    refSeqGene.getId().toString());
                            if (CollectionUtils.isNotEmpty(geneList)) {
                                referenceSequence.setGene(geneList.get(0));
                            }
                            // if gene is not set, could be due to being stored as an alias
                            if (referenceSequence.getGene() == null) {
                                List<GeneSymbol> geneSymbolList = hearsayDAOBeanService.getGeneSymbolDAO()
                                        .findBySymbol(refSeqGene.getName());
                                if (CollectionUtils.isNotEmpty(geneSymbolList)) {
                                    referenceSequence.setGene(geneSymbolList.get(0).getGene());
                                }
                            }
                        }

                        // set genomic location/range
                        List<TranscriptMapsExons> exons = transcriptMaps.getExons();
                        exons.sort((a, b) -> a.getContigStart().compareTo(b.getContigStart()));

                        Location genomicLocation = new Location(exons.get(0).getContigStart(), exons.get(exons.size() - 1).getContigEnd());
                        genomicLocation.setId(hearsayDAOBeanService.getLocationDAO().save(genomicLocation));
                        logger.info(genomicLocation.toString());
                        referenceSequence.setGenomicLocation(genomicLocation);

                        hearsayDAOBeanService.getReferenceSequenceDAO().save(referenceSequence);
                        logger.info(referenceSequence.toString());
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
                List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(refSeqVersion,
                        versionId);
                if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                    RefSeqGene refSeqGene = refSeqGeneList.get(0);
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO().findBySymbol(refSeqGene.getName());
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

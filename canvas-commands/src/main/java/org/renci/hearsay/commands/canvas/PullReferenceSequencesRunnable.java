package org.renci.hearsay.commands.canvas;

import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_CDS_PROTEIN_ID;
import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_GENE_ID;
import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_TRANSCRIPT_VERSION_ID;
import static org.renci.hearsay.commands.canvas.Constants.REF_GENOME_REF_ID;
import static org.renci.hearsay.commands.canvas.Constants.REF_GENOME_REF_SEQ_VERSION_ACCESSION;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
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

public class PullReferenceSequencesRunnable implements Runnable {

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
    public void run() {
        logger.debug("ENTERING call()");

        try {

            GenomeRef genomeRef = canvasDAOBeanService.getGenomeRefDAO().findById(genomeRefId);
            logger.info(genomeRef.toString());

            List<GenomeReference> genomeReferences = hearsayDAOBeanService.getGenomeReferenceDAO()
                    .findByIdentifierSystemAndValue(REF_GENOME_REF_ID, genomeRefId.toString());

            // assume that reference sequences can't be shared....locations & identifiers will vary from
            // datasource to datasource

            List<GenomeRefSeq> foundGenomeRefSeqs = canvasDAOBeanService.getGenomeRefSeqDAO().findBySeqType("Chromosome");

            if (CollectionUtils.isNotEmpty(foundGenomeRefSeqs)) {
                logger.info("foundGenomeRefSeqs.size() = {}", foundGenomeRefSeqs.size());
                Executors.newSingleThreadExecutor().submit(() -> {
                    for (GenomeRefSeq genomeRefSeq : foundGenomeRefSeqs) {
                        try {
                            String versionedGenomicAccession = genomeRefSeq.getVerAccession();
                            Identifier versionedGenomicAccessionIdentifier = new Identifier(REF_GENOME_REF_SEQ_VERSION_ACCESSION,
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
                }).get();
            }

            List<Transcript> foundTranscripts = canvasDAOBeanService.getTranscriptDAO().findByGenomeRefIdAndRefSeqVersion(genomeRefId,
                    refSeqVersion);

            if (CollectionUtils.isEmpty(foundTranscripts)) {
                logger.warn("No transcripts found");
                return;
            }

            logger.info("foundTranscripts.size() = {}", foundTranscripts.size());

            ExecutorService es = Executors.newFixedThreadPool(2);

            es.submit(() -> {
                for (Transcript transcript : foundTranscripts) {
                    try {
                        String versionedRefSeqAccession = transcript.getVersionId();
                        Identifier transcriptVersionIdIdentifier = new Identifier(REFSEQ_TRANSCRIPT_VERSION_ID, versionedRefSeqAccession);
                        List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                                .findByExample(transcriptVersionIdIdentifier);
                        if (CollectionUtils.isEmpty(foundIdentifierList)) {
                            hearsayDAOBeanService.getIdentifierDAO().save(transcriptVersionIdIdentifier);
                        }
                    } catch (HearsayDAOException e) {
                        e.printStackTrace();
                    }
                }
            });

            es.submit(() -> {

                for (Transcript transcript : foundTranscripts) {
                    try {
                        // set protein identifier
                        String versionedRefSeqAccession = transcript.getVersionId();
                        List<RefSeqCodingSequence> refSeqCodingSequenceList = canvasDAOBeanService.getRefSeqCodingSequenceDAO()
                                .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
                        if (CollectionUtils.isNotEmpty(refSeqCodingSequenceList)) {
                            RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceList.get(0);
                            logger.debug(refSeqCDS.toString());
                            Identifier proteinIdIdentifier = new Identifier(REFSEQ_CDS_PROTEIN_ID, refSeqCDS.getProteinId());
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

            });

            es.shutdown();
            es.awaitTermination(2L, TimeUnit.HOURS);

            ExecutorService transcriptES = Executors.newFixedThreadPool(8);

            for (Transcript transcript : foundTranscripts) {

                transcriptES.submit(() -> {
                    logger.info(transcript.toString());
                    try {
                        List<TranscriptMaps> foundTranscriptMaps = canvasDAOBeanService.getTranscriptMapsDAO()
                                .findByGenomeRefIdAndRefSeqVersionAndTranscriptId("includeAll", genomeRefId, refSeqVersion,
                                        transcript.getAccession());

                        if (CollectionUtils.isEmpty(foundTranscriptMaps)) {
                            logger.info("No TranscriptMaps found");
                            return;
                        }

                        logger.info("foundTranscriptMaps.size(): {}", foundTranscriptMaps.size());
                        TranscriptMaps transcriptMaps = foundTranscriptMaps.get(0);

                        logger.info(transcriptMaps.toString());

                        List<Identifier> referenceSequenceIdentifierList = new ArrayList<Identifier>();

                        String versionedRefSeqAccession = transcriptMaps.getTranscript().getVersionId();
                        Identifier transcriptVersionIdIdentifier = new Identifier(REFSEQ_TRANSCRIPT_VERSION_ID, versionedRefSeqAccession);
                        List<Identifier> foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO()
                                .findByExample(transcriptVersionIdIdentifier);
                        if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                            transcriptVersionIdIdentifier = foundIdentifierList.get(0);
                            referenceSequenceIdentifierList.add(transcriptVersionIdIdentifier);
                        }

                        GenomeRefSeq genomeRefSeq = transcriptMaps.getGenomeRefSeq();
                        String versionedGenomicAccession = genomeRefSeq.getVerAccession();
                        Identifier versionedGenomicAccessionIdentifier = new Identifier(REF_GENOME_REF_SEQ_VERSION_ACCESSION,
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
                            Identifier proteinIdIdentifier = new Identifier(REFSEQ_CDS_PROTEIN_ID, refSeqCDS.getProteinId());
                            foundIdentifierList = hearsayDAOBeanService.getIdentifierDAO().findByExample(proteinIdIdentifier);
                            if (CollectionUtils.isNotEmpty(foundIdentifierList)) {
                                proteinIdIdentifier = foundIdentifierList.get(0);
                                referenceSequenceIdentifierList.add(proteinIdIdentifier);
                            }
                        }

                        if (referenceSequenceIdentifierList.size() < 2) {
                            logger.warn("referenceSequenceIdentifierList.size() < 2");
                            return;
                        }

                        referenceSequenceIdentifierList.forEach(a -> logger.info(a.toString()));

                        List<ReferenceSequence> foundReferenceSequenceList = hearsayDAOBeanService.getReferenceSequenceDAO()
                                .findByIdentifiers(referenceSequenceIdentifierList);

                        if (CollectionUtils.isNotEmpty(foundReferenceSequenceList)) {
                            logger.warn("ReferenceSequences were found...returning: {}", foundReferenceSequenceList.get(0).toString());
                            return;
                        }
                        
                        // creating ReferenceSequence since it didn't already exist
                        ReferenceSequence referenceSequence = new ReferenceSequence();
                        StrandType sType = StrandType.PLUS;
                        if ("-".equals(transcriptMaps.getStrand())) {
                            sType = StrandType.MINUS;
                        }
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
                        referenceSequence.getIdentifiers().addAll(referenceSequenceIdentifierList);
                        referenceSequence.setId(hearsayDAOBeanService.getReferenceSequenceDAO().save(referenceSequence));

                        // set gene
                        List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO()
                                .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionedRefSeqAccession);
                        if (CollectionUtils.isNotEmpty(refSeqGeneList)) {
                            RefSeqGene refSeqGene = refSeqGeneList.get(0);
                            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findByIdentifierSystemAndValue(REFSEQ_GENE_ID,
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

                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        e.printStackTrace();
                    }

                });

            }
            transcriptES.shutdown();
            transcriptES.awaitTermination(2L, TimeUnit.HOURS);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return;
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

}

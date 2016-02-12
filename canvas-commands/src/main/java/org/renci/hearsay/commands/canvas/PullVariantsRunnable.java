package org.renci.hearsay.commands.canvas;

import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_TRANSCRIPT_VERSION_ID;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.CanonicalAllele;
import org.renci.hearsay.dao.model.ComplexityType;
import org.renci.hearsay.dao.model.ContextualAllele;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.ReferenceCoordinate;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullVariantsRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullVariantsRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");

        try {

            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findAll();
            if (CollectionUtils.isEmpty(geneList)) {
                logger.warn("No Genes found");
                return;
            }

            ExecutorService es = Executors.newFixedThreadPool(4);
            logger.info("geneList.size(): {}", geneList.size());

            for (Gene gene : geneList) {

                es.submit(() -> {

                    try {
                        logger.info(gene.toString());

                        List<LocationVariant> locationVariantList = canvasDAOBeanService.getLocationVariantDAO()
                                .findByGeneSymbol(gene.getSymbol());

                        if (CollectionUtils.isEmpty(locationVariantList)) {
                            logger.warn("No LocationVariants found");
                            return;
                        }

                        logger.info("locationVariantList.size(): {}", locationVariantList.size());

                        for (LocationVariant locationVariant : locationVariantList) {
                            logger.info(locationVariant.toString());

                            CanonicalAllele canonicalAllele = new CanonicalAllele();
                            // FIXME don't know when this would be false
                            canonicalAllele.setActive(Boolean.TRUE);
                            // FIXME don't know what makes a it ComplexityType.COMPLEX
                            canonicalAllele.setComplexityType(ComplexityType.SIMPLE);
                            canonicalAllele.setId(hearsayDAOBeanService.getCanonicalAlleleDAO().save(canonicalAllele));

                            List<Variants_61_2> variantList = canvasDAOBeanService.getVariants_61_2_DAO()
                                    .findByLocationVariantId(locationVariant.getId());

                            if (CollectionUtils.isEmpty(variantList)) {
                                logger.warn("No Variants_61_2s found");
                                continue;
                            }

                            for (Variants_61_2 variant : variantList) {

                                logger.info(variant.toString());

                                List<ReferenceSequence> referenceSequenceList = hearsayDAOBeanService.getReferenceSequenceDAO()
                                        .findByIdentifierSystemAndValue(REFSEQ_TRANSCRIPT_VERSION_ID, variant.getKey().getTranscr());

                                if (CollectionUtils.isEmpty(referenceSequenceList)) {
                                    logger.info(variant.getKey().toString());
                                    logger.warn("No ReferenceSequence found");
                                    continue;
                                }

                                ReferenceSequence referenceSequence = referenceSequenceList.get(0);
                                logger.info(referenceSequence.toString());

                                ReferenceCoordinate referenceCoordinate = new ReferenceCoordinate();
                                referenceCoordinate.setReferenceSequence(referenceSequence);
                                referenceCoordinate.setRefAllele(locationVariant.getRef());
                                Location referenceCoordinateLocation = new Location(locationVariant.getPosition(),
                                        locationVariant.getEndPosition());
                                referenceCoordinateLocation.setId(hearsayDAOBeanService.getLocationDAO().save(referenceCoordinateLocation));
                                referenceCoordinate.setId(hearsayDAOBeanService.getReferenceCoordinateDAO().save(referenceCoordinate));
                                logger.info(referenceCoordinate.toString());

                                ContextualAllele contextualAllele = new ContextualAllele();
                                contextualAllele.setAllele(locationVariant.getSeq());
                                contextualAllele.setCanonicalAllele(canonicalAllele);
                                contextualAllele.setReferenceCoordinate(referenceCoordinate);
                                contextualAllele.setId(hearsayDAOBeanService.getContextualAlleleDAO().save(contextualAllele));
                                logger.info(contextualAllele.toString());

                            }

                            // IntronOffset intronOffSet = new IntronOffset();

                            // GenomicVariant genomicVariant = new GenomicVariant();
                            // // genomicVariant.setGene(transcriptRefSeq.getGene());
                            // genomicVariant.setGene(gene);
                            // genomicVariant.setReferenceAllele(variant.getReferenceAllele());
                            // genomicVariant.setVariantAllele(variant.getAlternateAllele());
                            // genomicVariant.setStart(locationVariant.getPosition());
                            // genomicVariant.setStop(locationVariant.getEndPosition());
                            // genomicVariant.setHgvs(variant.getHgvsGenomic());
                            // genomicVariant.setChromosome(locationVariant.getReferenceVersionAccession().getVerAccession());
                            // genomicVariant.setCanonicalVariant(canonicalVariant);
                            //
                            // Long id = hearsayDAOBean.getGenomicVariantDAO().save(genomicVariant);
                            // genomicVariant.setId(id);
                            // logger.debug(genomicVariant.toString());
                            // genomicVariants.add(genomicVariant);
                            //
                            // List<ReferenceClinicalAssertions> assertions =
                            // canvasDAOBean.getReferenceClinicalAssertionsDAO()
                            // .findByLocationVariantIdAndVersion(locationVariant.getId(), 4);
                            // if (assertions != null && !assertions.isEmpty()) {
                            // logger.info("assertions.size(): {}", assertions.size());
                            // for (ReferenceClinicalAssertions assertion : assertions) {
                            // logger.info(assertion.toString());
                            // VariantAssertion variantAssertion = new VariantAssertion();
                            // variantAssertion.setGenomicVariant(genomicVariant);
                            // variantAssertion.setAccession(assertion.getAccession());
                            // variantAssertion.setAssertion(assertion.getAssertion());
                            // variantAssertion.setVersion(assertion.getVersion());
                            // variantAssertion.setId(hearsayDAOBeanService.getVariantAssertionDAO().save(variantAssertion));
                            // logger.info(variantAssertion.toString());
                            // }
                            // }
                            //
                            // List<VariantFrequency> variantFrequencies = canvasDAOBeanService.getVariantFrequencyDAO()
                            // .findByLocationVariantIdAndVersion(locationVariant.getId(), "0.1");
                            // if (variantFrequencies != null && !variantFrequencies.isEmpty()) {
                            //
                            // // get the first one...should be sorted by freq desc
                            // VariantFrequency variantFrequency = variantFrequencies.get(0);
                            // PopulationFrequency pf = new PopulationFrequency();
                            // pf.setFrequency(variantFrequency.getAlternateAlleleFrequency());
                            // pf.setVariantRepresentation(genomicVariant);
                            // pf.setSource("ExAC");
                            // pf.setPopulation(variantFrequency.getPopulation());
                            // pf.setVersion(variantFrequency.getVersion());
                            // pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                            // logger.debug(pf.toString());
                            //
                            // }
                            //
                            // List<OneThousandGenomeSNPFrequencyPopulation> snpFrequencyPopulations =
                            // canvasDAOBeanService
                            // .getSNPFrequencyPopulationDAO().findByLocationVariantIdAndVersion(locationVariant.getId(),
                            // 1);
                            // if (snpFrequencyPopulations != null && !snpFrequencyPopulations.isEmpty()) {
                            // OneThousandGenomeSNPFrequencyPopulation snpFP = snpFrequencyPopulations.get(0);
                            // PopulationFrequency pf = new PopulationFrequency();
                            // pf.setFrequency(snpFP.getAltAlleleFreq().doubleValue());
                            // pf.setVariantRepresentation(genomicVariant);
                            // pf.setSource("1000_GENOME");
                            // pf.setPopulation(snpFP.getPopulation());
                            // pf.setVersion(snpFP.getVersion().toString());
                            // pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                            // logger.debug(pf.toString());
                            // }
                            //
                            // VariantEffect variantEffect = variant.getVariantEffect();
                            // TranscriptVariant transcriptVariant = new TranscriptVariant();
                            // transcriptVariant.setCanonicalVariant(canonicalVariant);
                            // transcriptVariant.setGene(gene);
                            // transcriptVariant.setHgvs(variant.getHgvsTranscript());
                            // transcriptVariant.setTranscript(variant.getTranscr());
                            // if (variant.getTranscrPos() != null) {
                            // transcriptVariant.setStart(variant.getTranscrPos());
                            // }
                            // transcriptVariant.setVariantEffect(variantEffect.getVariantEffect());
                            // Long id = hearsayDAOBeanService.getTranscriptVariantDAO().save(transcriptVariant);
                            // transcriptVariant.setId(id);
                            // logger.debug(transcriptVariant.toString());
                            // transcriptVariants.add(transcriptVariant);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

            }

            es.shutdown();
            es.awaitTermination(1L, TimeUnit.DAYS);
        } catch (HearsayDAOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}

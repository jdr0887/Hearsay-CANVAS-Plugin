package org.renci.hearsay.commons.canvas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.exac.dao.model.VariantFrequency;
import org.renci.hearsay.canvas.genome1k.dao.model.SNPFrequencyPopulation;
import org.renci.hearsay.canvas.refseq.dao.model.VariantEffect;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.CanonicalVariant;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.GenomicVariant;
import org.renci.hearsay.dao.model.PopulationFrequency;
import org.renci.hearsay.dao.model.TranscriptVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullVariantsCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullVariantsCallable() {
        super();
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.info("ENTERING call()");

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 4, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());

        List<Gene> genes = hearsayDAOBean.getGeneDAO().findAll();
        if (genes != null && !genes.isEmpty()) {
            logger.info("genes.size(): {}", genes.size());
            for (Gene gene : genes) {
                logger.info(gene.toString());

                List<CanonicalVariant> canonicalVariants = hearsayDAOBean.getCanonicalVariantDAO().findByGeneName(
                        gene.getName());
                if (canonicalVariants != null && !canonicalVariants.isEmpty()) {
                    logger.info("canonicalVariants.size(): {}", canonicalVariants.size());
                    continue;
                }

                tpe.submit(new Task(gene));
            }
        }

        tpe.shutdown();
        logger.info("LEAVING call()");
        return null;

    }

    class Task implements Callable<Void> {

        private Gene gene;

        public Task(Gene gene) {
            super();
            this.gene = gene;
        }

        public Void call() throws HearsayDAOException {
            // List<TranscriptRefSeq> transcriptRefSeqs = hearsayDAOBean.getTranscriptRefSeqDAO().findAll();
            // if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
            // for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
            // logger.info(transcriptRefSeq.toString());
            // List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(
            // transcriptRefSeq.getGene().getName());

            List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(gene.getName());
            if (variants != null && !variants.isEmpty()) {
                logger.info("variants.size(): {}", variants.size());

                Set<LocationVariant> locationVariantSet = new HashSet<LocationVariant>();

                Set<GenomicVariant> genomicVariants = new HashSet<GenomicVariant>();
                Set<TranscriptVariant> transcriptVariants = new HashSet<TranscriptVariant>();

                CanonicalVariant canonicalVariant = null;

                for (Variants_61_2 variant : variants) {

                    LocationVariant locationVariant = variant.getLocationVariant();
                    if (!locationVariantSet.contains(locationVariant)) {
                        locationVariantSet.add(locationVariant);

                        canonicalVariant = new CanonicalVariant();
                        canonicalVariant.setId(hearsayDAOBean.getCanonicalVariantDAO().save(canonicalVariant));

                        GenomicVariant genomicVariant = new GenomicVariant();
                        // genomicVariant.setGene(transcriptRefSeq.getGene());
                        genomicVariant.setGene(gene);
                        genomicVariant.setReferenceAllele(variant.getReferenceAllele());
                        genomicVariant.setVariantAllele(variant.getAlternateAllele());
                        genomicVariant.setStart(locationVariant.getPosition());
                        genomicVariant.setStop(locationVariant.getEndPosition());
                        genomicVariant.setHgvs(variant.getHgvsGenomic());
                        genomicVariant.setChromosome(locationVariant.getReferenceVersionAccession().getVerAccession());
                        genomicVariant.setCanonicalVariant(canonicalVariant);

                        Long id = hearsayDAOBean.getGenomicVariantDAO().save(genomicVariant);
                        genomicVariant.setId(id);
                        logger.debug(genomicVariant.toString());
                        genomicVariants.add(genomicVariant);

                        List<VariantFrequency> variantFrequencies = canvasDAOBean.getVariantFrequencyDAO()
                                .findByLocationVariantIdAndVersion(locationVariant.getId(), "0.1");
                        if (variantFrequencies != null && !variantFrequencies.isEmpty()) {

                            // get the first one...should be sorted by freq desc
                            VariantFrequency variantFrequency = variantFrequencies.get(0);
                            PopulationFrequency pf = new PopulationFrequency();
                            pf.setFrequency(variantFrequency.getAlternateAlleleFrequency());
                            pf.setVariantRepresentation(genomicVariant);
                            pf.setSource("ExAC");
                            pf.setPopulation(variantFrequency.getPopulation());
                            pf.setVersion(variantFrequency.getVersion());
                            pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                            logger.debug(pf.toString());

                        }

                        List<SNPFrequencyPopulation> snpFrequencyPopulations = canvasDAOBean
                                .getSNPFrequencyPopulationDAO().findByLocationVariantIdAndVersion(
                                        locationVariant.getId(), 1);
                        if (snpFrequencyPopulations != null && !snpFrequencyPopulations.isEmpty()) {
                            SNPFrequencyPopulation snpFP = snpFrequencyPopulations.get(0);
                            PopulationFrequency pf = new PopulationFrequency();
                            pf.setFrequency(snpFP.getAltAlleleFreq().doubleValue());
                            pf.setVariantRepresentation(genomicVariant);
                            pf.setSource("1000_GENOME");
                            pf.setPopulation(snpFP.getPopulation());
                            pf.setVersion(snpFP.getVersion().toString());
                            pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                            logger.debug(pf.toString());
                        }

                    }

                    VariantEffect variantEffect = variant.getVariantEffect();
                    TranscriptVariant transcriptVariant = new TranscriptVariant();
                    transcriptVariant.setCanonicalVariant(canonicalVariant);
                    transcriptVariant.setGene(gene);
                    transcriptVariant.setHgvs(variant.getHgvsTranscript());
                    transcriptVariant.setTranscript(variant.getTranscr());
                    if (variant.getTranscrPos() != null) {
                        transcriptVariant.setStart(variant.getTranscrPos());
                    }
                    transcriptVariant.setVariantEffect(variantEffect.getVariantEffect());
                    Long id = hearsayDAOBean.getTranscriptVariantDAO().save(transcriptVariant);
                    transcriptVariant.setId(id);
                    logger.debug(transcriptVariant.toString());
                    transcriptVariants.add(transcriptVariant);

                }

            }

            logger.info("LEAVING call()");
            return null;
        }
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
}

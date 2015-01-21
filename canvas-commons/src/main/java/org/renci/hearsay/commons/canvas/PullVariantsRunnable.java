package org.renci.hearsay.commons.canvas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
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
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.renci.hearsay.dao.model.TranscriptVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullVariantsRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullVariantsRunnable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullVariantsRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.info("ENTERING call()");
        try {

            // List<TranscriptRefSeq> transcriptRefSeqs = hearsayDAOBean.getTranscriptRefSeqDAO().findAll();
            // if (transcriptRefSeqs != null && !transcriptRefSeqs.isEmpty()) {
            // for (TranscriptRefSeq transcriptRefSeq : transcriptRefSeqs) {
            // logger.info(transcriptRefSeq.toString());
            // List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(
            // transcriptRefSeq.getGene().getName());

            List<Gene> genes = hearsayDAOBean.getGeneDAO().findAll();
            if (genes != null && !genes.isEmpty()) {
                for (Gene gene : genes) {
                    List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(gene.getName());

                    if (variants != null && !variants.isEmpty()) {

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

                                Set<PopulationFrequency> populationFrequencies = new HashSet<>();
                                List<MaxVariantFrequency> maxVariantFrequencies = canvasDAOBean
                                        .getMaxVariantFrequencyDAO().findByLocationVariantIdAndVersion(
                                                locationVariant.getId(), "0.1");
                                if (maxVariantFrequencies != null && !maxVariantFrequencies.isEmpty()) {
                                    for (MaxVariantFrequency maxVariantFrequency : maxVariantFrequencies) {
                                        PopulationFrequency pf = new PopulationFrequency();
                                        pf.setFrequency(maxVariantFrequency.getMaxAlleleFrequency());
                                        pf.setSource("ExAC");
                                        pf.setVersion(maxVariantFrequency.getVersion());
                                        pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                                        populationFrequencies.add(pf);
                                    }
                                }

                                List<SNPFrequencyPopulation> snpFrequencyPopulations = canvasDAOBean
                                        .getSNPFrequencyPopulationDAO().findByLocationVariantIdAndVersion(
                                                locationVariant.getId(), 1);
                                if (snpFrequencyPopulations != null && !snpFrequencyPopulations.isEmpty()) {
                                    for (SNPFrequencyPopulation snpFP : snpFrequencyPopulations) {
                                        PopulationFrequency pf = new PopulationFrequency();
                                        pf.setFrequency(snpFP.getAltAlleleFreq().doubleValue());
                                        pf.setSource("1000_GENOME");
                                        pf.setVersion(snpFP.getVersion().toString());
                                        pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                                        populationFrequencies.add(pf);
                                    }
                                }

                                GenomicVariant genomicVariant = new GenomicVariant();
                                genomicVariant.setPopulationFrequencies(populationFrequencies);
                                // genomicVariant.setGene(transcriptRefSeq.getGene());
                                genomicVariant.setGene(gene);
                                genomicVariant.setReferenceAllele(variant.getReferenceAllele());
                                genomicVariant.setVariantAllele(variant.getAlternateAllele());
                                genomicVariant.setStart(locationVariant.getPosition());
                                genomicVariant.setStop(locationVariant.getEndPosition());
                                genomicVariant.setHgvs(variant.getHgvsGenomic());
                                genomicVariant.setChromosome(locationVariant.getReferenceVersionAccession()
                                        .getVerAccession());
                                genomicVariant.setCanonicalVariant(canonicalVariant);

                                Long id = hearsayDAOBean.getGenomicVariantDAO().save(genomicVariant);
                                genomicVariant.setId(id);
                                logger.info(genomicVariant.toString());
                                genomicVariants.add(genomicVariant);
                            }

                            VariantEffect variantEffect = variant.getVariantEffect();
                            TranscriptVariant transcriptVariant = new TranscriptVariant();
                            transcriptVariant.setCanonicalVariant(canonicalVariant);
                            // transcriptVariant.setGene(transcriptRefSeq.getGene());
                            transcriptVariant.setGene(gene);
                            transcriptVariant.setHgvs(variant.getHgvsTranscript());
                            transcriptVariant.setTranscript(variant.getTranscr());
                            if (variant.getTranscrPos() != null) {
                                transcriptVariant.setStart(variant.getTranscrPos());
                            }
                            transcriptVariant.setVariantEffect(variantEffect.getVariantEffect());
                            Long id = hearsayDAOBean.getTranscriptVariantDAO().save(transcriptVariant);
                            transcriptVariant.setId(id);
                            logger.info(transcriptVariant.toString());
                            transcriptVariants.add(transcriptVariant);

                        }

                    }

                }
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }
        logger.info("LEAVING call()");
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

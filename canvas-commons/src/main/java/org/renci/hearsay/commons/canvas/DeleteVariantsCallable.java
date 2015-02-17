package org.renci.hearsay.commons.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.CanonicalVariant;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.GenomicVariant;
import org.renci.hearsay.dao.model.MolecularConsequence;
import org.renci.hearsay.dao.model.PopulationFrequency;
import org.renci.hearsay.dao.model.TranscriptVariant;
import org.renci.hearsay.dao.model.TranslationVariant;
import org.renci.hearsay.dao.model.VariantRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteVariantsCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(DeleteVariantsCallable.class);

    private HearsayDAOBean hearsayDAOBean;

    private String geneName;

    public DeleteVariantsCallable(HearsayDAOBean hearsayDAOBean) {
        super();
        this.hearsayDAOBean = hearsayDAOBean;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.info("ENTERING call()");

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 2, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());

        List<Gene> genes = new ArrayList<Gene>();

        if (StringUtils.isNotEmpty(geneName)) {
            genes.addAll(hearsayDAOBean.getGeneDAO().findByName(geneName));
        } else {
            genes.addAll(hearsayDAOBean.getGeneDAO().findAll());
        }

        if (genes != null && !genes.isEmpty()) {
            logger.info("genes.size(): {}", genes.size());
            for (Gene gene : genes) {
                logger.info(gene.toString());
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

        @Override
        public Void call() throws HearsayDAOException {

            List<CanonicalVariant> canonicalVariants = hearsayDAOBean.getCanonicalVariantDAO().findByGeneName(
                    gene.getName());
            if (canonicalVariants != null && !canonicalVariants.isEmpty()) {
                for (CanonicalVariant canonicalVariant : canonicalVariants) {

                    Set<VariantRepresentation> variantRepresentations = canonicalVariant.getVariants();
                    if (variantRepresentations != null && !variantRepresentations.isEmpty()) {

                        List<GenomicVariant> genomicVariants = new ArrayList<>();
                        List<TranscriptVariant> transcriptVariants = new ArrayList<>();
                        List<TranslationVariant> translationVariants = new ArrayList<>();
                        for (VariantRepresentation variantRepresentation : variantRepresentations) {
                            logger.info("Deleting: {}", variantRepresentation.toString());
                            if (variantRepresentation instanceof GenomicVariant) {
                                genomicVariants.add((GenomicVariant) variantRepresentation);
                            }
                            if (variantRepresentation instanceof TranscriptVariant) {
                                transcriptVariants.add((TranscriptVariant) variantRepresentation);
                            }
                            if (variantRepresentation instanceof TranslationVariant) {
                                translationVariants.add((TranslationVariant) variantRepresentation);
                            }
                            Set<PopulationFrequency> populationFrequencies = variantRepresentation
                                    .getPopulationFrequencies();
                            if (populationFrequencies != null && !populationFrequencies.isEmpty()) {
                                List<PopulationFrequency> pfList = new ArrayList<>(populationFrequencies);
                                hearsayDAOBean.getPopulationFrequencyDAO().delete(pfList);
                            }
                            Set<MolecularConsequence> molecularConsequences = variantRepresentation.getConsequences();
                            if (molecularConsequences != null && !molecularConsequences.isEmpty()) {
                                List<MolecularConsequence> pfList = new ArrayList<>(molecularConsequences);
                                hearsayDAOBean.getMolecularConsequenceDAO().delete(pfList);
                            }
                        }
                        if (!genomicVariants.isEmpty()) {
                            hearsayDAOBean.getGenomicVariantDAO().delete(genomicVariants);
                        }
                        if (!transcriptVariants.isEmpty()) {
                            hearsayDAOBean.getTranscriptVariantDAO().delete(transcriptVariants);
                        }
                        if (!translationVariants.isEmpty()) {
                            hearsayDAOBean.getTranslationVariantDAO().delete(translationVariants);
                        }
                    }
                }
            }
            return null;
        }
    }

    public HearsayDAOBean getHearsayDAOBean() {
        return hearsayDAOBean;
    }

    public void setHearsayDAOBean(HearsayDAOBean hearsayDAOBean) {
        this.hearsayDAOBean = hearsayDAOBean;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

}

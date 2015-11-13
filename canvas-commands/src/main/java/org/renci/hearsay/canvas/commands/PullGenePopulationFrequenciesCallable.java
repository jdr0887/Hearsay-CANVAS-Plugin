package org.renci.hearsay.canvas.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.PopulationFrequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenePopulationFrequenciesCallable implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesCallable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenePopulationFrequenciesCallable(CANVASDAOBeanService canvasDAOBeanService,
            HearsayDAOBeanService hearsayDAOBeanService) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");

        try {

            ExecutorService findGenesExecutorService = Executors.newFixedThreadPool(4);
            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findAll();
            final Set<Gene> foundGeneSet = new HashSet<Gene>();
            for (final Gene gene : geneList) {

                findGenesExecutorService.submit(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            // non-granular search for population frequencies
                            List<PopulationFrequency> alreadyPersistedPopulationFrequencies = hearsayDAOBeanService
                                    .getPopulationFrequencyDAO().findByGeneId(gene.getId());
                            if (CollectionUtils.isEmpty(alreadyPersistedPopulationFrequencies)) {
                                foundGeneSet.add(gene);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
            findGenesExecutorService.shutdown();
            findGenesExecutorService.awaitTermination(1, TimeUnit.DAYS);

            ExecutorService persistPopulationFrequencyExecutorService = Executors.newFixedThreadPool(4);
            if (CollectionUtils.isNotEmpty(foundGeneSet)) {
                for (final Gene gene : foundGeneSet) {

                    persistPopulationFrequencyExecutorService.submit(new Runnable() {

                        @Override
                        public void run() {

                            logger.info(gene.toString());

                            try {

                                List<Variants_61_2> variants = canvasDAOBeanService.getVariants_61_2_DAO()
                                        .findByGeneName(gene.getSymbol());

                                if (CollectionUtils.isNotEmpty(variants)) {

                                    logger.info("variants.size(): {}", variants.size());

                                    for (final Variants_61_2 variant : variants) {

                                        final LocationVariant locationVariant = variant.getLocationVariant();

                                        if (locationVariant != null) {

                                            // logger.debug(locationVariant.toString());
                                            //
                                            // List<MaxFreq> clinbinMaxVariantFrequencies = locationVariant
                                            // .getClinbinMaxVariantFrequencies();
                                            // if (CollectionUtils.isNotEmpty(clinbinMaxVariantFrequencies)) {
                                            // for (final MaxFreq maxFreq : clinbinMaxVariantFrequencies) {
                                            // logger.debug(maxFreq.toString());
                                            //
                                            // PopulationFrequency pf = new PopulationFrequency();
                                            // pf.setFrequency(maxFreq.getMaxAlleleFreq());
                                            // pf.setGene(gene);
                                            // pf.setSource("CLINBIN");
                                            // pf.setVersion(maxFreq.getGen1000Version().toString());
                                            // Location location = new Location(locationVariant.getPosition(),
                                            // locationVariant.getEndPosition());
                                            // location.setId(hearsayDAOBean.getLocationDAO().save(location));
                                            // pf.setPosition(location);
                                            // pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                                            //
                                            // }
                                            // }

                                            List<MaxVariantFrequency> exacMaxVariantFrequencies = locationVariant
                                                    .getMaxVariantFrequencies();
                                            if (CollectionUtils.isNotEmpty(exacMaxVariantFrequencies)) {
                                                for (final MaxVariantFrequency maxFreq : exacMaxVariantFrequencies) {
                                                    logger.debug(maxFreq.toString());

                                                    PopulationFrequency pf = new PopulationFrequency();
                                                    pf.setFrequency(maxFreq.getMaxAlleleFrequency());
                                                    pf.setGene(gene);
                                                    pf.setSource("EXAC");
                                                    pf.setVersion(maxFreq.getKey().getVersion());
                                                    Location location = new Location(locationVariant.getPosition(),
                                                            locationVariant.getEndPosition());
                                                    location.setId(
                                                            hearsayDAOBeanService.getLocationDAO().save(location));
                                                    pf.setPosition(location);
                                                    pf.setId(
                                                            hearsayDAOBeanService.getPopulationFrequencyDAO().save(pf));
                                                }
                                            }

                                        }

                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });

                }
            }
            persistPopulationFrequencyExecutorService.shutdown();
            persistPopulationFrequencyExecutorService.awaitTermination(4, TimeUnit.DAYS);

        } catch (Exception e) {
            logger.error("Error", e);
            throw new HearsayDAOException(e.getMessage());
        }

        return null;
    }

}
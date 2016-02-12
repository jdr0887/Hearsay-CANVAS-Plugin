package org.renci.hearsay.commands.canvas;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.PopulationFrequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenePopulationFrequenciesRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenePopulationFrequenciesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");

        try {

            List<Gene> geneList = hearsayDAOBeanService.getGeneDAO().findAll();

            ExecutorService es = Executors.newFixedThreadPool(4);
            if (CollectionUtils.isNotEmpty(geneList)) {
                for (Gene gene : geneList) {

                    es.submit(() -> {

                        logger.info(gene.toString());

                        try {

                            List<Variants_61_2> variants = canvasDAOBeanService.getVariants_61_2_DAO().findByGeneName(gene.getSymbol());

                            if (CollectionUtils.isEmpty(variants)) {
                                logger.warn("No variants found");
                                return;
                            }

                            logger.info("variants.size(): {}", variants.size());

                            for (final Variants_61_2 variant : variants) {

                                LocationVariant locationVariant = variant.getLocationVariant();

                                if (locationVariant != null) {

                                    logger.debug(locationVariant.toString());
                                    // List<MaxFreq> clinbinMaxVariantFrequencies = canvasDAOBeanService.getMaxFreqDAO()
                                    // .findByLocationVariantId(locationVariant.getId());
                                    // if (CollectionUtils.isNotEmpty(clinbinMaxVariantFrequencies)) {
                                    // logger.debug("clinbinMaxVariantFrequencies.size(): {}",
                                    // clinbinMaxVariantFrequencies.size());
                                    // for (final MaxFreq maxFreq : clinbinMaxVariantFrequencies) {
                                    // logger.debug(maxFreq.toString());
                                    // Location location = new Location(locationVariant.getPosition(),
                                    // locationVariant.getEndPosition());
                                    // location.setId(hearsayDAOBeanService.getLocationDAO().save(location));
                                    // PopulationFrequency populationFrequency = new
                                    // PopulationFrequency(maxFreq.getMaxAlleleFreq(),
                                    // "CLINBIN", maxFreq.getKey().getGen1000Version().toString());
                                    // populationFrequency.setPosition(location);
                                    // populationFrequency.setGene(gene);
                                    // populationFrequency
                                    // .setId(hearsayDAOBeanService.getPopulationFrequencyDAO().save(populationFrequency));
                                    // logger.info(populationFrequency.toString());
                                    // }
                                    // }

                                    List<MaxVariantFrequency> exacMaxVariantFrequencies = canvasDAOBeanService.getMaxVariantFrequencyDAO()
                                            .findByLocationVariantIdAndFrequencyThreshold(locationVariant.getId(), 0.05D);
                                    if (CollectionUtils.isNotEmpty(exacMaxVariantFrequencies)) {
                                        logger.info("exacMaxVariantFrequencies.size(): {}", exacMaxVariantFrequencies.size());
                                        for (final MaxVariantFrequency maxFreq : exacMaxVariantFrequencies) {
                                            logger.info(maxFreq.toString());
                                            Location location = new Location(locationVariant.getPosition(),
                                                    locationVariant.getEndPosition());
                                            location.setId(hearsayDAOBeanService.getLocationDAO().save(location));
                                            PopulationFrequency populationFrequency = new PopulationFrequency(
                                                    maxFreq.getMaxAlleleFrequency(), "EXAC", maxFreq.getKey().getVersion());
                                            populationFrequency.setPosition(location);
                                            populationFrequency.setGene(gene);
                                            populationFrequency
                                                    .setId(hearsayDAOBeanService.getPopulationFrequencyDAO().save(populationFrequency));
                                            logger.info(populationFrequency.toString());
                                        }
                                    }

                                }

                            }

                        } catch (Exception e) {
                            logger.error("Error", e);
                            e.printStackTrace();
                        }

                    });

                }
            }
            es.shutdown();
            es.awaitTermination(4L, TimeUnit.HOURS);

        } catch (Exception e) {
            logger.error("Error", e);
            e.printStackTrace();
        }

    }

}

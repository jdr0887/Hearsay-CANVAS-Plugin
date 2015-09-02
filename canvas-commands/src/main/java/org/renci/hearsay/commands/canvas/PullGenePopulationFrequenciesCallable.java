package org.renci.hearsay.commands.canvas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.Location;
import org.renci.hearsay.dao.model.PopulationFrequency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenePopulationFrequenciesCallable implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(PullGenePopulationFrequenciesCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullGenePopulationFrequenciesCallable() {
        super();
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");

        try {

            Set<Gene> genes2Process = new HashSet<Gene>();
            List<Gene> geneList = hearsayDAOBean.getGeneDAO().findAll();

            if (CollectionUtils.isNotEmpty(geneList)) {
                for (final Gene gene : geneList) {
                    // non-granular search for population frequencies
                    List<PopulationFrequency> alreadyPersistedPopulationFrequencies = hearsayDAOBean
                            .getPopulationFrequencyDAO().findByGeneName(gene.getName());
                    if (CollectionUtils.isNotEmpty(alreadyPersistedPopulationFrequencies)) {
                        logger.info("skipping: {}", gene.toString());
                        continue;
                    }
                    genes2Process.add(gene);
                }
            }

            ExecutorService es = Executors.newFixedThreadPool(4);

            if (CollectionUtils.isNotEmpty(genes2Process)) {
                for (final Gene gene : genes2Process) {

                    List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(gene.getName());

                    if (CollectionUtils.isNotEmpty(variants)) {

                        logger.info(gene.toString());
                        logger.info("variants.size(): {}", variants.size());

                        for (final Variants_61_2 variant : variants) {

                            es.submit(new Runnable() {

                                @Override
                                public void run() {
                                    try {

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
                                                    .getExacMaxVariantFrequencies();
                                            if (CollectionUtils.isNotEmpty(exacMaxVariantFrequencies)) {
                                                for (final MaxVariantFrequency maxFreq : exacMaxVariantFrequencies) {
                                                    logger.debug(maxFreq.toString());

                                                    PopulationFrequency pf = new PopulationFrequency();
                                                    pf.setFrequency(maxFreq.getMaxAlleleFrequency());
                                                    pf.setGene(gene);
                                                    pf.setSource("EXAC");
                                                    pf.setVersion(maxFreq.getVersion());
                                                    Location location = new Location(locationVariant.getPosition(),
                                                            locationVariant.getEndPosition());
                                                    location.setId(hearsayDAOBean.getLocationDAO().save(location));
                                                    pf.setPosition(location);
                                                    pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
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

                }
            }
            es.shutdown();
            es.awaitTermination(2, TimeUnit.DAYS);

        } catch (Exception e) {
            logger.error("Error", e);
            throw new HearsayDAOException(e.getMessage());
        }

        return null;
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

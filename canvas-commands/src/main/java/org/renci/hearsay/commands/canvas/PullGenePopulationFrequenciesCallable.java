package org.renci.hearsay.commands.canvas;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
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
            List<Gene> geneList = hearsayDAOBean.getGeneDAO().findAll();
            if (CollectionUtils.isNotEmpty(geneList)) {
                for (Gene gene : geneList) {

                    logger.info(gene.toString());
                    List<Variants_61_2> variants = canvasDAOBean.getVariants_61_2_DAO().findByGeneName(gene.getName());

                    if (CollectionUtils.isNotEmpty(variants)) {

                        for (Variants_61_2 variant : variants) {

                            LocationVariant locationVariant = variant.getLocationVariant();
                            if (locationVariant != null) {

                                logger.info(locationVariant.toString());

                                List<MaxFreq> clinbinMaxVariantFrequencies = locationVariant
                                        .getClinbinMaxVariantFrequencies();
                                if (CollectionUtils.isNotEmpty(clinbinMaxVariantFrequencies)) {
                                    for (MaxFreq maxFreq : clinbinMaxVariantFrequencies) {
                                        logger.debug(maxFreq.toString());

                                        PopulationFrequency pf = new PopulationFrequency();
                                        pf.setFrequency(maxFreq.getMaxAlleleFreq());
                                        pf.setGene(gene);
                                        pf.setSource("CLINBIN");
                                        pf.setVersion(maxFreq.getGen1000Version().toString());
                                        Location location = new Location(locationVariant.getPosition(),
                                                locationVariant.getEndPosition());
                                        location.setId(hearsayDAOBean.getLocationDAO().save(location));
                                        pf.setPosition(location);
                                        pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));
                                    }
                                }

                                List<MaxVariantFrequency> exacMaxVariantFrequencies = locationVariant
                                        .getExacMaxVariantFrequencies();
                                if (CollectionUtils.isNotEmpty(exacMaxVariantFrequencies)) {
                                    for (MaxVariantFrequency maxFreq : exacMaxVariantFrequencies) {
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

                        }

                    }

                }
            }
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

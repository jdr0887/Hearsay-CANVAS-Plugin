package org.renci.hearsay.commands.canvas;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.exac.dao.model.MaxVariantFrequency;
import org.renci.hearsay.canvas.genome1k.dao.model.SNPFrequencyPopulation;
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

        List<Gene> geneList = hearsayDAOBean.getGeneDAO().findAll();
        if (CollectionUtils.isNotEmpty(geneList)) {
            for (Gene gene : geneList) {

                List<MaxVariantFrequency> exacMaxVariantFrequencyList = canvasDAOBean.getMaxVariantFrequencyDAO()
                        .findByGeneNameAndMaxAlleleFrequency(gene.getName(), 0.05);

                if (CollectionUtils.isNotEmpty(exacMaxVariantFrequencyList)) {
                    for (MaxVariantFrequency maxVariantFrequency : exacMaxVariantFrequencyList) {

                        PopulationFrequency pf = new PopulationFrequency();
                        pf.setFrequency(maxVariantFrequency.getMaxAlleleFrequency());
                        pf.setGene(gene);
                        pf.setSource("EXAC");
                        LocationVariant locationVariant = maxVariantFrequency.getLocationVariant();
                        Location location = new Location(locationVariant.getPosition(),
                                locationVariant.getEndPosition());
                        location.setId(hearsayDAOBean.getLocationDAO().save(location));
                        pf.setPosition(location);
                        pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));

                    }
                }

                List<MaxFreq> clinbinMaxFreqList = canvasDAOBean.getMaxFreqDAO().findByGeneNameAndMaxAlleleFrequency(
                        gene.getName(), 0.05);

                if (CollectionUtils.isNotEmpty(clinbinMaxFreqList)) {
                    for (MaxFreq maxFreq : clinbinMaxFreqList) {

                        PopulationFrequency pf = new PopulationFrequency();
                        pf.setFrequency(maxFreq.getMaxAlleleFreq());
                        pf.setGene(gene);
                        pf.setSource("CLINBIN");
                        LocationVariant locationVariant = maxFreq.getLocationVariant();
                        Location location = new Location(locationVariant.getPosition(),
                                locationVariant.getEndPosition());
                        location.setId(hearsayDAOBean.getLocationDAO().save(location));
                        pf.setPosition(location);
                        pf.setId(hearsayDAOBean.getPopulationFrequencyDAO().save(pf));

                    }
                }

            }
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

package org.renci.hearsay.canvas.commands;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.GeneSymbol;
import org.renci.hearsay.dao.model.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    private String refSeqVersion;

    public PullGenesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService, String refSeqVersion) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.refSeqVersion = refSeqVersion;
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {

            List<RefSeqGene> refSeqGeneList = canvasDAOBeanService.getRefSeqGeneDAO().findByRefSeqVersion(refSeqVersion);
            if (CollectionUtils.isNotEmpty(refSeqGeneList)) {
                for (RefSeqGene refSeqGene : refSeqGeneList) {

                    // check for existing gene by name
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO().findBySymbol(refSeqGene.getName());
                    if (CollectionUtils.isNotEmpty(alreadyPersistedGeneList)) {

                        Identifier identifier = new Identifier("canvas/refseq/gene", refSeqGene.getId().toString());
                        identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                        logger.info(identifier.toString());

                        Gene foundGene = alreadyPersistedGeneList.get(0);
                        foundGene.getIdentifiers().add(identifier);
                        hearsayDAOBeanService.getGeneDAO().save(foundGene);
                        logger.info("Gene is already persisted: {}", foundGene.toString());

                        continue;
                    }

                    // check for existing gene aliases by name
                    List<GeneSymbol> alreadyPersistedGeneSymbolList = hearsayDAOBeanService.getGeneSymbolDAO()
                            .findBySymbol(refSeqGene.getName());
                    if (CollectionUtils.isNotEmpty(alreadyPersistedGeneSymbolList)) {

                        Identifier identifier = new Identifier("canvas/refseq/gene", refSeqGene.getId().toString());
                        identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                        logger.debug(identifier.toString());

                        Gene foundGene = alreadyPersistedGeneSymbolList.get(0).getGene();
                        foundGene.getIdentifiers().add(identifier);
                        hearsayDAOBeanService.getGeneDAO().save(foundGene);
                        logger.info("GeneSymbol is already persisted: {}", foundGene.toString());

                        continue;
                    }

                    Identifier identifier = new Identifier("canvas/refseq/gene", refSeqGene.getId().toString());
                    identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                    logger.debug(identifier.toString());

                    Gene gene = new Gene();
                    gene.setSymbol(refSeqGene.getName());
                    gene.setDescription(refSeqGene.getDescription());
                    gene.getIdentifiers().add(identifier);
                    gene.setId(hearsayDAOBeanService.getGeneDAO().save(gene));
                    logger.info("persisting: {}", gene.toString());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("FINISHED run()");
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

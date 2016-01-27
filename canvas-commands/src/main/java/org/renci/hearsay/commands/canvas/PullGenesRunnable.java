package org.renci.hearsay.commands.canvas;

import static org.renci.hearsay.commands.canvas.Constants.REFSEQ_GENE_ID;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOException;
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
                    Identifier identifier = new Identifier(REFSEQ_GENE_ID, refSeqGene.getId().toString());
                    List<Identifier> possibleIdentifiers = hearsayDAOBeanService.getIdentifierDAO().findByExample(identifier);
                    if (CollectionUtils.isEmpty(possibleIdentifiers)) {
                        hearsayDAOBeanService.getIdentifierDAO().save(identifier);
                    }
                }

                ExecutorService es = Executors.newFixedThreadPool(4);

                for (RefSeqGene refSeqGene : refSeqGeneList) {

                    es.submit(() -> {
                        try {

                            Identifier identifier = new Identifier(REFSEQ_GENE_ID, refSeqGene.getId().toString());
                            List<Identifier> possibleIdentifiers = hearsayDAOBeanService.getIdentifierDAO().findByExample(identifier);
                            if (CollectionUtils.isNotEmpty(possibleIdentifiers)) {
                                identifier = possibleIdentifiers.get(0);
                            } else {
                                identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                            }
                            logger.debug(identifier.toString());

                            // check for existing gene by name
                            List<Gene> alreadyPersistedGeneList = hearsayDAOBeanService.getGeneDAO().findBySymbol(refSeqGene.getName());
                            if (CollectionUtils.isNotEmpty(alreadyPersistedGeneList)) {
                                Gene foundGene = alreadyPersistedGeneList.get(0);
                                if (!foundGene.getIdentifiers().contains(identifier)) {
                                    foundGene.getIdentifiers().add(identifier);
                                    hearsayDAOBeanService.getGeneDAO().save(foundGene);
                                }
                                logger.info("Gene is already persisted: {}", foundGene.toString());
                                return;
                            }

                            // check for existing gene aliases by name
                            List<GeneSymbol> alreadyPersistedGeneSymbolList = hearsayDAOBeanService.getGeneSymbolDAO()
                                    .findBySymbol(refSeqGene.getName());
                            if (CollectionUtils.isNotEmpty(alreadyPersistedGeneSymbolList)) {
                                Gene foundGene = alreadyPersistedGeneSymbolList.get(0).getGene();
                                if (!foundGene.getIdentifiers().contains(identifier)) {
                                    foundGene.getIdentifiers().add(identifier);
                                    hearsayDAOBeanService.getGeneDAO().save(foundGene);
                                }
                                logger.info("GeneSymbol is already persisted: {}", foundGene.toString());
                                return;
                            }

                            Gene gene = new Gene();
                            gene.setSymbol(refSeqGene.getName());
                            gene.setDescription(refSeqGene.getDescription());
                            gene.getIdentifiers().add(identifier);
                            gene.setId(hearsayDAOBeanService.getGeneDAO().save(gene));

                            logger.debug("persisting: {}", gene.toString());
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }

                    });

                }
                es.shutdown();
                es.awaitTermination(20L, TimeUnit.MINUTES);
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

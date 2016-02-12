package org.renci.hearsay.commands.canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.Chromosome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(PullRunnable.class);

    private HearsayDAOBeanService hearsayDAOBeanService;

    private CANVASDAOBeanService canvasDAOBeanService;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService, String refSeqVersion,
            Integer genomeRefId) {
        super();
        this.hearsayDAOBeanService = hearsayDAOBeanService;
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public void run() {
        logger.debug("ENTERING run()");

        // persist dictionary items
        long startPersistChromosomeTime = System.currentTimeMillis();
        try {
            List<String> chromosomeList = new ArrayList<String>();
            for (int i = 1; i < 22; i++) {
                chromosomeList.add(i + "");
            }
            chromosomeList.add("X");
            chromosomeList.add("Y");
            chromosomeList.add("MT");

            for (String chromosome : chromosomeList) {
                List<Chromosome> foundChromosomes = hearsayDAOBeanService.getChromosomeDAO().findByName(chromosome);
                if (CollectionUtils.isEmpty(foundChromosomes)) {
                    hearsayDAOBeanService.getChromosomeDAO().save(new Chromosome(chromosome));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endPersistChromosomeTime = System.currentTimeMillis();

        // persist genes & genome references
        long startPersistGenesAndGenomeReferencesTime = System.currentTimeMillis();
        try {
            ExecutorService es = Executors.newFixedThreadPool(2);
            es.submit(new PullGenomeReferencesRunnable(canvasDAOBeanService, hearsayDAOBeanService));
            es.submit(new PullGenesRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion));
            es.shutdown();
            es.awaitTermination(1L, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endPersistGenesAndGenomeReferencesTime = System.currentTimeMillis();

        // persist reference sequences
        long startPersistReferenceSequencesTime = System.currentTimeMillis();
        try {
            Executors.newSingleThreadExecutor()
                    .submit(new PullReferenceSequencesRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion, genomeRefId))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endPersistReferenceSequencesTime = System.currentTimeMillis();

        // persist alignments
        long startPersistAlignmentsTime = System.currentTimeMillis();
        try {
            Executors.newSingleThreadExecutor()
                    .submit(new PullAlignmentsRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion, genomeRefId)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endPersistAlignmentsTime = System.currentTimeMillis();

        // add alignment UTRs
        long startAddAlignmentUTRsTime = System.currentTimeMillis();
        try {
            Executors.newSingleThreadExecutor().submit(new AddAlignmentUTRsRunnable(hearsayDAOBeanService)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endAddAlignmentUTRsTime = System.currentTimeMillis();

        // persist features
        long startPersistFeaturesTime = System.currentTimeMillis();
        try {
            Executors.newSingleThreadExecutor().submit(new PullFeaturesRunnable(canvasDAOBeanService, hearsayDAOBeanService, refSeqVersion))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endPersistFeaturesTime = System.currentTimeMillis();

        // persist features
        long startPersistGenePopulationFrequenciesTime = System.currentTimeMillis();
        try {
            Executors.newSingleThreadExecutor()
                    .submit(new PullGenePopulationFrequenciesRunnable(canvasDAOBeanService, hearsayDAOBeanService)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endPersistGenePopulationFrequenciesTime = System.currentTimeMillis();

        Long chromosomeDuration = (endPersistChromosomeTime - startPersistChromosomeTime) / 1000;
        logger.info("duration to persist Chromosomes: {} seconds", chromosomeDuration);
        Long genesAndGenomeReferencesDuration = (endPersistGenesAndGenomeReferencesTime - startPersistGenesAndGenomeReferencesTime) / 1000;
        logger.info("duration to persist Genes & GenomeReferences: {} seconds", genesAndGenomeReferencesDuration);
        Long referencesSequencesDuration = (endPersistReferenceSequencesTime - startPersistReferenceSequencesTime) / 1000;
        logger.info("duration to persist ReferenceSequences: {} seconds", referencesSequencesDuration);
        Long alignmentsDuration = (endPersistAlignmentsTime - startPersistAlignmentsTime) / 1000;
        logger.info("duration to persist Alignments: {} seconds", alignmentsDuration);
        Long addAlignmentUTRsDuration = (endAddAlignmentUTRsTime - startAddAlignmentUTRsTime) / 1000;
        logger.info("duration to persist Alignment UTRs: {} seconds", addAlignmentUTRsDuration);
        Long featuresDuration = (endPersistFeaturesTime - startPersistFeaturesTime) / 1000;
        logger.info("duration to persist Features: {} seconds", featuresDuration);
        Long genePopulationFrequenciesDuration = (endPersistGenePopulationFrequenciesTime - startPersistGenePopulationFrequenciesTime)
                / 1000;
        logger.info("duration to persist Gene PopulationFrequencies: {} seconds", genePopulationFrequenciesDuration);

        Long totalDuration = (chromosomeDuration + genesAndGenomeReferencesDuration + referencesSequencesDuration + alignmentsDuration
                + addAlignmentUTRsDuration + featuresDuration + genePopulationFrequenciesDuration) / 60;
        logger.info("Total time to pull from CANVAS: {} minutes", totalDuration);

    }

}

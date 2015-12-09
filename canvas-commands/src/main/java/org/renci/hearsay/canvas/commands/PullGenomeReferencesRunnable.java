package org.renci.hearsay.canvas.commands;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.dao.HearsayDAOBeanService;
import org.renci.hearsay.dao.model.GenomeReference;
import org.renci.hearsay.dao.model.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenomeReferencesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenomeReferencesRunnable.class);

    private CANVASDAOBeanService canvasDAOBeanService;

    private HearsayDAOBeanService hearsayDAOBeanService;

    public PullGenomeReferencesRunnable(CANVASDAOBeanService canvasDAOBeanService, HearsayDAOBeanService hearsayDAOBeanService) {
        super();
        this.canvasDAOBeanService = canvasDAOBeanService;
        this.hearsayDAOBeanService = hearsayDAOBeanService;
    }

    @Override
    public void run() {
        logger.info("ENTERING run()");
        try {
            List<GenomeRef> genomeRefList = canvasDAOBeanService.getGenomeRefDAO().findAll();
            if (CollectionUtils.isNotEmpty(genomeRefList)) {
                for (GenomeRef genomeRef : genomeRefList) {

                    String genomeRefValue = genomeRef.getRefVer();

                    // canvas -> ncbi : BUILD.36.1 -> GRCh36 or BUILD.37.2 -> GRCh37.p2

                    genomeRefValue = genomeRefValue.replace("BUILD.", "GRCh");
                    if (!genomeRefValue.substring(genomeRefValue.indexOf('.'), genomeRefValue.length()).equals(".1")) {
                        genomeRefValue = genomeRefValue.replace(".", ".p");
                    }

                    if (genomeRefValue.substring(genomeRefValue.indexOf('.'), genomeRefValue.length()).equals(".1")) {
                        genomeRefValue = genomeRefValue.replace(".1", "");
                    }

                    List<GenomeReference> foundGenomeReferences = hearsayDAOBeanService.getGenomeReferenceDAO().findByName(genomeRefValue);

                    if (CollectionUtils.isNotEmpty(foundGenomeReferences)) {
                        Identifier identifier = new Identifier("canvas/ref/genomeRef/id", genomeRef.getId().toString());
                        identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                        logger.info(identifier.toString());

                        GenomeReference genomeReference = foundGenomeReferences.get(0);
                        genomeReference.getIdentifiers().add(identifier);
                        hearsayDAOBeanService.getGenomeReferenceDAO().save(genomeReference);
                        logger.info(genomeReference.toString());
                        continue;
                    }

                    Identifier identifier = new Identifier("canvas/ref/genomeRef/id", genomeRef.getId().toString());
                    identifier.setId(hearsayDAOBeanService.getIdentifierDAO().save(identifier));
                    logger.info(identifier.toString());

                    GenomeReference genomeReference = new GenomeReference(genomeRefValue);
                    genomeReference.getIdentifiers().add(identifier);
                    genomeReference.setId(hearsayDAOBeanService.getGenomeReferenceDAO().save(genomeReference));
                    logger.info(genomeReference.toString());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("FINISHED run()");
    }

}

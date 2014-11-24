package org.renci.hearsay.commons.canvas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullGenesRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PullGenesRunnable.class);

    private String refSeqVersion;

    private Integer genomeRefId;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public PullGenesRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");

        try {
            List<Transcript> transcripts = canvasDAOBean.getTranscriptDAO().findByRefSeqVersionAndGenomeRefId(
                    refSeqVersion, genomeRefId);
            if (transcripts != null && !transcripts.isEmpty()) {

                Set<String> versionIdSet = new HashSet<String>();

                for (Transcript transcript : transcripts) {
                    if (!versionIdSet.contains(transcript.getVersionId())) {
                        versionIdSet.add(transcript.getVersionId());
                    }
                }
                
                for (String versionId : versionIdSet) {

                    List<RefSeqGene> refSeqGenes = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(
                            refSeqVersion, versionId);
                    if (refSeqGenes != null && !refSeqGenes.isEmpty()) {
                        RefSeqGene refSeqGene = refSeqGenes.get(0);
                        try {
                            List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(
                                    refSeqGene.getName());
                            if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                                Gene gene = new Gene();
                                gene.setName(refSeqGene.getName());
                                gene.setDescription(refSeqGene.getDescription());
                                hearsayDAOBean.getGeneDAO().save(gene);
                            }
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        } catch (HearsayDAOException e1) {
            e1.printStackTrace();
        }
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
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

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

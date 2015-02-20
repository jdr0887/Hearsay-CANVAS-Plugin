package org.renci.hearsay.commons.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRef;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullTranscriptsCallable implements Callable<Void> {

    private final Logger logger = LoggerFactory.getLogger(PullTranscriptsCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    private Integer genomeRefId;

    public PullTranscriptsCallable(CANVASDAOBean canvasDAOBean, HearsayDAOBean hearsayDAOBean, String refSeqVersion,
            Integer genomeRefId) {
        super();
        this.canvasDAOBean = canvasDAOBean;
        this.hearsayDAOBean = hearsayDAOBean;
        this.refSeqVersion = refSeqVersion;
        this.genomeRefId = genomeRefId;
    }

    @Override
    public Void call() throws HearsayDAOException {
        logger.debug("ENTERING call()");

        List<TranscriptMapsExons> mapsExonsResults = new ArrayList<TranscriptMapsExons>();

        List<TranscriptMapsExons> pulledExons = canvasDAOBean.getTranscriptMapsExonsDAO()
                .findByGenomeRefIdAndRefSeqVersion(Integer.valueOf(genomeRefId), refSeqVersion);

        if (pulledExons != null && !pulledExons.isEmpty()) {
            mapsExonsResults.addAll(pulledExons);
        }
        logger.info("pulledExons.size() = {}", pulledExons.size());

        GenomeRef genomeRef = canvasDAOBean.getGenomeRefDAO().findById(genomeRefId.longValue());

        if (genomeRef == null) {
            logger.warn("No GenomeRef was found");
            return null;
        }

        if (mapsExonsResults != null && mapsExonsResults.size() > 0) {

            Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();

            for (TranscriptMapsExons exon : mapsExonsResults) {

                TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();
                MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                        transcriptionMaps.getMapCount());
                StrandType sType = StrandType.PLUS;
                if ("-".equals(transcriptionMaps.getStrand())) {
                    sType = StrandType.MINUS;
                }
                if (!map.containsKey(mappingKey)) {
                    map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession(), sType));
                }

            }

            logger.info("map.size(): {}", map.size());

            for (TranscriptMapsExons exon : mapsExonsResults) {
                TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
                MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                        transcriptionMaps.getMapCount());

                StrandType sType = StrandType.PLUS;
                if ("-".equals(transcriptionMaps.getStrand())) {
                    sType = StrandType.MINUS;
                }

                Region e = new Region();
                e.setNumber(exon.getKey().getExonNum());
                e.setRegionType(RegionType.EXON);
                if (sType.equals(StrandType.PLUS)) {
                    e.setTranscriptStart(exon.getTranscrStart());
                    e.setTranscriptStop(exon.getTranscrEnd());
                    e.setGenomeStart(exon.getContigStart());
                    e.setGenomeStop(exon.getContigEnd());
                } else {
                    e.setTranscriptStart(exon.getTranscrEnd());
                    e.setTranscriptStop(exon.getTranscrStart());
                    e.setGenomeStart(exon.getContigEnd());
                    e.setGenomeStop(exon.getContigStart());
                }
                map.get(mappingKey).getRegions().add(e);
            }

            for (MappingKey key : map.keySet()) {

                List<RefSeqGene> refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(
                        refSeqVersion, key.getVersionId());

                Gene gene = null;
                if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                    RefSeqGene refSeqGene = refSeqGeneList.get(0);
                    List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(refSeqGene.getName());
                    if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                        gene = new Gene();
                        gene.setName(refSeqGene.getName());
                        gene.setDescription(refSeqGene.getDescription());
                        gene.setId(hearsayDAOBean.getGeneDAO().save(gene));
                    } else {
                        gene = alreadyPersistedGeneList.get(0);
                    }
                }
                logger.info(gene.toString());

                TranscriptRefSeq transcriptRefSeq = new TranscriptRefSeq();
                transcriptRefSeq.setGene(gene);
                transcriptRefSeq.setAccession(key.getVersionId());
                List<TranscriptRefSeq> alreadyPersistedTranscriptList = hearsayDAOBean.getTranscriptRefSeqDAO()
                        .findByExample(transcriptRefSeq);
                if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                    Long id = hearsayDAOBean.getTranscriptRefSeqDAO().save(transcriptRefSeq);
                    transcriptRefSeq.setId(id);
                } else {
                    transcriptRefSeq = alreadyPersistedTranscriptList.get(0);
                }
                logger.info(transcriptRefSeq.toString());
            }

            ThreadPoolExecutor tpe = new ThreadPoolExecutor(8, 8, 3, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>());

            for (MappingKey key : map.keySet()) {
                PersistTranscriptsCallable runnable = new PersistTranscriptsCallable(canvasDAOBean, hearsayDAOBean,
                        refSeqVersion, key.getVersionId(), map.get(key));
                tpe.submit(runnable);
            }

            tpe.shutdown();

        }

        return null;
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

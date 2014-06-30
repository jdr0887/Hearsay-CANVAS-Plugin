package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.MappedTranscript;
import org.renci.hearsay.dao.model.RegionType;
import org.renci.hearsay.dao.model.StrandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingCallable implements Callable<List<org.renci.hearsay.dao.model.Transcript>> {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingCallable.class);

    private String refSeqVersion;

    private Integer genomeRefId;

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    public CANVASCachingCallable() {
        super();
    }

    @Override
    public List<org.renci.hearsay.dao.model.Transcript> call() {
        logger.info("ENTERING call()");
        List<org.renci.hearsay.dao.model.Transcript> results = new ArrayList<org.renci.hearsay.dao.model.Transcript>();

        logger.info(refSeqVersion);
        logger.info(genomeRefId.toString());

        try {

            List<TranscriptMapsExons> mapsExonsResults = canvasDAOBean.getTranscriptMapsExonsDAO()
                    .findByGenomeRefIdAndRefSeqVersion(genomeRefId, refSeqVersion);

            if (mapsExonsResults != null && mapsExonsResults.size() > 0) {
                persistTranscripts(mapsExonsResults);
            }

        } catch (HearsayDAOException e) {
            logger.error("error", e);
        }

        logger.info("LEAVING call()");
        return results;
    }

    private void persistTranscripts(List<TranscriptMapsExons> mapsExonsResults) throws HearsayDAOException {
        logger.info("ENTERING persistTranscripts(List<TranscriptMapsExons>)");

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
            Mapping mapping = map.get(key);

            List<RefSeqGene> refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(
                    refSeqVersion, key.getVersionId());

            Gene gene = null;
            if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                RefSeqGene refSeqGene = refSeqGeneList.get(0);
                List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(refSeqGene.getName());
                if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                    gene = new Gene(refSeqGene.getName(), refSeqGene.getDescription());
                    Long id = hearsayDAOBean.getGeneDAO().save(gene);
                    gene.setId(id);
                } else {
                    gene = alreadyPersistedGeneList.get(0);
                }
            }
            logger.debug(gene.toString());

            TreeSet<Region> regions = mapping.getRegions();

            org.renci.hearsay.dao.model.Transcript transcript = new org.renci.hearsay.dao.model.Transcript();
            transcript.setGene(gene);
            transcript.setAccession(key.getVersionId());
            List<org.renci.hearsay.dao.model.Transcript> alreadyPersistedTranscriptList = hearsayDAOBean
                    .getTranscriptDAO().findByExample(transcript);
            if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                Long id = hearsayDAOBean.getTranscriptDAO().save(transcript);
                transcript.setId(id);
            } else {
                transcript = alreadyPersistedTranscriptList.get(0);
            }
            logger.info(transcript.toString());

            MappedTranscript mappedTranscript = new MappedTranscript();
            mappedTranscript.setTranscript(transcript);
            mappedTranscript.setStrandType(mapping.getStrandType());
            mappedTranscript.setGenomicAccession(mapping.getVersionAccession());
            mappedTranscript.setGenomicStart(regions.first().toRange().getMinimumInteger());
            mappedTranscript.setGenomicStop(regions.last().toRange().getMaximumInteger());
            Long mappedTranscriptId = hearsayDAOBean.getMappedTranscriptDAO().save(mappedTranscript);
            mappedTranscript.setId(mappedTranscriptId);
            logger.info(mappedTranscript.toString());

            List<RefSeqCodingSequence> refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

            if (refSeqCodingSequenceResults == null
                    || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
                Iterator<Region> exonsAscendingIter = regions.iterator();
                while (exonsAscendingIter.hasNext()) {
                    Region exon = exonsAscendingIter.next();
                    exon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR);
                }
            }

            if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
                Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                        .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
                mapping.addUTRs(regionStart, regionEnd);
                mapping.addCDSCoordinates(regionStart);
            }
            mapping.addIntrons();

            for (Region region : mapping.getRegions()) {
                org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
                hearsayRegion.setMappedTranscript(mappedTranscript);
                hearsayRegion.setRegionType(region.getRegionType());
                hearsayRegion.setRegionStart(region.getGenomeStart());
                hearsayRegion.setRegionStop(region.getGenomeStop());

                hearsayRegion.setTranscriptStart(region.getTranscriptStart());
                hearsayRegion.setTranscriptStop(region.getTranscriptStop());

                hearsayRegion.setCdsStart(region.getContigStart());
                hearsayRegion.setCdsStop(region.getContigStop());

                logger.debug(region.toString());
                hearsayDAOBean.getRegionDAO().save(hearsayRegion);
            }

        }
        logger.info("LEAVING persistTranscripts(List<TranscriptMapsExons>)");
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

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

    public Integer getGenomeRefId() {
        return genomeRefId;
    }

    public void setGenomeRefId(Integer genomeRefId) {
        this.genomeRefId = genomeRefId;
    }

}

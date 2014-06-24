package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.model.Exon;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.MappingKey;
import org.renci.hearsay.canvas.ref.dao.model.GenomeRefSeq;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.MappedTranscript;
import org.renci.hearsay.dao.model.ReferenceSequence;
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
        logger.debug("ENTERING call()");
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

        return results;
    }

    private void persistTranscripts(List<TranscriptMapsExons> mapsExonsResults) throws HearsayDAOException {

        Map<MappingKey, Mapping> map = new HashMap<MappingKey, Mapping>();
        for (TranscriptMapsExons exon : mapsExonsResults) {
            TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
            GenomeRefSeq genomeRefSeq = transcriptionMaps.getGenomeRefSeq();
            MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                    transcriptionMaps.getMapCount());
            StrandType sType = StrandType.POSITIVE;
            if ("-".equals(transcriptionMaps.getStrand())) {
                sType = StrandType.NEGATIVE;
            }
            if (!map.containsKey(mappingKey)) {
                map.put(mappingKey, new Mapping(genomeRefSeq.getVerAccession(), sType));
            }
        }
        logger.info("map.size()", map.size());

        for (TranscriptMapsExons exon : mapsExonsResults) {
            TranscriptMaps transcriptionMaps = exon.getTranscriptMaps();
            MappingKey mappingKey = new MappingKey(transcriptionMaps.getTranscript().getVersionId(),
                    transcriptionMaps.getMapCount());

            Exon e = new Exon();
            e.setNumber(exon.getKey().getExonNum());
            e.setTranscriptStart(exon.getTranscrStart());
            e.setTranscriptEnd(exon.getTranscrEnd());
            e.setGenomeStart(exon.getContigStart());
            e.setGenomeEnd(exon.getContigEnd());
            e.setRegionType(RegionType.EXON);
            map.get(mappingKey).getExons().add(e);
        }

        for (MappingKey key : map.keySet()) {
            Mapping mapping = map.get(key);
            logger.info(mapping.toString());

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
            logger.info(gene.toString());

            TreeSet<Exon> exons = mapping.getExons();

            org.renci.hearsay.dao.model.Transcript transcript = new org.renci.hearsay.dao.model.Transcript();
            transcript.setGene(gene);
            transcript.setGenomicAccession(mapping.getVersionAccession());
            transcript.setGenomicStart(exons.first().toRange().getMinimumInteger());
            transcript.setGenomicEnd(exons.last().toRange().getMaximumInteger());

            // List<org.renci.hearsay.dao.model.Transcript> alreadyPersistedTranscriptList = hearsayDAOBean
            // .getTranscriptDAO().findByExample(transcript);
            // if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
            Long id = hearsayDAOBean.getTranscriptDAO().save(transcript);
            transcript.setId(id);
            // } else {
            // transcript = alreadyPersistedTranscriptList.get(0);
            // }
            logger.info(transcript.toString());

            List<RefSeqCodingSequence> refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, key.getVersionId());

            if (refSeqCodingSequenceResults == null
                    || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
                Iterator<Exon> exonsAscendingIter = exons.iterator();
                while (exonsAscendingIter.hasNext()) {
                    Exon exon = exonsAscendingIter.next();
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

            for (Exon exon : mapping.getExons()) {
                MappedTranscript mappedTranscript = new MappedTranscript();
                mappedTranscript.setTranscript(transcript);
                mappedTranscript.setCdsStart(exon.getContigStart());
                mappedTranscript.setCdsEnd(exon.getContigEnd());
                mappedTranscript.setTranscriptStart(exon.getTranscriptStart());
                mappedTranscript.setTranscriptEnd(exon.getTranscriptEnd());
                mappedTranscript.setRegionStart(exon.getGenomeStart());
                mappedTranscript.setRegionEnd(exon.getGenomeEnd());
                mappedTranscript.setStrandType(mapping.getStrandType());
                mappedTranscript.setRegionType(exon.getRegionType());
                logger.info(mappedTranscript.toString());
                hearsayDAOBean.getMappedTranscriptDAO().save(mappedTranscript);
            }

        }

    }

    private void persistReferenceSequence(Set<Transcript> transcriptSet) throws HearsayDAOException {
        Set<String> refSeqAccessionSet = new HashSet<String>();
        // for (Transcript transcript : transcriptSet) {
        // Set<TranscriptRefSeqVers> refSeqVersionSet = transcript.getRefseqVersions();
        // for (TranscriptRefSeqVers vers : refSeqVersionSet) {
        // refSeqAccessionSet.add(vers.getRefseqVer());
        // }
        // }

        for (String refSeqAccession : refSeqAccessionSet) {
            List<ReferenceSequence> foundReferenceSequenceList = hearsayDAOBean.getReferenceSequenceDAO()
                    .findByAccession(refSeqAccession);
            ReferenceSequence rs = new ReferenceSequence(refSeqAccession);
            if (foundReferenceSequenceList == null
                    || (foundReferenceSequenceList != null && !foundReferenceSequenceList.contains(rs))) {
                hearsayDAOBean.getReferenceSequenceDAO().save(rs);
            }
        }

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

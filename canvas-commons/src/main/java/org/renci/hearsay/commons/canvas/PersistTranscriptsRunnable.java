package org.renci.hearsay.commons.canvas;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.jpa.TranscriptUtil;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.dao.model.Region;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.TranscriptAlignment;
import org.renci.hearsay.dao.model.TranscriptRefSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistTranscriptsRunnable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PersistTranscriptsRunnable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    private String versionId;

    private Mapping mapping;

    public PersistTranscriptsRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");

        List<RefSeqGene> refSeqGeneList = null;
        try {
            refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO().findByRefSeqVersionAndTranscriptId(refSeqVersion,
                    versionId);
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        Gene gene = null;
        if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
            RefSeqGene refSeqGene = refSeqGeneList.get(0);
            try {
                List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findByName(refSeqGene.getName());
                if (alreadyPersistedGeneList != null && alreadyPersistedGeneList.isEmpty()) {
                    gene = new Gene();
                    gene.setName(refSeqGene.getName());
                    gene.setDescription(refSeqGene.getDescription());
                    gene.setId(hearsayDAOBean.getGeneDAO().save(gene));
                } else {
                    gene = alreadyPersistedGeneList.get(0);
                }
            } catch (HearsayDAOException e) {
                e.printStackTrace();
            }
        }
        logger.info(gene.toString());

        TreeSet<Region> regions = mapping.getRegions();

        TranscriptRefSeq transcriptRefSeq = new TranscriptRefSeq();
        transcriptRefSeq.setGene(gene);
        transcriptRefSeq.setAccession(versionId);
        try {
            List<TranscriptRefSeq> alreadyPersistedTranscriptList = hearsayDAOBean.getTranscriptRefSeqDAO()
                    .findByExample(transcriptRefSeq);
            if (alreadyPersistedTranscriptList != null && alreadyPersistedTranscriptList.isEmpty()) {
                Long id = hearsayDAOBean.getTranscriptRefSeqDAO().save(transcriptRefSeq);
                transcriptRefSeq.setId(id);
            } else {
                transcriptRefSeq = alreadyPersistedTranscriptList.get(0);
            }
            logger.info(transcriptRefSeq.toString());
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        TranscriptAlignment transcriptAlignment = new TranscriptAlignment();

        transcriptAlignment.setTranscriptRefSeq(transcriptRefSeq);
        transcriptAlignment.setStrandType(mapping.getStrandType());
        transcriptAlignment.setGenomicStart(regions.first().toRange().getMinimumInteger());
        transcriptAlignment.setGenomicStop(regions.last().toRange().getMaximumInteger());

        List<RefSeqCodingSequence> refSeqCodingSequenceResults = null;
        try {
            refSeqCodingSequenceResults = canvasDAOBean.getRefSeqCodingSequenceDAO()
                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionId);
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        if (refSeqCodingSequenceResults == null
                || (refSeqCodingSequenceResults != null && refSeqCodingSequenceResults.isEmpty())) {
            Iterator<Region> exonsAscendingIter = regions.iterator();
            while (exonsAscendingIter.hasNext()) {
                Region exon = exonsAscendingIter.next();
                exon.setRegionType(org.renci.hearsay.dao.model.RegionType.UTR);
            }
        }

        if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
            RefSeqCodingSequence refSeqCDS = refSeqCodingSequenceResults.get(0);
            transcriptAlignment.setProtein(refSeqCDS.getProteinId());
            Set<RegionGroup> regionGroupSet = refSeqCDS.getLocations();
            RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
            RegionGroup regionGroup = regionGroupArray[0];
            Set<RegionGroupRegion> regionGroupRegionSet = regionGroup.getRegionGroupRegions();

            RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                    .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);

            RegionGroupRegion rgr = regionGroupRegionArray[0];
            Integer regionStart = rgr.getRegionStart();
            Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
            transcriptAlignment.setProteinRegionStart(regionStart);
            transcriptAlignment.setProteinRegionStop(regionEnd);
            TranscriptUtil.addUTR5s(mapping, regionStart);
            TranscriptUtil.addUTR3s(mapping, regionEnd);
            TranscriptUtil.addCDSCoordinates(mapping, regionStart);
        }
        TranscriptUtil.addIntrons(mapping);

        try {
            Long mappedTranscriptId = hearsayDAOBean.getTranscriptAlignmentDAO().save(transcriptAlignment);
            transcriptAlignment.setId(mappedTranscriptId);
            logger.info(transcriptAlignment.toString());
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        for (Region region : mapping.getRegions()) {
            org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
            hearsayRegion.setTranscriptAlignment(transcriptAlignment);
            hearsayRegion.setRegionType(region.getRegionType());
            hearsayRegion.setRegionStart(region.getGenomeStart());
            hearsayRegion.setRegionStop(region.getGenomeStop());
            hearsayRegion.setTranscriptStart(region.getTranscriptStart());
            hearsayRegion.setTranscriptStop(region.getTranscriptStop());
            hearsayRegion.setCdsStart(region.getContigStart());
            hearsayRegion.setCdsStop(region.getContigStop());

            logger.info(region.toString());

            try {
                Long id = hearsayDAOBean.getRegionDAO().save(hearsayRegion);
                hearsayRegion.setId(id);
            } catch (HearsayDAOException e) {
                e.printStackTrace();
            }
            transcriptAlignment.getRegions().add(hearsayRegion);
        }
        try {
            hearsayDAOBean.getTranscriptAlignmentDAO().save(transcriptAlignment);
        } catch (HearsayDAOException e) {
            e.printStackTrace();
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

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getRefSeqVersion() {
        return refSeqVersion;
    }

    public void setRefSeqVersion(String refSeqVersion) {
        this.refSeqVersion = refSeqVersion;
    }

}

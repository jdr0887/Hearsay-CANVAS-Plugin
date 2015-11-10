package org.renci.hearsay.commands.canvas;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.GroupLayout.Alignment;

import org.renci.hearsay.canvas.dao.CANVASDAOBean;
import org.renci.hearsay.canvas.dao.model.Mapping;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOBean;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.model.Gene;
import org.renci.hearsay.dao.model.ReferenceSequence;
import org.renci.hearsay.dao.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistTranscriptsCallable implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(PersistTranscriptsCallable.class);

    private CANVASDAOBean canvasDAOBean;

    private HearsayDAOBean hearsayDAOBean;

    private String refSeqVersion;

    private String versionId;

    private Mapping mapping;

    public PersistTranscriptsCallable(CANVASDAOBean canvasDAOBean, HearsayDAOBean hearsayDAOBean, String refSeqVersion,
            String versionId, Mapping mapping) {
        super();
        this.canvasDAOBean = canvasDAOBean;
        this.hearsayDAOBean = hearsayDAOBean;
        this.refSeqVersion = refSeqVersion;
        this.versionId = versionId;
        this.mapping = mapping;
    }

    @Override
    public void run() {
        logger.debug("ENTERING call()");

        Gene gene = null;
        try {
            List<RefSeqGene> refSeqGeneList = canvasDAOBean.getRefSeqGeneDAO()
                    .findByRefSeqVersionAndTranscriptId(refSeqVersion, versionId);
            if (refSeqGeneList != null && !refSeqGeneList.isEmpty()) {
                RefSeqGene refSeqGene = refSeqGeneList.get(0);
                List<Gene> alreadyPersistedGeneList = hearsayDAOBean.getGeneDAO().findBySymbol(refSeqGene.getName());
                gene = alreadyPersistedGeneList.get(0);
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        if (gene == null) {
            logger.error("Gene not found");
            return;
        }
        logger.info(gene.toString());

        ReferenceSequence transcriptRefSeq = null;
        try {
            List<ReferenceSequence> alreadyPersistedTranscriptList = hearsayDAOBean.getReferenceSequenceDAO()
                    .findByIdentifierValue(versionId);
            if (alreadyPersistedTranscriptList != null && !alreadyPersistedTranscriptList.isEmpty()) {
                transcriptRefSeq = alreadyPersistedTranscriptList.get(0);
            }
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        if (transcriptRefSeq == null) {
            logger.error("TranscriptRefSeq not found");
            return;
        }
        logger.info(transcriptRefSeq.toString());

        TreeSet<Region> regions = mapping.getRegions();

        Alignment alignment = new Alignment();

        alignment.setReferenceSequence(transcriptRefSeq);
        alignment.setStrandType(mapping.getStrandType());
        alignment.setGenomicStart(regions.first().toRange().getMinimumInteger());
        alignment.setGenomicStop(regions.last().toRange().getMaximumInteger());

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
            alignment.setProtein(refSeqCDS.getProteinId());
            Set<RegionGroup> regionGroupSet = refSeqCDS.getLocations();
            RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);
            RegionGroup regionGroup = regionGroupArray[0];
            Set<RegionGroupRegion> regionGroupRegionSet = regionGroup.getRegionGroupRegions();

            RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                    .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);

            RegionGroupRegion rgr = regionGroupRegionArray[0];
            Integer regionStart = rgr.getRegionStart();
            Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();
            alignment.setProteinRegionStart(regionStart);
            alignment.setProteinRegionStop(regionEnd);
            TranscriptUtil.addUTR5s(mapping, regionStart);
            TranscriptUtil.addUTR3s(mapping, regionEnd);
            TranscriptUtil.addCDSCoordinates(mapping, regionStart);
        }
        TranscriptUtil.addIntrons(mapping);

        try {
            Long mappedTranscriptId = hearsayDAOBean.getAlignmentDAO().save(alignment);
            alignment.setId(mappedTranscriptId);
            logger.info(alignment.toString());
        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        for (Region region : mapping.getRegions()) {
            org.renci.hearsay.dao.model.Region hearsayRegion = new org.renci.hearsay.dao.model.Region();
            hearsayRegion.setAlignment(alignment);
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
            alignment.getRegions().add(hearsayRegion);
        }
        try {
            hearsayDAOBean.getAlignmentDAO().save(alignment);
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

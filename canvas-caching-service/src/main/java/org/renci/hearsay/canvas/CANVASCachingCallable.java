package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneExternalIdsDAO;
import org.renci.hearsay.canvas.annotation.dao.model.AnnotationGeneExternalIds;
import org.renci.hearsay.canvas.hgnc.dao.HGNCGeneDAO;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.canvas.refseq.dao.RefSeqCodingSequenceDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsExonsDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.RegionType;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;
import org.renci.hearsay.dao.TranscriptDAO;
import org.renci.hearsay.dao.TranscriptIntervalDAO;
import org.renci.hearsay.dao.model.StrandType;
import org.renci.hearsay.dao.model.TranscriptInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CANVASCachingCallable implements Callable<List<org.renci.hearsay.dao.model.Transcript>> {

    private final Logger logger = LoggerFactory.getLogger(CANVASCachingCallable.class);

    private CANVASCachingDAOBean cachingDAOBean;

    private CANVASCachingPropertyBean cachingPropertyBean;

    public CANVASCachingCallable(CANVASCachingDAOBean cachingDAOBean, CANVASCachingPropertyBean cachingPropertyBean) {
        super();
        this.cachingDAOBean = cachingDAOBean;
        this.cachingPropertyBean = cachingPropertyBean;
    }

    @Override
    public List<org.renci.hearsay.dao.model.Transcript> call() {
        logger.debug("ENTERING call()");
        List<org.renci.hearsay.dao.model.Transcript> results = new ArrayList<org.renci.hearsay.dao.model.Transcript>();

        logger.info(cachingPropertyBean.toString());

        String refSeqVersion = cachingPropertyBean.getRefSeqVersion();
        Integer genomeRefId = cachingPropertyBean.getGenomeRefId();

        AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO = cachingDAOBean.getAnnotationGeneExternalIdsDAO();

        RefSeqCodingSequenceDAO refSeqCodingSequenceDAO = cachingDAOBean.getRefSeqCodingSequenceDAO();

        RefSeqGeneDAO refSeqGeneDAO = cachingDAOBean.getRefSeqGeneDAO();

        HGNCGeneDAO hgncGeneDAO = cachingDAOBean.getHGNCGeneDAO();

        TranscriptMapsDAO transcriptionMapsDAO = cachingDAOBean.getTranscriptMapsDAO();

        TranscriptMapsExonsDAO transcriptionMapsExonsDAO = cachingDAOBean.getTranscriptMapsExonsDAO();

        TranscriptDAO transcriptDAO = cachingDAOBean.getHearsayTranscriptDAO();
        TranscriptIntervalDAO transcriptIntervalDAO = cachingDAOBean.getHearsayTranscriptIntervalDAO();

        try {

            List<TranscriptMaps> mapResults = transcriptionMapsDAO.findByGenomeRefIdAndRefSeqVersion(genomeRefId,
                    refSeqVersion);

            logger.info("transcriptMaps.size(): {}", mapResults.size());

            Map<String, Integer> map = new HashMap<String, Integer>();
            if (mapResults != null && mapResults.size() > 0) {
                for (TranscriptMaps transcriptMaps : mapResults) {
                    map.put(transcriptMaps.getTranscript().getVersionId(), transcriptMaps.getMapCount());
                }
            }
            logger.info("versionId/mapCount size: {}", map.size());

            Comparator<TranscriptMapsExons> exonComparator = new Comparator<TranscriptMapsExons>() {

                @Override
                public int compare(TranscriptMapsExons first, TranscriptMapsExons second) {
                    int ret = first.getContigStart().compareTo(second.getContigStart());
                    return ret;
                }

            };

            Map<String, TreeSet<TranscriptMapsExons>> accessionExonMap = new HashMap<String, TreeSet<TranscriptMapsExons>>();
            for (String versionId : map.keySet()) {
                Integer mapCount = map.get(versionId);
                List<TranscriptMapsExons> exons = transcriptionMapsExonsDAO
                        .findByTranscriptVersionIdAndTranscriptMapsMapCount(versionId, mapCount);

                for (TranscriptMapsExons exon : exons) {
                    String key = exon.getTranscriptMaps().getGenomeRefSeq().getVerAccession();
                    if (!accessionExonMap.containsKey(key)) {
                        accessionExonMap.put(key, new TreeSet<TranscriptMapsExons>(exonComparator));
                    }
                    accessionExonMap.get(key).add(exon);
                }
            }

            logger.info("accessionExonMap.size(): {}", accessionExonMap.size());

            Set<TranscriptMapsExons> exonsToAdd = new HashSet<TranscriptMapsExons>();
            for (String key : accessionExonMap.keySet()) {

                org.renci.hearsay.dao.model.Transcript hearsayTranscript = new org.renci.hearsay.dao.model.Transcript();

                TreeSet<TranscriptMapsExons> exons = accessionExonMap.get(key);

                TranscriptMapsExons firstTranscriptMapsExons = exons.first();
                TranscriptMapsExons lastTranscriptMapsExons = exons.last();
                TranscriptMaps transcriptMaps = firstTranscriptMapsExons.getTranscriptMaps();
                Transcript transcript = transcriptMaps.getTranscript();

                Integer lowerBound = firstTranscriptMapsExons.toRange().getMinimumInteger();
                hearsayTranscript.setBoundsStart(lowerBound);

                Integer upperBound = lastTranscriptMapsExons.toRange().getMaximumInteger();
                hearsayTranscript.setBoundsEnd(upperBound);

                String transcriptVersionId = transcript.getVersionId();
                hearsayTranscript.setRefSeqAccession(transcriptVersionId);

                String strand = transcriptMaps.getStrand();
                StrandType sType = StrandType.POSITIVE;
                for (StrandType t : StrandType.values()) {
                    if (t.getValue().equals(strand)) {
                        sType = t;
                    }
                }
                hearsayTranscript.setStrandType(sType);

                logger.info(transcript.toString());
                logger.info(hearsayTranscript.toString());

                // TODO need to add null checks
                RefSeqGene refSeqGene = null;

                List<RefSeqGene> refSeqGeneResults = refSeqGeneDAO.findByRefSeqVersionAndTranscriptId(refSeqVersion,
                        transcript.getVersionId());

                if (refSeqGeneResults != null && !refSeqGeneResults.isEmpty()) {
                    refSeqGene = refSeqGeneResults.get(0);
                }

                if (refSeqGene == null) {
                    logger.info("refSeqGene is null");
                    return results;
                }

                logger.info(refSeqGene.toString());

                Long geneId = refSeqGene.getId();
                hearsayTranscript.setGeneId(geneId);

                String geneName = refSeqGene.getName();
                hearsayTranscript.setGeneName(geneName);

                AnnotationGeneExternalIds annotatedGene = annotationGeneExternalIdsDAO.findById(refSeqGene.getId()
                        .intValue());

                logger.info(annotatedGene.toString());

                HGNCGene hgncGene = hgncGeneDAO.findById(annotatedGene.getGene().getId().intValue());
                if (hgncGene != null) {
                    String geneSymbol = hgncGene.getSymbol();
                    hearsayTranscript.setHgncSymbol(geneSymbol);
                }

                Integer mapCount = transcriptMaps.getMapCount();
                hearsayTranscript.setMapIndex(mapCount);

                Integer nMap = exons.size();
                hearsayTranscript.setMapsTotal(nMap);

                List<RefSeqCodingSequence> refSeqCodingSequenceResults = refSeqCodingSequenceDAO
                        .findByRefSeqVersionAndTranscriptId(refSeqVersion, transcript.getVersionId());

                if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                    String proteinId = refSeqCodingSequenceResults.get(0).getProteinId();
                    hearsayTranscript.setProteinRefSeqAccession(proteinId);
                }

                try {
                    Long id = transcriptDAO.save(hearsayTranscript);
                    hearsayTranscript.setId(id);
                    results.add(hearsayTranscript);
                } catch (HearsayDAOException e) {
                    e.printStackTrace();
                }

                if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {

                    Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                    RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);

                    Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                    RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                            .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                    Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                    Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();

                    // add utr

                    Iterator<TranscriptMapsExons> exonsAscendingIter = exons.iterator();
                    while (exonsAscendingIter.hasNext()) {
                        TranscriptMapsExons exon = exonsAscendingIter.next();
                        if (regionStart < exon.getTranscrStart() || regionStart > exon.getTranscrEnd()) {
                            exon.setRegionType(RegionType.UTR5);
                            if (regionStart != exon.getTranscrStart()) {
                                int v = regionStart - exon.getTranscrStart();
                                int nStrand = strand.equals("+") ? 1 : -1;
                                int gc = exon.getContigStart() + nStrand * v;
                                TranscriptMapsExons utr5 = new TranscriptMapsExons(RegionType.UTR5,
                                        exon.getTranscrStart(), regionStart - 1, exon.getContigStart(), gc - nStrand);
                                exonsToAdd.add(utr5);
                                exon.setContigStart(gc);
                                exon.setTranscrStart(regionStart);
                            }
                        }
                    }
                    if (!exonsToAdd.isEmpty()) {
                        exons.addAll(exonsToAdd);
                        exonsToAdd.clear();
                    }

                    Iterator<TranscriptMapsExons> exonsDescendingIter = exons.descendingIterator();
                    while (exonsDescendingIter.hasNext()) {
                        TranscriptMapsExons exon = exonsDescendingIter.next();
                        if (regionEnd < exon.getTranscrStart() || regionEnd > exon.getTranscrEnd()) {
                            exon.setRegionType(RegionType.UTR3);

                            if (regionStart != exon.getTranscrStart()) {
                                int v = regionEnd - exon.getTranscrStart();
                                int nStrand = strand.equals("+") ? 1 : -1;
                                int gc = exon.getContigStart() + nStrand * v;
                                TranscriptMapsExons utr3 = new TranscriptMapsExons(RegionType.UTR3, regionEnd + 1,
                                        exon.getTranscrEnd(), gc + nStrand, exon.getContigEnd());
                                exonsToAdd.add(utr3);
                                exon.setContigStart(gc);
                                exon.setTranscrStart(regionEnd);
                            }
                        }
                    }
                    if (!exonsToAdd.isEmpty()) {
                        exons.addAll(exonsToAdd);
                        exonsToAdd.clear();
                    }

                    for (TranscriptMapsExons e : exons) {
                        if (e.getRegionType().equals(RegionType.EXON)) {
                            e.setContigStart(e.getTranscrStart() - regionStart + 1);
                            e.setContigEnd(e.getTranscrEnd() - regionStart + 1);
                        }
                    }

                }

                Iterator<TranscriptMapsExons> exonsDescendingIter = exons.descendingIterator();
                while (exonsDescendingIter.hasNext()) {
                    TranscriptMapsExons original = exonsDescendingIter.next();
                    TranscriptMapsExons previous = exons.higher(original);
                    TranscriptMapsExons subsequent = exons.lower(original);
                    if (previous != null) {
                        TranscriptMapsExons intron = null;
                        if (strand.equals("+") && previous.getContigEnd() != original.getContigStart()) {
                            intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, previous.getContigEnd() + 1,
                                    original.getContigStart() - 1);
                        } else if (strand.equals("-") && subsequent != null
                                && subsequent.getContigStart() != previous.getContigEnd()) {
                            intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, original.getContigStart() + 1,
                                    previous.getContigEnd() - 1);
                        }
                        if (intron != null) {
                            exonsToAdd.add(intron);
                        }
                    }
                }
                if (!exonsToAdd.isEmpty()) {
                    exons.addAll(exonsToAdd);
                    exonsToAdd.clear();
                }

                if (strand.equals("+")) {
                    for (TranscriptMapsExons exon : exons) {

                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setTranscript(hearsayTranscript);
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        try {
                            transcriptIntervalDAO.save(tInterval);
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    Iterator<TranscriptMapsExons> iter = exons.descendingIterator();
                    while (iter.hasNext()) {
                        TranscriptMapsExons exon = iter.next();
                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setTranscript(hearsayTranscript);
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        try {
                            transcriptIntervalDAO.save(tInterval);
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        } catch (HearsayDAOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public CANVASCachingDAOBean getCachingDAOBean() {
        return cachingDAOBean;
    }

    public void setCachingDAOBean(CANVASCachingDAOBean cachingDAOBean) {
        this.cachingDAOBean = cachingDAOBean;
    }

    public CANVASCachingPropertyBean getCachingPropertyBean() {
        return cachingPropertyBean;
    }

    public void setCachingPropertyBean(CANVASCachingPropertyBean cachingPropertyBean) {
        this.cachingPropertyBean = cachingPropertyBean;
    }

}

package org.renci.hearsay.canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.collections4.iterators.ReverseListIterator;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqCodingSequence;
import org.renci.hearsay.canvas.refseq.dao.model.RefSeqGene;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroup;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.canvas.refseq.dao.model.RegionType;
import org.renci.hearsay.canvas.refseq.dao.model.Transcript;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMaps;
import org.renci.hearsay.canvas.refseq.dao.model.TranscriptMapsExons;
import org.renci.hearsay.dao.HearsayDAOException;
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

        try {

            List<TranscriptMaps> mapResults = cachingDAOBean.getTranscriptMapsDAO().findByGenomeRefIdAndRefSeqVersion(
                    genomeRefId, refSeqVersion);

            logger.info("transcriptMaps.size(): {}", mapResults.size());

            Map<String, Integer> map = new HashMap<String, Integer>();
            if (mapResults != null && mapResults.size() > 0) {
                for (TranscriptMaps transcriptMaps : mapResults) {
                    map.put(transcriptMaps.getTranscript().getVersionId(), transcriptMaps.getMapCount());
                }
            }
            logger.info("versionId/mapCount size: {}", map.size());

            Map<String, List<TranscriptMapsExons>> accessionExonMap = new HashMap<String, List<TranscriptMapsExons>>();
            for (String versionId : map.keySet()) {
                Integer mapCount = map.get(versionId);
                List<TranscriptMapsExons> exons = getCachingDAOBean().getTranscriptMapsExonsDAO()
                        .findByTranscriptVersionIdAndTranscriptMapsMapCount(versionId, mapCount);

                for (TranscriptMapsExons exon : exons) {
                    String key = exon.getTranscriptMaps().getGenomeRefSeq().getVerAccession();
                    if (!accessionExonMap.containsKey(key)) {
                        accessionExonMap.put(key, new ArrayList<TranscriptMapsExons>());
                    }
                    accessionExonMap.get(key).add(exon);
                }
            }

            logger.info("accessionExonMap.size(): {}", accessionExonMap.size());

            for (String key : accessionExonMap.keySet()) {

                org.renci.hearsay.dao.model.Transcript hearsayTranscript = new org.renci.hearsay.dao.model.Transcript();

                List<TranscriptMapsExons> exons = accessionExonMap.get(key);
                Collections.sort(exons, new Comparator<TranscriptMapsExons>() {

                    @Override
                    public int compare(TranscriptMapsExons first, TranscriptMapsExons second) {
                        int ret = first.getContigStart().compareTo(second.getContigStart());
                        return ret;
                    }

                });

                TranscriptMaps transcriptMaps = exons.get(0).getTranscriptMaps();
                Transcript transcript = transcriptMaps.getTranscript();

                Integer lowerBound = exons.get(0).toRange().getMinimumInteger();
                hearsayTranscript.setBoundsStart(lowerBound);

                Integer upperBound = exons.get(exons.size() - 1).toRange().getMaximumInteger();
                hearsayTranscript.setBoundsEnd(upperBound);

                String transcriptVersionId = transcript.getVersionId();
                hearsayTranscript.setRefSeqAccession(transcriptVersionId);

                String strand = exons.get(0).getTranscriptMaps().getStrand();
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

                List<RefSeqGene> refSeqGeneResults = getCachingDAOBean().getRefSeqGeneDAO()
                        .findByRefSeqVersionAndAnnotationGeneExternalIdsNamespaceAndTranscriptId(refSeqVersion,
                                "refseq", transcript.getVersionId());
                
                if (refSeqGeneResults != null && !refSeqGeneResults.isEmpty()) {
                    refSeqGene = refSeqGeneResults.get(0);
                }

                if (refSeqGene == null) {
                    logger.warn("refSeqGene is null");
                    return results;
                }
                
                Long geneId = refSeqGene.getId();
                hearsayTranscript.setGeneId(geneId);

                String geneName = refSeqGene.getName();
                hearsayTranscript.setGeneName(geneName);

                HGNCGene hgncGene = getCachingDAOBean().getHGNCGeneDAO().findById(geneId.intValue());
                String geneSymbol = hgncGene.getSymbol();
                hearsayTranscript.setHgncSymbol(geneSymbol);

                Integer mapCount = exons.get(0).getTranscriptMaps().getMapCount();
                hearsayTranscript.setMapIndex(mapCount);

                Integer nMap = exons.size();
                hearsayTranscript.setMapsTotal(nMap);

                try {
                    Long id = getCachingDAOBean().getHearsayTranscriptDAO().save(hearsayTranscript);
                    hearsayTranscript.setId(id);
                    results.add(hearsayTranscript);
                } catch (HearsayDAOException e) {
                    e.printStackTrace();
                }

                List<RefSeqCodingSequence> refSeqCodingSequenceResults = getCachingDAOBean()
                        .getRefSeqCodingSequenceDAO().findByRefSeqVersionAndTranscriptId(refSeqVersion,
                                transcript.getVersionId());

                if (refSeqCodingSequenceResults != null && !refSeqCodingSequenceResults.isEmpty()) {
                    String proteinId = refSeqCodingSequenceResults.get(0).getProteinId();
                    hearsayTranscript.setProteinRefSeqAccession(proteinId);

                    Set<RegionGroup> regionGroupSet = refSeqCodingSequenceResults.get(0).getLocations();
                    RegionGroup[] regionGroupArray = regionGroupSet.toArray(new RegionGroup[regionGroupSet.size()]);

                    Set<RegionGroupRegion> regionGroupRegionSet = regionGroupArray[0].getRegionGroupRegions();
                    RegionGroupRegion[] regionGroupRegionArray = regionGroupRegionSet
                            .toArray(new RegionGroupRegion[regionGroupRegionSet.size()]);
                    Integer regionStart = regionGroupRegionArray[0].getRegionStart();
                    Integer regionEnd = regionGroupRegionArray[regionGroupRegionSet.size() - 1].getRegionEnd();

                    // add utr

                    int exonIndex1 = 0;
                    TranscriptMapsExons firstExon = exons.get(exonIndex1);
                    boolean foundFirst = true;

                    while (regionStart < firstExon.getTranscrStart() || regionStart > firstExon.getTranscrEnd()) {
                        firstExon.setRegionType(RegionType.UTR5);
                        exonIndex1++;
                        if (exonIndex1 == exons.size()) {
                            foundFirst = false;
                            break;
                        }
                        firstExon = exons.get(exonIndex1);
                        if (foundFirst) {
                            if (regionStart != firstExon.getTranscrStart()) {
                                int v = regionStart - firstExon.getTranscrStart();
                                int nStrand = strand.equals("+") ? 1 : -1;
                                int gc = firstExon.getContigStart() + nStrand * v;
                                TranscriptMapsExons utr5 = new TranscriptMapsExons(RegionType.UTR5,
                                        firstExon.getTranscrStart(), regionStart - 1, firstExon.getContigStart(), gc
                                                - nStrand);
                                exons.add(exonIndex1, utr5);
                                firstExon.setContigStart(gc);
                                firstExon.setTranscrStart(regionStart);
                            }
                        }
                    }

                    int exonIndex2 = exons.size() - 1;
                    TranscriptMapsExons lastExon = exons.get(exonIndex2);
                    boolean foundLast = true;

                    while (regionEnd < lastExon.getTranscrStart() || regionEnd > lastExon.getTranscrEnd()) {
                        lastExon.setRegionType(RegionType.UTR3);
                        exonIndex2--;
                        if (exonIndex2 == exons.size()) {
                            foundLast = false;
                            break;
                        }
                        lastExon = exons.get(exonIndex2);
                        if (foundLast) {
                            int v = regionEnd - lastExon.getTranscrStart();
                            int nStrand = strand.equals("+") ? 1 : -1;
                            int gc = lastExon.getContigStart() + nStrand * v;
                            TranscriptMapsExons utr3 = new TranscriptMapsExons(RegionType.UTR3, regionEnd + 1,
                                    lastExon.getTranscrEnd(), gc + nStrand, lastExon.getContigEnd());
                            exons.add(exonIndex2 + 1, utr3);
                            lastExon.setContigStart(gc);
                            lastExon.setTranscrStart(regionEnd);
                        }
                    }

                    for (TranscriptMapsExons exon : exons) {
                        if (exon.getRegionType().equals(RegionType.EXON)) {
                            exon.setContigStart(exon.getTranscrStart() - regionStart + 1);
                            exon.setContigEnd(exon.getTranscrEnd() - regionStart + 1);
                        }
                    }

                }

                for (int i = exons.size() - 1; i > 0; --i) {
                    TranscriptMapsExons exon = exons.get(i);
                    if (exon.getTranscriptMaps().getStrand().equals("+")
                            && exons.get(i - 1).getContigEnd() != exons.get(i).getContigStart()) {
                        int g1 = exons.get(i - 1).getContigEnd() + 1;
                        int g2 = exons.get(i).getContigStart() - 1;
                        TranscriptMapsExons intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, g1, g2);
                        exons.add(i, intron);
                    } else if (exon.getTranscriptMaps().getStrand().equals("-")
                            && exons.get(i + 1).getContigStart() != exons.get(i - 1).getContigEnd()) {
                        int g1 = exons.get(i).getContigStart() + 1;
                        int g2 = exons.get(i - 1).getContigEnd() - 1;
                        TranscriptMapsExons intron = new TranscriptMapsExons(RegionType.INTRON, -1, -1, g1, g2);
                        exons.add(i, intron);
                    }
                }

                if (strand.equals("+")) {
                    for (TranscriptMapsExons exon : exons) {

                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        try {
                            getCachingDAOBean().getHearsayTranscriptIntervalDAO().save(tInterval);
                        } catch (HearsayDAOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    ReverseListIterator<TranscriptMapsExons> iter = new ReverseListIterator<TranscriptMapsExons>(exons);
                    while (iter.hasNext()) {
                        TranscriptMapsExons exon = iter.next();
                        TranscriptInterval tInterval = new TranscriptInterval();
                        tInterval.setTranscriptEnd(exon.getTranscrEnd());
                        tInterval.setTranscriptStart(exon.getTranscrStart());
                        tInterval.setCdsStart(exon.getContigStart());
                        tInterval.setCdsEnd(exon.getContigEnd());
                        tInterval.setRegionType(org.renci.hearsay.dao.model.RegionType.valueOf(exon.getRegionType()
                                .toString()));
                        try {
                            getCachingDAOBean().getHearsayTranscriptIntervalDAO().save(tInterval);
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

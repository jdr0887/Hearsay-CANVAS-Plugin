package org.renci.hearsay.canvas.dao.jpa;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneDAO;
import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneExternalIdsDAO;
import org.renci.hearsay.canvas.clinbin.dao.MaxFreqDAO;
import org.renci.hearsay.canvas.clinvar.dao.ReferenceClinicalAssertionsDAO;
import org.renci.hearsay.canvas.dao.CANVASDAOBeanService;
import org.renci.hearsay.canvas.exac.dao.MaxVariantFrequencyDAO;
import org.renci.hearsay.canvas.exac.dao.VariantFrequencyDAO;
import org.renci.hearsay.canvas.genome1k.dao.OneThousandGenomeSNPFrequencyPopulationDAO;
import org.renci.hearsay.canvas.hgnc.dao.HGNCGeneDAO;
import org.renci.hearsay.canvas.ref.dao.GenomeRefDAO;
import org.renci.hearsay.canvas.ref.dao.GenomeRefSeqDAO;
import org.renci.hearsay.canvas.refseq.dao.FeatureDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqCodingSequenceDAO;
import org.renci.hearsay.canvas.refseq.dao.RefSeqGeneDAO;
import org.renci.hearsay.canvas.refseq.dao.RegionGroupDAO;
import org.renci.hearsay.canvas.refseq.dao.RegionGroupRegionDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsDAO;
import org.renci.hearsay.canvas.refseq.dao.TranscriptMapsExonsDAO;
import org.renci.hearsay.canvas.refseq.dao.Variants_61_2_DAO;
import org.renci.hearsay.canvas.var.dao.LocationVariantDAO;

public class CANVASDAOBeanServiceImpl implements CANVASDAOBeanService {

    private AnnotationGeneDAO annotationGeneDAO;

    private AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO;

    private RefSeqCodingSequenceDAO refSeqCodingSequenceDAO;

    private RefSeqGeneDAO refSeqGeneDAO;

    private RegionGroupDAO regionGroupDAO;

    private RegionGroupRegionDAO regionGroupRegionDAO;

    private FeatureDAO featureDAO;

    private HGNCGeneDAO HGNCGeneDAO;

    private GenomeRefDAO genomeRefDAO;

    private GenomeRefSeqDAO genomeRefSeqDAO;

    private LocationVariantDAO locationVariantDAO;

    private OneThousandGenomeSNPFrequencyPopulationDAO oneThousandGenomeSNPFrequencyPopulationDAO;

    private TranscriptDAO transcriptDAO;

    private TranscriptMapsDAO transcriptMapsDAO;

    private TranscriptMapsExonsDAO transcriptMapsExonsDAO;

    private Variants_61_2_DAO variants_61_2_DAO;

    private MaxVariantFrequencyDAO maxVariantFrequencyDAO;

    private MaxFreqDAO maxFreqDAO;

    private VariantFrequencyDAO variantFrequencyDAO;

    private ReferenceClinicalAssertionsDAO referenceClinicalAssertionsDAO;

    public CANVASDAOBeanServiceImpl() {
        super();
    }

    @Override
    public LocationVariantDAO getLocationVariantDAO() {
        return locationVariantDAO;
    }

    @Override
    public void setLocationVariantDAO(LocationVariantDAO locationVariantDAO) {
        this.locationVariantDAO = locationVariantDAO;
    }

    @Override
    public RegionGroupDAO getRegionGroupDAO() {
        return regionGroupDAO;
    }

    @Override
    public void setRegionGroupDAO(RegionGroupDAO regionGroupDAO) {
        this.regionGroupDAO = regionGroupDAO;
    }

    @Override
    public RegionGroupRegionDAO getRegionGroupRegionDAO() {
        return regionGroupRegionDAO;
    }

    @Override
    public void setRegionGroupRegionDAO(RegionGroupRegionDAO regionGroupRegionDAO) {
        this.regionGroupRegionDAO = regionGroupRegionDAO;
    }

    @Override
    public GenomeRefSeqDAO getGenomeRefSeqDAO() {
        return genomeRefSeqDAO;
    }

    @Override
    public void setGenomeRefSeqDAO(GenomeRefSeqDAO genomeRefSeqDAO) {
        this.genomeRefSeqDAO = genomeRefSeqDAO;
    }

    @Override
    public MaxFreqDAO getMaxFreqDAO() {
        return maxFreqDAO;
    }

    @Override
    public void setMaxFreqDAO(MaxFreqDAO maxFreqDAO) {
        this.maxFreqDAO = maxFreqDAO;
    }

    @Override
    public ReferenceClinicalAssertionsDAO getReferenceClinicalAssertionsDAO() {
        return referenceClinicalAssertionsDAO;
    }

    @Override
    public void setReferenceClinicalAssertionsDAO(ReferenceClinicalAssertionsDAO referenceClinicalAssertionsDAO) {
        this.referenceClinicalAssertionsDAO = referenceClinicalAssertionsDAO;
    }

    @Override
    public OneThousandGenomeSNPFrequencyPopulationDAO getOneThousandGenomeSNPFrequencyPopulationDAO() {
        return oneThousandGenomeSNPFrequencyPopulationDAO;
    }

    @Override
    public void setOneThousandGenomeSNPFrequencyPopulationDAO(
            OneThousandGenomeSNPFrequencyPopulationDAO oneThousandGenomeSNPFrequencyPopulationDAO) {
        this.oneThousandGenomeSNPFrequencyPopulationDAO = oneThousandGenomeSNPFrequencyPopulationDAO;
    }

    @Override
    public FeatureDAO getFeatureDAO() {
        return featureDAO;
    }

    @Override
    public void setFeatureDAO(FeatureDAO featureDAO) {
        this.featureDAO = featureDAO;
    }

    @Override
    public AnnotationGeneDAO getAnnotationGeneDAO() {
        return annotationGeneDAO;
    }

    @Override
    public void setAnnotationGeneDAO(AnnotationGeneDAO annotationGeneDAO) {
        this.annotationGeneDAO = annotationGeneDAO;
    }

    @Override
    public AnnotationGeneExternalIdsDAO getAnnotationGeneExternalIdsDAO() {
        return annotationGeneExternalIdsDAO;
    }

    @Override
    public void setAnnotationGeneExternalIdsDAO(AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO) {
        this.annotationGeneExternalIdsDAO = annotationGeneExternalIdsDAO;
    }

    @Override
    public RefSeqCodingSequenceDAO getRefSeqCodingSequenceDAO() {
        return refSeqCodingSequenceDAO;
    }

    @Override
    public void setRefSeqCodingSequenceDAO(RefSeqCodingSequenceDAO refSeqCodingSequenceDAO) {
        this.refSeqCodingSequenceDAO = refSeqCodingSequenceDAO;
    }

    @Override
    public RefSeqGeneDAO getRefSeqGeneDAO() {
        return refSeqGeneDAO;
    }

    @Override
    public void setRefSeqGeneDAO(RefSeqGeneDAO refSeqGeneDAO) {
        this.refSeqGeneDAO = refSeqGeneDAO;
    }

    @Override
    public HGNCGeneDAO getHGNCGeneDAO() {
        return HGNCGeneDAO;
    }

    @Override
    public void setHGNCGeneDAO(HGNCGeneDAO hGNCGeneDAO) {
        HGNCGeneDAO = hGNCGeneDAO;
    }

    @Override
    public GenomeRefDAO getGenomeRefDAO() {
        return genomeRefDAO;
    }

    @Override
    public void setGenomeRefDAO(GenomeRefDAO genomeRefDAO) {
        this.genomeRefDAO = genomeRefDAO;
    }

    @Override
    public TranscriptDAO getTranscriptDAO() {
        return transcriptDAO;
    }

    @Override
    public void setTranscriptDAO(TranscriptDAO transcriptDAO) {
        this.transcriptDAO = transcriptDAO;
    }

    @Override
    public TranscriptMapsDAO getTranscriptMapsDAO() {
        return transcriptMapsDAO;
    }

    @Override
    public void setTranscriptMapsDAO(TranscriptMapsDAO transcriptMapsDAO) {
        this.transcriptMapsDAO = transcriptMapsDAO;
    }

    @Override
    public TranscriptMapsExonsDAO getTranscriptMapsExonsDAO() {
        return transcriptMapsExonsDAO;
    }

    @Override
    public void setTranscriptMapsExonsDAO(TranscriptMapsExonsDAO transcriptMapsExonsDAO) {
        this.transcriptMapsExonsDAO = transcriptMapsExonsDAO;
    }

    @Override
    public Variants_61_2_DAO getVariants_61_2_DAO() {
        return variants_61_2_DAO;
    }

    @Override
    public void setVariants_61_2_DAO(Variants_61_2_DAO variants_61_2_DAO) {
        this.variants_61_2_DAO = variants_61_2_DAO;
    }

    @Override
    public MaxVariantFrequencyDAO getMaxVariantFrequencyDAO() {
        return maxVariantFrequencyDAO;
    }

    @Override
    public void setMaxVariantFrequencyDAO(MaxVariantFrequencyDAO maxVariantFrequencyDAO) {
        this.maxVariantFrequencyDAO = maxVariantFrequencyDAO;
    }

    @Override
    public VariantFrequencyDAO getVariantFrequencyDAO() {
        return variantFrequencyDAO;
    }

    @Override
    public void setVariantFrequencyDAO(VariantFrequencyDAO variantFrequencyDAO) {
        this.variantFrequencyDAO = variantFrequencyDAO;
    }

}

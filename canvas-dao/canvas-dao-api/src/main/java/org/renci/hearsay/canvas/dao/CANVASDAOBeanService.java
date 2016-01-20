package org.renci.hearsay.canvas.dao;

import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneDAO;
import org.renci.hearsay.canvas.annotation.dao.AnnotationGeneExternalIdsDAO;
import org.renci.hearsay.canvas.clinbin.dao.MaxFreqDAO;
import org.renci.hearsay.canvas.clinvar.dao.ReferenceClinicalAssertionsDAO;
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

public interface CANVASDAOBeanService {

    public MaxFreqDAO getMaxFreqDAO();

    public void setMaxFreqDAO(MaxFreqDAO maxFreqDAO);

    public ReferenceClinicalAssertionsDAO getReferenceClinicalAssertionsDAO();

    public void setReferenceClinicalAssertionsDAO(ReferenceClinicalAssertionsDAO referenceClinicalAssertionsDAO);

    public OneThousandGenomeSNPFrequencyPopulationDAO getOneThousandGenomeSNPFrequencyPopulationDAO();

    public void setOneThousandGenomeSNPFrequencyPopulationDAO(
            OneThousandGenomeSNPFrequencyPopulationDAO oneThousandGenomeSNPFrequencyPopulationDAO);

    public FeatureDAO getFeatureDAO();

    public void setFeatureDAO(FeatureDAO featureDAO);

    public AnnotationGeneDAO getAnnotationGeneDAO();

    public void setAnnotationGeneDAO(AnnotationGeneDAO annotationGeneDAO);

    public AnnotationGeneExternalIdsDAO getAnnotationGeneExternalIdsDAO();

    public void setAnnotationGeneExternalIdsDAO(AnnotationGeneExternalIdsDAO annotationGeneExternalIdsDAO);

    public RefSeqCodingSequenceDAO getRefSeqCodingSequenceDAO();

    public void setRefSeqCodingSequenceDAO(RefSeqCodingSequenceDAO refSeqCodingSequenceDAO);

    public RefSeqGeneDAO getRefSeqGeneDAO();

    public void setRefSeqGeneDAO(RefSeqGeneDAO refSeqGeneDAO);

    public RegionGroupDAO getRegionGroupDAO();

    public void setRegionGroupDAO(RegionGroupDAO regionGroupDAO);

    public RegionGroupRegionDAO getRegionGroupRegionDAO();

    public void setRegionGroupRegionDAO(RegionGroupRegionDAO regionGroupRegionDAO);

    public HGNCGeneDAO getHGNCGeneDAO();

    public void setHGNCGeneDAO(HGNCGeneDAO hGNCGeneDAO);

    public GenomeRefDAO getGenomeRefDAO();

    public void setGenomeRefDAO(GenomeRefDAO genomeRefDAO);

    public GenomeRefSeqDAO getGenomeRefSeqDAO();

    public void setGenomeRefSeqDAO(GenomeRefSeqDAO genomeRefSeqDAO);

    public TranscriptDAO getTranscriptDAO();

    public void setTranscriptDAO(TranscriptDAO transcriptDAO);

    public TranscriptMapsDAO getTranscriptMapsDAO();

    public void setTranscriptMapsDAO(TranscriptMapsDAO transcriptMapsDAO);

    public TranscriptMapsExonsDAO getTranscriptMapsExonsDAO();

    public void setTranscriptMapsExonsDAO(TranscriptMapsExonsDAO transcriptMapsExonsDAO);

    public Variants_61_2_DAO getVariants_61_2_DAO();

    public void setVariants_61_2_DAO(Variants_61_2_DAO variants_61_2_DAO);

    public MaxVariantFrequencyDAO getMaxVariantFrequencyDAO();

    public void setMaxVariantFrequencyDAO(MaxVariantFrequencyDAO maxVariantFrequencyDAO);

    public VariantFrequencyDAO getVariantFrequencyDAO();

    public void setVariantFrequencyDAO(VariantFrequencyDAO variantFrequencyDAO);

}
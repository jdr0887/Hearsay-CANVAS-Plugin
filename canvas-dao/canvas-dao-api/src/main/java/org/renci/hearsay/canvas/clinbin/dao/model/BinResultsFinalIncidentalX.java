package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.refseq.dao.model.LocationType;
import org.renci.hearsay.canvas.refseq.dao.model.VariantEffect;
import org.renci.hearsay.canvas.var.dao.model.Assembly;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "bin_results_final_incidentalx")
public class BinResultsFinalIncidentalX implements Persistable {

    private static final long serialVersionUID = 702358141551470361L;

    @EmbeddedId
    private BinResultsFinalIncidentalXPK key;

    @MapsId
    @Column(name = "participant", length = 50)
    private String participant;

    @MapsId
    @Column(name = "mapnum")
    private Integer mapnum;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "incidental_result_version")
    private IncidentalResultVersionX incidentalResultVersion;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "incidental_bin_id")
    private IncidentalBinX incidentalBin;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "asm_id")
    private Assembly assembly;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @ManyToOne
    @JoinColumn(name = "zygosity_mode")
    private ZygosityModeType zygosityMode;

    @ManyToOne
    @JoinColumn(name = "phenotype_id")
    private PhenotypeX phenotype;

    @Column(name = "ncg_alt_f")
    private Float ncgAlternateFrequency;

    @Column(name = "ncg_hwe_p")
    private Float ncgHWEP;

    @ManyToOne
    @JoinColumn(name = "carrier_status_id")
    private CarrierStatus carrierStatus;

    @Column(name = "chromosome", length = 15)
    private String chromosome;

    @Column(name = "pos")
    private Integer pos;

    @Column(name = "type", length = 15)
    private String type;

    @Column(name = "transcr", length = 31)
    private String transcr;

    @Column(name = "refseq_gene")
    private String refseqGene;

    @Column(name = "hgnc_gene")
    private String hgncGene;

    @ManyToOne
    @JoinColumn(name = "loc_type")
    private LocationType locationType;

    @Column(name = "strand", length = 1)
    private String strand;

    @Column(name = "transcr_pos")
    private Integer transcrPos;

    @Column(name = "cds_pos")
    private Integer cdsPos;

    @Column(name = "aa_start")
    private Integer aaStart;

    @Column(name = "aa_end")
    private Integer aaEnd;

    @Column(name = "original_aa")
    private String originalAa;

    @Column(name = "final_aa")
    private String finalAa;

    @Column(name = "frameshift")
    private Boolean frameshift;

    @Column(name = "inframe")
    private Boolean inframe;

    @Column(name = "intron_exon_dist")
    private Integer intronExonDist;

    @ManyToOne
    @JoinColumn(name = "variant_effect")
    private VariantEffect variantEffect;

    @Column(name = "nummaps")
    private Integer nummaps;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "acc_num", length = 10)
    private String accNum;

    @Column(name = "tag")
    private String tag;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @Lob
    @Column(name = "refallele")
    private String referenceAllele;

    @Column(name = "altallele", length = 65535)
    private String alternateAllele;

    @Column(name = "hgvsgenomic", length = 131090)
    private String hgvsGenomic;

    @Column(name = "hgvscds", length = 131090)
    private String hgvsCodingSequence;

    @Column(name = "hgvstranscript", length = 131090)
    private String hgvsTranscript;

    @Column(name = "hgvsprotein", length = 65555)
    private String hgvsProtein;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "qd")
    private Float qd;

    @Column(name = "read_pos_rank_sum")
    private Float readPosRankSum;

    @Column(name = "frac_reads_with_dels")
    private Float fracReadsWithDels;

    @Column(name = "hrun")
    private Integer hrun;

    @Column(name = "strand_score")
    private Float strandScore;

    @Column(name = "ref_depth")
    private Integer refDepth;

    @Column(name = "alt_depth")
    private Integer altDepth;

    @Column(name = "homozygous")
    private Boolean homozygous;

    @Column(name = "genotype_qual")
    private Float genotypeQual;

    @Lob
    @Column(name = "rs_id")
    private String rsId;

    public BinResultsFinalIncidentalX() {
        super();
    }

    public BinResultsFinalIncidentalXPK getKey() {
        return key;
    }

    public void setKey(BinResultsFinalIncidentalXPK key) {
        this.key = key;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Integer getMapnum() {
        return mapnum;
    }

    public void setMapnum(Integer mapnum) {
        this.mapnum = mapnum;
    }

    public IncidentalResultVersionX getIncidentalResultVersion() {
        return incidentalResultVersion;
    }

    public void setIncidentalResultVersion(IncidentalResultVersionX incidentalResultVersion) {
        this.incidentalResultVersion = incidentalResultVersion;
    }

    public IncidentalBinX getIncidentalBin() {
        return incidentalBin;
    }

    public void setIncidentalBin(IncidentalBinX incidentalBin) {
        this.incidentalBin = incidentalBin;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    public ZygosityModeType getZygosityMode() {
        return zygosityMode;
    }

    public void setZygosityMode(ZygosityModeType zygosityMode) {
        this.zygosityMode = zygosityMode;
    }

    public PhenotypeX getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(PhenotypeX phenotype) {
        this.phenotype = phenotype;
    }

    public Float getNcgAlternateFrequency() {
        return ncgAlternateFrequency;
    }

    public void setNcgAlternateFrequency(Float ncgAlternateFrequency) {
        this.ncgAlternateFrequency = ncgAlternateFrequency;
    }

    public Float getNcgHWEP() {
        return ncgHWEP;
    }

    public void setNcgHWEP(Float ncgHWEP) {
        this.ncgHWEP = ncgHWEP;
    }

    public CarrierStatus getCarrierStatus() {
        return carrierStatus;
    }

    public void setCarrierStatus(CarrierStatus carrierStatus) {
        this.carrierStatus = carrierStatus;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    public String getRefseqGene() {
        return refseqGene;
    }

    public void setRefseqGene(String refseqGene) {
        this.refseqGene = refseqGene;
    }

    public String getHgncGene() {
        return hgncGene;
    }

    public void setHgncGene(String hgncGene) {
        this.hgncGene = hgncGene;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getStrand() {
        return strand;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public Integer getTranscrPos() {
        return transcrPos;
    }

    public void setTranscrPos(Integer transcrPos) {
        this.transcrPos = transcrPos;
    }

    public Integer getCdsPos() {
        return cdsPos;
    }

    public void setCdsPos(Integer cdsPos) {
        this.cdsPos = cdsPos;
    }

    public Integer getAaStart() {
        return aaStart;
    }

    public void setAaStart(Integer aaStart) {
        this.aaStart = aaStart;
    }

    public Integer getAaEnd() {
        return aaEnd;
    }

    public void setAaEnd(Integer aaEnd) {
        this.aaEnd = aaEnd;
    }

    public String getOriginalAa() {
        return originalAa;
    }

    public void setOriginalAa(String originalAa) {
        this.originalAa = originalAa;
    }

    public String getFinalAa() {
        return finalAa;
    }

    public void setFinalAa(String finalAa) {
        this.finalAa = finalAa;
    }

    public Boolean getFrameshift() {
        return frameshift;
    }

    public void setFrameshift(Boolean frameshift) {
        this.frameshift = frameshift;
    }

    public Boolean getInframe() {
        return inframe;
    }

    public void setInframe(Boolean inframe) {
        this.inframe = inframe;
    }

    public Integer getIntronExonDist() {
        return intronExonDist;
    }

    public void setIntronExonDist(Integer intronExonDist) {
        this.intronExonDist = intronExonDist;
    }

    public VariantEffect getVariantEffect() {
        return variantEffect;
    }

    public void setVariantEffect(VariantEffect variantEffect) {
        this.variantEffect = variantEffect;
    }

    public Integer getNummaps() {
        return nummaps;
    }

    public void setNummaps(Integer nummaps) {
        this.nummaps = nummaps;
    }

    public Integer getGeneId() {
        return geneId;
    }

    public void setGeneId(Integer geneId) {
        this.geneId = geneId;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Float getMaxAlleleFreq() {
        return maxAlleleFreq;
    }

    public void setMaxAlleleFreq(Float maxAlleleFreq) {
        this.maxAlleleFreq = maxAlleleFreq;
    }

    public String getReferenceAllele() {
        return referenceAllele;
    }

    public void setReferenceAllele(String referenceAllele) {
        this.referenceAllele = referenceAllele;
    }

    public String getAlternateAllele() {
        return alternateAllele;
    }

    public void setAlternateAllele(String alternateAllele) {
        this.alternateAllele = alternateAllele;
    }

    public String getHgvsGenomic() {
        return hgvsGenomic;
    }

    public void setHgvsGenomic(String hgvsGenomic) {
        this.hgvsGenomic = hgvsGenomic;
    }

    public String getHgvsCodingSequence() {
        return hgvsCodingSequence;
    }

    public void setHgvsCodingSequence(String hgvsCodingSequence) {
        this.hgvsCodingSequence = hgvsCodingSequence;
    }

    public String getHgvsTranscript() {
        return hgvsTranscript;
    }

    public void setHgvsTranscript(String hgvsTranscript) {
        this.hgvsTranscript = hgvsTranscript;
    }

    public String getHgvsProtein() {
        return hgvsProtein;
    }

    public void setHgvsProtein(String hgvsProtein) {
        this.hgvsProtein = hgvsProtein;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Float getQd() {
        return qd;
    }

    public void setQd(Float qd) {
        this.qd = qd;
    }

    public Float getReadPosRankSum() {
        return readPosRankSum;
    }

    public void setReadPosRankSum(Float readPosRankSum) {
        this.readPosRankSum = readPosRankSum;
    }

    public Float getFracReadsWithDels() {
        return fracReadsWithDels;
    }

    public void setFracReadsWithDels(Float fracReadsWithDels) {
        this.fracReadsWithDels = fracReadsWithDels;
    }

    public Integer getHrun() {
        return hrun;
    }

    public void setHrun(Integer hrun) {
        this.hrun = hrun;
    }

    public Float getStrandScore() {
        return strandScore;
    }

    public void setStrandScore(Float strandScore) {
        this.strandScore = strandScore;
    }

    public Integer getRefDepth() {
        return refDepth;
    }

    public void setRefDepth(Integer refDepth) {
        this.refDepth = refDepth;
    }

    public Integer getAltDepth() {
        return altDepth;
    }

    public void setAltDepth(Integer altDepth) {
        this.altDepth = altDepth;
    }

    public Boolean getHomozygous() {
        return homozygous;
    }

    public void setHomozygous(Boolean homozygous) {
        this.homozygous = homozygous;
    }

    public Float getGenotypeQual() {
        return genotypeQual;
    }

    public void setGenotypeQual(Float genotypeQual) {
        this.genotypeQual = genotypeQual;
    }

    public String getRsId() {
        return rsId;
    }

    public void setRsId(String rsId) {
        this.rsId = rsId;
    }

    @Override
    public String toString() {
        return String
                .format("BinResultsFinalIncidentalX [participant=%s, mapnum=%s, ncgAlternateFrequency=%s, ncgHWEP=%s, chromosome=%s, pos=%s, type=%s, transcr=%s, refseqGene=%s, hgncGene=%s, locationType=%s, strand=%s, transcrPos=%s, cdsPos=%s, aaStart=%s, aaEnd=%s, originalAa=%s, finalAa=%s, frameshift=%s, inframe=%s, intronExonDist=%s, nummaps=%s, geneId=%s, accNum=%s, tag=%s, maxAlleleFreq=%s, disease=%s, referenceAllele=%s, alternateAllele=%s, hgvsGenomic=%s, hgvsCodingSequence=%s, hgvsTranscript=%s, hgvsProtein=%s, depth=%s, qd=%s, readPosRankSum=%s, fracReadsWithDels=%s, hrun=%s, strandScore=%s, refDepth=%s, altDepth=%s, homozygous=%s, genotypeQual=%s, rsId=%s]",
                        participant, mapnum, ncgAlternateFrequency, ncgHWEP, chromosome, pos, type, transcr,
                        refseqGene, hgncGene, locationType, strand, transcrPos, cdsPos, aaStart, aaEnd, originalAa,
                        finalAa, frameshift, inframe, intronExonDist, variantEffect, nummaps, geneId, accNum, tag,
                        maxAlleleFreq, referenceAllele, alternateAllele, hgvsGenomic, hgvsCodingSequence,
                        hgvsTranscript, hgvsProtein, depth, qd, readPosRankSum, fracReadsWithDels, hrun, strandScore,
                        refDepth, altDepth, homozygous, genotypeQual, rsId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aaEnd == null) ? 0 : aaEnd.hashCode());
        result = prime * result + ((aaStart == null) ? 0 : aaStart.hashCode());
        result = prime * result + ((accNum == null) ? 0 : accNum.hashCode());
        result = prime * result + ((altDepth == null) ? 0 : altDepth.hashCode());
        result = prime * result + ((alternateAllele == null) ? 0 : alternateAllele.hashCode());
        result = prime * result + ((cdsPos == null) ? 0 : cdsPos.hashCode());
        result = prime * result + ((chromosome == null) ? 0 : chromosome.hashCode());
        result = prime * result + ((depth == null) ? 0 : depth.hashCode());
        result = prime * result + ((finalAa == null) ? 0 : finalAa.hashCode());
        result = prime * result + ((fracReadsWithDels == null) ? 0 : fracReadsWithDels.hashCode());
        result = prime * result + ((frameshift == null) ? 0 : frameshift.hashCode());
        result = prime * result + ((geneId == null) ? 0 : geneId.hashCode());
        result = prime * result + ((genotypeQual == null) ? 0 : genotypeQual.hashCode());
        result = prime * result + ((hgncGene == null) ? 0 : hgncGene.hashCode());
        result = prime * result + ((hgvsCodingSequence == null) ? 0 : hgvsCodingSequence.hashCode());
        result = prime * result + ((hgvsGenomic == null) ? 0 : hgvsGenomic.hashCode());
        result = prime * result + ((hgvsProtein == null) ? 0 : hgvsProtein.hashCode());
        result = prime * result + ((hgvsTranscript == null) ? 0 : hgvsTranscript.hashCode());
        result = prime * result + ((homozygous == null) ? 0 : homozygous.hashCode());
        result = prime * result + ((hrun == null) ? 0 : hrun.hashCode());
        result = prime * result + ((inframe == null) ? 0 : inframe.hashCode());
        result = prime * result + ((intronExonDist == null) ? 0 : intronExonDist.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((mapnum == null) ? 0 : mapnum.hashCode());
        result = prime * result + ((maxAlleleFreq == null) ? 0 : maxAlleleFreq.hashCode());
        result = prime * result + ((ncgAlternateFrequency == null) ? 0 : ncgAlternateFrequency.hashCode());
        result = prime * result + ((ncgHWEP == null) ? 0 : ncgHWEP.hashCode());
        result = prime * result + ((nummaps == null) ? 0 : nummaps.hashCode());
        result = prime * result + ((originalAa == null) ? 0 : originalAa.hashCode());
        result = prime * result + ((participant == null) ? 0 : participant.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((qd == null) ? 0 : qd.hashCode());
        result = prime * result + ((readPosRankSum == null) ? 0 : readPosRankSum.hashCode());
        result = prime * result + ((refDepth == null) ? 0 : refDepth.hashCode());
        result = prime * result + ((referenceAllele == null) ? 0 : referenceAllele.hashCode());
        result = prime * result + ((refseqGene == null) ? 0 : refseqGene.hashCode());
        result = prime * result + ((rsId == null) ? 0 : rsId.hashCode());
        result = prime * result + ((strand == null) ? 0 : strand.hashCode());
        result = prime * result + ((strandScore == null) ? 0 : strandScore.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((transcr == null) ? 0 : transcr.hashCode());
        result = prime * result + ((transcrPos == null) ? 0 : transcrPos.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BinResultsFinalIncidentalX other = (BinResultsFinalIncidentalX) obj;
        if (aaEnd == null) {
            if (other.aaEnd != null)
                return false;
        } else if (!aaEnd.equals(other.aaEnd))
            return false;
        if (aaStart == null) {
            if (other.aaStart != null)
                return false;
        } else if (!aaStart.equals(other.aaStart))
            return false;
        if (accNum == null) {
            if (other.accNum != null)
                return false;
        } else if (!accNum.equals(other.accNum))
            return false;
        if (altDepth == null) {
            if (other.altDepth != null)
                return false;
        } else if (!altDepth.equals(other.altDepth))
            return false;
        if (alternateAllele == null) {
            if (other.alternateAllele != null)
                return false;
        } else if (!alternateAllele.equals(other.alternateAllele))
            return false;
        if (cdsPos == null) {
            if (other.cdsPos != null)
                return false;
        } else if (!cdsPos.equals(other.cdsPos))
            return false;
        if (chromosome == null) {
            if (other.chromosome != null)
                return false;
        } else if (!chromosome.equals(other.chromosome))
            return false;
        if (depth == null) {
            if (other.depth != null)
                return false;
        } else if (!depth.equals(other.depth))
            return false;
        if (finalAa == null) {
            if (other.finalAa != null)
                return false;
        } else if (!finalAa.equals(other.finalAa))
            return false;
        if (fracReadsWithDels == null) {
            if (other.fracReadsWithDels != null)
                return false;
        } else if (!fracReadsWithDels.equals(other.fracReadsWithDels))
            return false;
        if (frameshift == null) {
            if (other.frameshift != null)
                return false;
        } else if (!frameshift.equals(other.frameshift))
            return false;
        if (geneId == null) {
            if (other.geneId != null)
                return false;
        } else if (!geneId.equals(other.geneId))
            return false;
        if (genotypeQual == null) {
            if (other.genotypeQual != null)
                return false;
        } else if (!genotypeQual.equals(other.genotypeQual))
            return false;
        if (hgncGene == null) {
            if (other.hgncGene != null)
                return false;
        } else if (!hgncGene.equals(other.hgncGene))
            return false;
        if (hgvsCodingSequence == null) {
            if (other.hgvsCodingSequence != null)
                return false;
        } else if (!hgvsCodingSequence.equals(other.hgvsCodingSequence))
            return false;
        if (hgvsGenomic == null) {
            if (other.hgvsGenomic != null)
                return false;
        } else if (!hgvsGenomic.equals(other.hgvsGenomic))
            return false;
        if (hgvsProtein == null) {
            if (other.hgvsProtein != null)
                return false;
        } else if (!hgvsProtein.equals(other.hgvsProtein))
            return false;
        if (hgvsTranscript == null) {
            if (other.hgvsTranscript != null)
                return false;
        } else if (!hgvsTranscript.equals(other.hgvsTranscript))
            return false;
        if (homozygous == null) {
            if (other.homozygous != null)
                return false;
        } else if (!homozygous.equals(other.homozygous))
            return false;
        if (hrun == null) {
            if (other.hrun != null)
                return false;
        } else if (!hrun.equals(other.hrun))
            return false;
        if (inframe == null) {
            if (other.inframe != null)
                return false;
        } else if (!inframe.equals(other.inframe))
            return false;
        if (intronExonDist == null) {
            if (other.intronExonDist != null)
                return false;
        } else if (!intronExonDist.equals(other.intronExonDist))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (mapnum == null) {
            if (other.mapnum != null)
                return false;
        } else if (!mapnum.equals(other.mapnum))
            return false;
        if (maxAlleleFreq == null) {
            if (other.maxAlleleFreq != null)
                return false;
        } else if (!maxAlleleFreq.equals(other.maxAlleleFreq))
            return false;
        if (ncgAlternateFrequency == null) {
            if (other.ncgAlternateFrequency != null)
                return false;
        } else if (!ncgAlternateFrequency.equals(other.ncgAlternateFrequency))
            return false;
        if (ncgHWEP == null) {
            if (other.ncgHWEP != null)
                return false;
        } else if (!ncgHWEP.equals(other.ncgHWEP))
            return false;
        if (nummaps == null) {
            if (other.nummaps != null)
                return false;
        } else if (!nummaps.equals(other.nummaps))
            return false;
        if (originalAa == null) {
            if (other.originalAa != null)
                return false;
        } else if (!originalAa.equals(other.originalAa))
            return false;
        if (participant == null) {
            if (other.participant != null)
                return false;
        } else if (!participant.equals(other.participant))
            return false;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (qd == null) {
            if (other.qd != null)
                return false;
        } else if (!qd.equals(other.qd))
            return false;
        if (readPosRankSum == null) {
            if (other.readPosRankSum != null)
                return false;
        } else if (!readPosRankSum.equals(other.readPosRankSum))
            return false;
        if (refDepth == null) {
            if (other.refDepth != null)
                return false;
        } else if (!refDepth.equals(other.refDepth))
            return false;
        if (referenceAllele == null) {
            if (other.referenceAllele != null)
                return false;
        } else if (!referenceAllele.equals(other.referenceAllele))
            return false;
        if (refseqGene == null) {
            if (other.refseqGene != null)
                return false;
        } else if (!refseqGene.equals(other.refseqGene))
            return false;
        if (rsId == null) {
            if (other.rsId != null)
                return false;
        } else if (!rsId.equals(other.rsId))
            return false;
        if (strand == null) {
            if (other.strand != null)
                return false;
        } else if (!strand.equals(other.strand))
            return false;
        if (strandScore == null) {
            if (other.strandScore != null)
                return false;
        } else if (!strandScore.equals(other.strandScore))
            return false;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        if (transcrPos == null) {
            if (other.transcrPos != null)
                return false;
        } else if (!transcrPos.equals(other.transcrPos))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}

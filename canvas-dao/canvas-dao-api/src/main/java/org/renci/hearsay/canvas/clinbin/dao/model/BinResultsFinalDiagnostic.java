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
@Table(schema = "clinbin", name = "bin_results_final_diagnostic")
public class BinResultsFinalDiagnostic implements Persistable {

    private static final long serialVersionUID = -32282642230894067L;

    @EmbeddedId
    private BinResultsFinalDiagnosticPK key;

    @MapsId("dx")
    @ManyToOne
    @JoinColumn(name = "dx_id")
    private DX dx;

    @MapsId("diagnosticResultVersion")
    @ManyToOne
    @JoinColumn(name = "diagnostic_result_version")
    private DiagnosticResultVersion diagnosticResultVersion;

    @MapsId("assembly")
    @ManyToOne
    @JoinColumn(name = "asm_id")
    private Assembly assembly;

    @MapsId("locationVariant")
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    @ManyToOne
    @JoinColumn(name = "loc_type")
    private LocationType locationType;

    @ManyToOne
    @JoinColumn(name = "variant_effect")
    private VariantEffect variantEffect;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private DiseaseClass diseaseClass;

    @Column(name = "chromosome", length = 15)
    private String chromosome;

    @Column(name = "pos")
    private Integer pos;

    @Column(name = "type", length = 15)
    private String type;

    @Column(name = "refseq_gene")
    private String refseqGene;

    @Column(name = "hgnc_gene")
    private String hgncGene;

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

    @Column(name = "nummaps")
    private Integer nummaps;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "acc_num", length = 10)
    private String accNum;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @Lob
    @Column(name = "refallele")
    private String refallele;

    @Column(name = "altallele", length = 65535)
    private String altallele;

    @Column(name = "hgvsgenomic", length = 131090)
    private String hgvsgenomic;

    @Column(name = "hgvscds", length = 131090)
    private String hgvscds;

    @Column(name = "hgvstranscript", length = 131090)
    private String hgvstranscript;

    @Column(name = "hgvsprotein", length = 65555)
    private String hgvsprotein;

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
    @Column(name = "tier")
    private String tier;

    @Lob
    @Column(name = "inheritance")
    private String inheritance;

    @Lob
    @Column(name = "rs_id")
    private String rsId;

    @Column(name = "exon_truncation_count")
    private Integer exonTruncationCount;

    public BinResultsFinalDiagnostic() {
        super();
    }

    public BinResultsFinalDiagnosticPK getKey() {
        return key;
    }

    public void setKey(BinResultsFinalDiagnosticPK key) {
        this.key = key;
    }

    public DX getDx() {
        return dx;
    }

    public void setDx(DX dx) {
        this.dx = dx;
    }

    public DiagnosticResultVersion getDiagnosticResultVersion() {
        return diagnosticResultVersion;
    }

    public void setDiagnosticResultVersion(DiagnosticResultVersion diagnosticResultVersion) {
        this.diagnosticResultVersion = diagnosticResultVersion;
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

    public Float getMaxAlleleFreq() {
        return maxAlleleFreq;
    }

    public void setMaxAlleleFreq(Float maxAlleleFreq) {
        this.maxAlleleFreq = maxAlleleFreq;
    }

    public DiseaseClass getDiseaseClass() {
        return diseaseClass;
    }

    public void setDiseaseClass(DiseaseClass diseaseClass) {
        this.diseaseClass = diseaseClass;
    }

    public String getRefallele() {
        return refallele;
    }

    public void setRefallele(String refallele) {
        this.refallele = refallele;
    }

    public String getAltallele() {
        return altallele;
    }

    public void setAltallele(String altallele) {
        this.altallele = altallele;
    }

    public String getHgvsgenomic() {
        return hgvsgenomic;
    }

    public void setHgvsgenomic(String hgvsgenomic) {
        this.hgvsgenomic = hgvsgenomic;
    }

    public String getHgvscds() {
        return hgvscds;
    }

    public void setHgvscds(String hgvscds) {
        this.hgvscds = hgvscds;
    }

    public String getHgvstranscript() {
        return hgvstranscript;
    }

    public void setHgvstranscript(String hgvstranscript) {
        this.hgvstranscript = hgvstranscript;
    }

    public String getHgvsprotein() {
        return hgvsprotein;
    }

    public void setHgvsprotein(String hgvsprotein) {
        this.hgvsprotein = hgvsprotein;
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

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getInheritance() {
        return inheritance;
    }

    public void setInheritance(String inheritance) {
        this.inheritance = inheritance;
    }

    public String getRsId() {
        return rsId;
    }

    public void setRsId(String rsId) {
        this.rsId = rsId;
    }

    public Integer getExonTruncationCount() {
        return exonTruncationCount;
    }

    public void setExonTruncationCount(Integer exonTruncationCount) {
        this.exonTruncationCount = exonTruncationCount;
    }

    @Override
    public String toString() {
        return String.format(
                "BinResultsFinalDiagnostic [chromosome=%s, pos=%s, type=%s, refseqGene=%s, hgncGene=%s, transcrPos=%s, cdsPos=%s, aaStart=%s, aaEnd=%s, originalAa=%s, finalAa=%s, frameshift=%s, inframe=%s, intronExonDist=%s, nummaps=%s, geneId=%s, accNum=%s, maxAlleleFreq=%s, refallele=%s, altallele=%s, hgvsgenomic=%s, hgvscds=%s, hgvstranscript=%s, hgvsprotein=%s, depth=%s, qd=%s, readPosRankSum=%s, fracReadsWithDels=%s, hrun=%s, strandScore=%s, refDepth=%s, altDepth=%s, homozygous=%s, genotypeQual=%s, tier=%s, inheritance=%s, rsId=%s, exonTruncationCount=%s]",
                chromosome, pos, type, refseqGene, hgncGene, transcrPos, cdsPos, aaStart, aaEnd, originalAa, finalAa,
                frameshift, inframe, intronExonDist, nummaps, geneId, accNum, maxAlleleFreq, refallele, altallele,
                hgvsgenomic, hgvscds, hgvstranscript, hgvsprotein, depth, qd, readPosRankSum, fracReadsWithDels, hrun,
                strandScore, refDepth, altDepth, homozygous, genotypeQual, tier, inheritance, rsId,
                exonTruncationCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aaEnd == null) ? 0 : aaEnd.hashCode());
        result = prime * result + ((aaStart == null) ? 0 : aaStart.hashCode());
        result = prime * result + ((accNum == null) ? 0 : accNum.hashCode());
        result = prime * result + ((altDepth == null) ? 0 : altDepth.hashCode());
        result = prime * result + ((altallele == null) ? 0 : altallele.hashCode());
        result = prime * result + ((cdsPos == null) ? 0 : cdsPos.hashCode());
        result = prime * result + ((chromosome == null) ? 0 : chromosome.hashCode());
        result = prime * result + ((depth == null) ? 0 : depth.hashCode());
        result = prime * result + ((exonTruncationCount == null) ? 0 : exonTruncationCount.hashCode());
        result = prime * result + ((finalAa == null) ? 0 : finalAa.hashCode());
        result = prime * result + ((fracReadsWithDels == null) ? 0 : fracReadsWithDels.hashCode());
        result = prime * result + ((frameshift == null) ? 0 : frameshift.hashCode());
        result = prime * result + ((geneId == null) ? 0 : geneId.hashCode());
        result = prime * result + ((genotypeQual == null) ? 0 : genotypeQual.hashCode());
        result = prime * result + ((hgncGene == null) ? 0 : hgncGene.hashCode());
        result = prime * result + ((hgvscds == null) ? 0 : hgvscds.hashCode());
        result = prime * result + ((hgvsgenomic == null) ? 0 : hgvsgenomic.hashCode());
        result = prime * result + ((hgvsprotein == null) ? 0 : hgvsprotein.hashCode());
        result = prime * result + ((hgvstranscript == null) ? 0 : hgvstranscript.hashCode());
        result = prime * result + ((homozygous == null) ? 0 : homozygous.hashCode());
        result = prime * result + ((hrun == null) ? 0 : hrun.hashCode());
        result = prime * result + ((inframe == null) ? 0 : inframe.hashCode());
        result = prime * result + ((inheritance == null) ? 0 : inheritance.hashCode());
        result = prime * result + ((intronExonDist == null) ? 0 : intronExonDist.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((maxAlleleFreq == null) ? 0 : maxAlleleFreq.hashCode());
        result = prime * result + ((nummaps == null) ? 0 : nummaps.hashCode());
        result = prime * result + ((originalAa == null) ? 0 : originalAa.hashCode());
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((qd == null) ? 0 : qd.hashCode());
        result = prime * result + ((readPosRankSum == null) ? 0 : readPosRankSum.hashCode());
        result = prime * result + ((refDepth == null) ? 0 : refDepth.hashCode());
        result = prime * result + ((refallele == null) ? 0 : refallele.hashCode());
        result = prime * result + ((refseqGene == null) ? 0 : refseqGene.hashCode());
        result = prime * result + ((rsId == null) ? 0 : rsId.hashCode());
        result = prime * result + ((strandScore == null) ? 0 : strandScore.hashCode());
        result = prime * result + ((tier == null) ? 0 : tier.hashCode());
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
        BinResultsFinalDiagnostic other = (BinResultsFinalDiagnostic) obj;
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
        if (altallele == null) {
            if (other.altallele != null)
                return false;
        } else if (!altallele.equals(other.altallele))
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
        if (exonTruncationCount == null) {
            if (other.exonTruncationCount != null)
                return false;
        } else if (!exonTruncationCount.equals(other.exonTruncationCount))
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
        if (hgvscds == null) {
            if (other.hgvscds != null)
                return false;
        } else if (!hgvscds.equals(other.hgvscds))
            return false;
        if (hgvsgenomic == null) {
            if (other.hgvsgenomic != null)
                return false;
        } else if (!hgvsgenomic.equals(other.hgvsgenomic))
            return false;
        if (hgvsprotein == null) {
            if (other.hgvsprotein != null)
                return false;
        } else if (!hgvsprotein.equals(other.hgvsprotein))
            return false;
        if (hgvstranscript == null) {
            if (other.hgvstranscript != null)
                return false;
        } else if (!hgvstranscript.equals(other.hgvstranscript))
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
        if (inheritance == null) {
            if (other.inheritance != null)
                return false;
        } else if (!inheritance.equals(other.inheritance))
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
        if (maxAlleleFreq == null) {
            if (other.maxAlleleFreq != null)
                return false;
        } else if (!maxAlleleFreq.equals(other.maxAlleleFreq))
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
        if (refallele == null) {
            if (other.refallele != null)
                return false;
        } else if (!refallele.equals(other.refallele))
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
        if (strandScore == null) {
            if (other.strandScore != null)
                return false;
        } else if (!strandScore.equals(other.strandScore))
            return false;
        if (tier == null) {
            if (other.tier != null)
                return false;
        } else if (!tier.equals(other.tier))
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

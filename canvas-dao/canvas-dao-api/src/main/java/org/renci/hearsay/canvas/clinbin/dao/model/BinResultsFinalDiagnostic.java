package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinResultsFinalDiagnostic")
@XmlRootElement(name = "binResultsFinalDiagnostic")
@Entity
@Table(name = "bin_results_final_diagnostic")
public class BinResultsFinalDiagnostic {

    @Column(name = "participant", length = 50)
    private String participant;

    @Column(name = "dx_id")
    private Integer dxId;

    @Column(name = "diagnostic_list_version")
    private Integer diagnosticListVersion;

    @Column(name = "asm_id")
    private Integer asmId;

    @Column(name = "loc_var_id")
    private Integer locVarId;

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

    @Lob
    @Column(name = "loc_type")
    private String locType;

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

    @Lob
    @Column(name = "variant_effect")
    private String variantEffect;

    @Column(name = "mapnum")
    private Integer mapnum;

    @Column(name = "nummaps")
    private Integer nummaps;

    @Column(name = "gene_id")
    private Integer geneId;

    @Column(name = "acc_num", length = 10)
    private String accNum;

    @Column(name = "max_allele_freq")
    private Float maxAlleleFreq;

    @Column(name = "class_id")
    private Integer classId;

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

    @Column(name = "rs_id")
    private Integer rsId;

    @Column(name = "exon_truncation_count")
    private Integer exonTruncationCount;

    public BinResultsFinalDiagnostic() {
        super();
    }

}

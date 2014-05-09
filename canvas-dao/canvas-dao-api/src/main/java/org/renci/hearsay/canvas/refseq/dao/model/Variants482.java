package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Variants482")
@Entity
@Table(schema = "refseq", name = "variants_48_2")
public class Variants482 {

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Lob
    @Column(name = "chromosome")
    private String chromosome;

    @Column(name = "pos")
    private Integer pos;

    @Lob
    @Column(name = "type")
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

    @Column(name = "noncan_exon")
    private Integer noncanExon;

    @Column(name = "feature_id")
    private Integer featureId;

    public Variants482() {
        super();
    }

}

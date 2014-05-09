package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DxCoverage")
@XmlRootElement(name = "dxCoverage")
@Entity
@Table(name = "dx_coverage")
public class DxCoverage {

    @Column(name = "dx_exon_id")
    private Integer dxExonId;

    @Column(name = "participant", length = 50)
    private String participant;

    @Column(name = "frac_gt_1")
    private Float fracGt1;

    @Column(name = "frac_gt_5")
    private Float fracGt5;

    @Column(name = "frac_gt_10")
    private Float fracGt10;

    @Column(name = "frac_gt_15")
    private Float fracGt15;

    @Column(name = "frac_gt_30")
    private Float fracGt30;

    @Column(name = "frac_gt_50")
    private Float fracGt50;

    public DxCoverage() {
        super();
    }

}

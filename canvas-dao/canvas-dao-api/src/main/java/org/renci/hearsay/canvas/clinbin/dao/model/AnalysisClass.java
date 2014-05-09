package org.renci.hearsay.canvas.clinbin.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnalysisClass")
@XmlRootElement(name = "analysisClass")
@Entity
@Table(name = "analysis_class")
public class AnalysisClass {

    @Column(name = "analysis_class_id")
    private Long analysisClassId;

    @Lob
    @Column(name = "selected_class")
    private String selectedClass;

    @Lob
    @Column(name = "select_class_descr")
    private String selectClassDescr;

    @Column(name = "loc_var_id")
    private Integer locVarId;

    @Lob
    @Column(name = "user_name")
    private String userName;

    @Column(name = "timestamp")
    private Date timestamp;

    @Lob
    @Column(name = "hgnc_gene")
    private String hgncGene;

    @Column(name = "dx_id")
    private Integer dxId;

    public AnalysisClass() {
        super();
    }

}

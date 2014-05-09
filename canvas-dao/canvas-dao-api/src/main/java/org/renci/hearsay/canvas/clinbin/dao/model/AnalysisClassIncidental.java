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
@XmlType(name = "AnalysisClassIncidental")
@XmlRootElement(name = "analysisClassIncidental")
@Entity
@Table(name = "analysis_class_incidental")
public class AnalysisClassIncidental {

    @Column(name = "analysis_class_incidental_id")
    private Long analysisClassIncidentalId;

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

    @Lob
    @Column(name = "disease")
    private String disease;

    @Lob
    @Column(name = "bin")
    private String bin;

    public AnalysisClassIncidental() {
        super();
    }

}

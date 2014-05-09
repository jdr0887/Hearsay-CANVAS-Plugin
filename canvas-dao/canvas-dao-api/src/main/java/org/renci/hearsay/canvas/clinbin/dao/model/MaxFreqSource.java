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
@XmlType(name = "MaxFreqSource")
@XmlRootElement(name = "maxFreqSource")
@Entity
@Table(name = "max_freq_source")
public class MaxFreqSource {

    @Lob
    @Column(name = "source")
    private String source;

    public MaxFreqSource() {
        super();
    }

}

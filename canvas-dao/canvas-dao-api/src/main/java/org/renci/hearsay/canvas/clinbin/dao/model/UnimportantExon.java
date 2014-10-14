package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "clinbin", name = "unimportant_exon")
public class UnimportantExon implements Persistable {

    private static final long serialVersionUID = 6602270522663182823L;

    @EmbeddedId
    private UnimportantExonPK key;

    @MapsId
    @Lob
    @Column(name = "transcr")
    private String transcr;

    @MapsId
    @Column(name = "noncan_exon")
    private Integer noncanExon;

    @Column(name = "count")
    private Integer count;

    public UnimportantExon() {
        super();
    }

    public UnimportantExonPK getKey() {
        return key;
    }

    public void setKey(UnimportantExonPK key) {
        this.key = key;
    }

    public String getTranscr() {
        return transcr;
    }

    public void setTranscr(String transcr) {
        this.transcr = transcr;
    }

    public Integer getNoncanExon() {
        return noncanExon;
    }

    public void setNoncanExon(Integer noncanExon) {
        this.noncanExon = noncanExon;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("UnimportantExon [transcr=%s, noncanExon=%s, count=%s]", transcr, noncanExon, count);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((count == null) ? 0 : count.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((noncanExon == null) ? 0 : noncanExon.hashCode());
        result = prime * result + ((transcr == null) ? 0 : transcr.hashCode());
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
        UnimportantExon other = (UnimportantExon) obj;
        if (count == null) {
            if (other.count != null)
                return false;
        } else if (!count.equals(other.count))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (noncanExon == null) {
            if (other.noncanExon != null)
                return false;
        } else if (!noncanExon.equals(other.noncanExon))
            return false;
        if (transcr == null) {
            if (other.transcr != null)
                return false;
        } else if (!transcr.equals(other.transcr))
            return false;
        return true;
    }

}

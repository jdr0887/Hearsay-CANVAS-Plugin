package org.renci.hearsay.canvas.var.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "var", name = "library")
public class Library implements Persistable {

    private static final long serialVersionUID = 3324902011981084756L;

    @Id
    @Column(name = "library_id")
    private Integer id;

    @Column(name = "htsf_library_name")
    private String htsfLibraryName;

    @ManyToOne
    @JoinColumn(name = "sample_id")
    private Sample sample;

    public Library() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHtsfLibraryName() {
        return htsfLibraryName;
    }

    public void setHtsfLibraryName(String htsfLibraryName) {
        this.htsfLibraryName = htsfLibraryName;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    @Override
    public String toString() {
        return "Library [id=" + id + ", htsfLibraryName=" + htsfLibraryName + ", sample=" + sample + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((htsfLibraryName == null) ? 0 : htsfLibraryName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((sample == null) ? 0 : sample.hashCode());
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
        Library other = (Library) obj;
        if (htsfLibraryName == null) {
            if (other.htsfLibraryName != null)
                return false;
        } else if (!htsfLibraryName.equals(other.htsfLibraryName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (sample == null) {
            if (other.sample != null)
                return false;
        } else if (!sample.equals(other.sample))
            return false;
        return true;
    }

}

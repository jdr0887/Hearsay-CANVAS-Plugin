package org.renci.hearsay.canvas.clinbin.dao.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;
import org.renci.hearsay.canvas.var.dao.model.LocationVariant;

@Entity
@Table(schema = "clinbin", name = "rs_variants")
public class RsVariants implements Persistable {

    private static final long serialVersionUID = 4338530913155714017L;

    @EmbeddedId
    private RsVariantsPK key;

    @MapsId
    @Column(name = "bin_id", length = 15)
    private String id;

    @MapsId
    @Column(name = "version")
    private Integer version;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "loc_var_id")
    private LocationVariant locationVariant;

    public RsVariants() {
        super();
    }

    public RsVariantsPK getKey() {
        return key;
    }

    public void setKey(RsVariantsPK key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public LocationVariant getLocationVariant() {
        return locationVariant;
    }

    public void setLocationVariant(LocationVariant locationVariant) {
        this.locationVariant = locationVariant;
    }

    @Override
    public String toString() {
        return String.format("RsVariants [id=%s, version=%s]", id, version);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        RsVariants other = (RsVariants) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

}

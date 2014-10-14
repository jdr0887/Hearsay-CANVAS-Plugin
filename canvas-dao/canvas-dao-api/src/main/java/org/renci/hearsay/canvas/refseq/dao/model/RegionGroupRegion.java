package org.renci.hearsay.canvas.refseq.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.renci.hearsay.canvas.dao.Persistable;

@Entity
@Table(schema = "refseq", name = "region_group_regions")
public class RegionGroupRegion implements Persistable {

    private static final long serialVersionUID = 7705809636894949101L;

    @Id
    @ManyToOne
    @JoinColumn(name = "region_group_id")
    private RegionGroup regionGroup;

    @Column(name = "region_start")
    private Integer regionStart;

    @Column(name = "region_end")
    private Integer regionEnd;

    public RegionGroupRegion() {
        super();
    }

    public RegionGroup getRegionGroup() {
        return regionGroup;
    }

    public void setRegionGroup(RegionGroup regionGroup) {
        this.regionGroup = regionGroup;
    }

    public Integer getRegionStart() {
        return regionStart;
    }

    public void setRegionStart(Integer regionStart) {
        this.regionStart = regionStart;
    }

    public Integer getRegionEnd() {
        return regionEnd;
    }

    public void setRegionEnd(Integer regionEnd) {
        this.regionEnd = regionEnd;
    }

    @Override
    public String toString() {
        return String.format("RegionGroupRegion [regionStart=%s, regionEnd=%s]", regionStart, regionEnd);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((regionEnd == null) ? 0 : regionEnd.hashCode());
        result = prime * result + ((regionStart == null) ? 0 : regionStart.hashCode());
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
        RegionGroupRegion other = (RegionGroupRegion) obj;
        if (regionEnd == null) {
            if (other.regionEnd != null)
                return false;
        } else if (!regionEnd.equals(other.regionEnd))
            return false;
        if (regionStart == null) {
            if (other.regionStart != null)
                return false;
        } else if (!regionStart.equals(other.regionStart))
            return false;
        return true;
    }

}

package org.renci.hearsay.canvas.dao.model;

import java.io.Serializable;

public class MappingKey implements Serializable {

    private static final long serialVersionUID = 6636500745946908585L;

    private String versionId;

    private Integer mapCount;

    public MappingKey() {
        super();
    }

    public MappingKey(String versionId, Integer mapCount) {
        super();
        this.versionId = versionId;
        this.mapCount = mapCount;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public Integer getMapCount() {
        return mapCount;
    }

    public void setMapCount(Integer mapCount) {
        this.mapCount = mapCount;
    }

    @Override
    public String toString() {
        return String.format("Mapping [versionId=%s, mapCount=%s]", versionId, mapCount);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mapCount == null) ? 0 : mapCount.hashCode());
        result = prime * result + ((versionId == null) ? 0 : versionId.hashCode());
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
        MappingKey other = (MappingKey) obj;
        if (mapCount == null) {
            if (other.mapCount != null)
                return false;
        } else if (!mapCount.equals(other.mapCount))
            return false;
        if (versionId == null) {
            if (other.versionId != null)
                return false;
        } else if (!versionId.equals(other.versionId))
            return false;
        return true;
    }

}

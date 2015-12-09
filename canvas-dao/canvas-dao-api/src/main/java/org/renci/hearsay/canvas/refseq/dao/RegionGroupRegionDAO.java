package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.RegionGroupRegion;
import org.renci.hearsay.dao.HearsayDAOException;

public interface RegionGroupRegionDAO extends BaseDAO<RegionGroupRegion, Integer> {

    public List<RegionGroupRegion> findByRegionGroupId(Integer regionGroupId) throws HearsayDAOException;

}

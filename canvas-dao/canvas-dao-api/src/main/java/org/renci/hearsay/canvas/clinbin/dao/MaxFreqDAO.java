package org.renci.hearsay.canvas.clinbin.dao;

import java.util.List;

import org.renci.hearsay.canvas.clinbin.dao.model.MaxFreq;
import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.dao.HearsayDAOException;

public interface MaxFreqDAO extends BaseDAO<MaxFreq, Long> {

    public List<MaxFreq> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold) throws HearsayDAOException;

}

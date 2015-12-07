package org.renci.hearsay.canvas.refseq.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;
import org.renci.hearsay.dao.HearsayDAOException;

public interface Variants_61_2_DAO extends BaseDAO<Variants_61_2, Long> {

    public List<Variants_61_2> findByLocationVariantId(Long id) throws HearsayDAOException;

    public List<Variants_61_2> findByGeneName(String name) throws HearsayDAOException;

    public List<Variants_61_2> findByTranscriptAccession(String accession) throws HearsayDAOException;

    public List<Variants_61_2> findByGeneNameAndMaxAlleleFrequency(String name, Double threshold) throws HearsayDAOException;

}

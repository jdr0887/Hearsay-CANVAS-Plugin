package org.renci.hearsay.canvas.hgnc.dao;

import java.util.List;

import org.renci.hearsay.canvas.dao.BaseDAO;
import org.renci.hearsay.canvas.hgnc.dao.model.HGNCGene;
import org.renci.hearsay.dao.HearsayDAOException;

public interface HGNCGeneDAO extends BaseDAO<HGNCGene, Integer> {

    /**
     * 
     * @param namespace
     * @return
     * @throws HearsayDAOException
     */
    public List<HGNCGene> findByAnnotationGeneExternalIdsNamespace(String namespace) throws HearsayDAOException;

    /**
     * 
     * @param name
     * @return
     * @throws HearsayDAOException
     */
    public List<HGNCGene> findByName(String name) throws HearsayDAOException;

    /**
     * 
     * @param symbol
     * @return
     * @throws HearsayDAOException
     */
    public List<HGNCGene> findBySymbol(String symbol) throws HearsayDAOException;

}

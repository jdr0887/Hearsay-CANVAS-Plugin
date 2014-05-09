package org.renci.hearsay.canvas.var.dao.jpa;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.ProjectDAO;
import org.renci.hearsay.canvas.var.dao.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectDAOImpl extends BaseDAOImpl<Project, Long> implements ProjectDAO {

    private final Logger logger = LoggerFactory.getLogger(ProjectDAOImpl.class);

    public ProjectDAOImpl() {
        super();
    }

    @Override
    public Class<Project> getPersistentClass() {
        return Project.class;
    }

}

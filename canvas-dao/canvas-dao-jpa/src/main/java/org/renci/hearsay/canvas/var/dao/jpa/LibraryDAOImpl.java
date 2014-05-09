package org.renci.hearsay.canvas.var.dao.jpa;

import org.renci.hearsay.canvas.dao.jpa.BaseDAOImpl;
import org.renci.hearsay.canvas.var.dao.LibraryDAO;
import org.renci.hearsay.canvas.var.dao.model.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryDAOImpl extends BaseDAOImpl<Library, Long> implements LibraryDAO {

    private final Logger logger = LoggerFactory.getLogger(LibraryDAOImpl.class);

    public LibraryDAOImpl() {
        super();
    }

    @Override
    public Class<Library> getPersistentClass() {
        return Library.class;
    }

}

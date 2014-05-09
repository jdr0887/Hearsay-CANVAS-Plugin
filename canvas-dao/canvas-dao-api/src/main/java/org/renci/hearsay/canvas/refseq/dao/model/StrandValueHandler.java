package org.renci.hearsay.canvas.refseq.dao.model;

import java.sql.SQLException;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueHandler;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.JavaTypes;

public class StrandValueHandler implements ValueHandler {

    private static final long serialVersionUID = -2286816904905642649L;

    public StrandValueHandler() {
        super();
    }

    @Override
    public Object getResultArgument(ValueMapping vm) {
        return null;
    }

    @Override
    public boolean isVersionable(ValueMapping vm) {
        return false;
    }

    @Override
    public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
        DBDictionary dict = vm.getMappingRepository().getDBDictionary();
        DBIdentifier colName = DBIdentifier.newColumn(name, dict != null ? dict.delimitAll() : false);
        return map(vm, colName, io, adapt);
    }

    public Column[] map(ValueMapping vm, DBIdentifier name, ColumnIO io, boolean adapt) {
        Column col = new Column();
        col.setIdentifier(name);
        col.setJavaType(JavaTypes.STRING);
        col.setSize(-1);
        col.setTypeIdentifier(DBIdentifier.newColumnDefinition(vm.getMappingRepository().getDBDictionary().xmlTypeName));
        col.setXML(true);
        return new Column[] { col };
    }

    @Override
    public boolean objectValueRequiresLoad(ValueMapping vm) {
        return false;
    }

    @Override
    public Object toDataStoreValue(ValueMapping vm, Object value, JDBCStore store) {
        if (value == null)
            return null;

        return null;
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object arg1, OpenJPAStateManager arg2, JDBCStore arg3,
            JDBCFetchConfiguration arg4) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object toObjectValue(ValueMapping vm, Object arg1) {
        // TODO Auto-generated method stub
        return null;
    }

}

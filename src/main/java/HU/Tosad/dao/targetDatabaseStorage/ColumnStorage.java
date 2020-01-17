package HU.Tosad.dao.targetDatabaseStorage;

import HU.Tosad.table.Column;

import java.util.List;

public interface ColumnStorage {
    public List<Column> getAll();
    public Column getByName(String name);
}

package HU.Tosad.dao.targetDatabaseStorage;

import HU.Tosad.table.Table;

import java.util.List;

public interface TableStorage {
    public List<Table> getAll();
    public Table getByName(String name);
}

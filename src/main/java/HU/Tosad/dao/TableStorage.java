package HU.Tosad.dao;

import HU.Tosad.model.Table;
import java.util.List;

public interface TableStorage {

    public List<Table> getAll();
    public Table getByName(String name);
}

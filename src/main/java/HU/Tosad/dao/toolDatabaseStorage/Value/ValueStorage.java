package HU.Tosad.dao.toolDatabaseStorage.Value;

import HU.Tosad.businessRule.Value;

import java.sql.SQLException;
import java.util.List;

public interface ValueStorage {
    public Value Save(Value v) throws SQLException;;
    public boolean Delete(int valueId) throws SQLException;;
    public Value Update(Value v, int valueId) throws SQLException;;
    public List<Value> getAll() throws SQLException;;
}

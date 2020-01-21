package HU.Tosad.dao.toolDatabaseStorage.Value;

import HU.Tosad.businessRule.Value;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ValueStorage {
    public Value Save(Value v) throws SQLException;;
    public boolean addBusinessRule(Map<String, String> v, int Rulesid) throws SQLException;;
    public boolean Delete(int valueId) throws SQLException;;
    public Value Update(Value v, int valueId) throws SQLException;;
    public List<Value> getAll() throws SQLException;;
}
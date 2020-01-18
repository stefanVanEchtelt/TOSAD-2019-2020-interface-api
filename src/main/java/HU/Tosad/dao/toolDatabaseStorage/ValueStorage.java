package HU.Tosad.dao;

import HU.Tosad.model.Rule;
import HU.Tosad.model.Value;

import java.sql.SQLException;
import java.util.List;

public interface ValueStorage {
    public Value Save(Value v) throws SQLException;;
    public boolean Delete(Value v) throws SQLException;;
    public Value Update(Value v) throws SQLException;;
    public List<Value> getAll() throws SQLException;;
}

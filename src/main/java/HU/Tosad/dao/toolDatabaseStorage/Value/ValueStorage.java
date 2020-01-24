package HU.Tosad.dao.toolDatabaseStorage.Value;

import HU.Tosad.businessRule.Value;
import org.json.JSONException;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ValueStorage {
    public Value Save(Value v) throws SQLException;;
    public boolean addBusinessRule(MultiValueMap<String, String> v, List<Integer> Rulesids) throws SQLException, JSONException;;
    public boolean Delete(int valueId) throws SQLException;;
    public Value Update(Value v, int valueId) throws SQLException;;
    public List<Value> getAll() throws SQLException;;
}

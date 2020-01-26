package HU.Tosad.dao.toolDatabaseStorage.Value;

import org.json.JSONException;
import org.springframework.util.MultiValueMap;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ValueStorage {
    public boolean addBusinessRule(MultiValueMap<String, String> v, List<Integer> Rulesids) throws SQLException, JSONException;
    public Map<String, String> getBusinessRuleById(int businessRuleId);

}

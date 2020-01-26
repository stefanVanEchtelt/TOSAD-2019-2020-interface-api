package HU.Tosad.dao.toolDatabaseStorage.Rule;

import org.json.JSONException;
import org.springframework.util.MultiValueMap;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RuleStorage {
    public List<Integer> addBusinessRule(MultiValueMap<String, String> v, int businessRuleId) throws SQLException, JSONException;
    public Map<String, String> getBusinessRuleById(int businessRuleId);

}


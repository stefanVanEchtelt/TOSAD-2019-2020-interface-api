package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import org.json.JSONException;
import org.springframework.util.MultiValueMap;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EventTriggerTypeStorage {
    public List<Integer> addBusinessRule(MultiValueMap<String, String>  body) throws SQLException, JSONException;
    public Map<String, String> getBusinessRuleById(int businessRuleId);

}

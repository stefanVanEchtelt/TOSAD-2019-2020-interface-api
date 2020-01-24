package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import HU.Tosad.businessRule.EventTriggerType;
import org.json.JSONException;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EventTriggerTypeStorage {
    public EventTriggerType Save(EventTriggerType ett);
    public List<Integer> addBusinessRule(MultiValueMap<String, String>  body) throws SQLException, JSONException;;
    public boolean Delete(int eventTriggerTypeId);
    public EventTriggerType Update(EventTriggerType ett, int eventTriggerTypeId);
    public List<EventTriggerType> getAll();
}

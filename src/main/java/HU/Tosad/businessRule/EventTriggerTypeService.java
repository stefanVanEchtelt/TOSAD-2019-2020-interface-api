package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.EventTriggerType.EventTriggerTypeStorage;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class EventTriggerTypeService {
    private static EventTriggerTypeStorage eventTriggerTypeStorage = null;

    @Autowired
    public EventTriggerTypeService(@Qualifier("OracleEventTriggerTypeStorage") EventTriggerTypeStorage eventTriggerTypeStorage) { this.eventTriggerTypeStorage = eventTriggerTypeStorage;}

    public static Map<String, String> getBusinessRuleById(int businessRuleId){
        return eventTriggerTypeStorage.getBusinessRuleById(businessRuleId);
    }

    public static List<Integer> addBusinessRule(MultiValueMap<String, String> body) throws SQLException, JSONException {
        return eventTriggerTypeStorage.addBusinessRule(body);
    }
}
package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.EventTriggerType.EventTriggerTypeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventTriggerTypeService {
    private final EventTriggerTypeStorage eventTriggerTypeStorage;

    @Autowired
    public EventTriggerTypeService(@Qualifier("OracleEventTriggerTypeStorage") EventTriggerTypeStorage eventTriggerTypeStorage) { this.eventTriggerTypeStorage = eventTriggerTypeStorage;}

    public List<EventTriggerType> getAll() throws SQLException {
        return eventTriggerTypeStorage.getAll();
    }

    public EventTriggerType Save(EventTriggerType eventTriggerType) throws SQLException{
        return eventTriggerTypeStorage.Save(eventTriggerType);
    }

    public EventTriggerType Update(EventTriggerType eventTriggerType, int eventTriggerTypeId) throws SQLException {
        return eventTriggerTypeStorage.Update(eventTriggerType, eventTriggerTypeId);
    }

    public boolean Delete(int eventTriggerTypeId) throws SQLException {
        return eventTriggerTypeStorage.Delete(eventTriggerTypeId);
    }

}
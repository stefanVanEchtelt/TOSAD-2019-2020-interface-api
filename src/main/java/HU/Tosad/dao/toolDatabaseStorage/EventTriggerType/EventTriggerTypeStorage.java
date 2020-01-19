package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import HU.Tosad.businessRule.EventTriggerType;

import java.util.List;

public interface EventTriggerTypeStorage {
    public EventTriggerType Save(EventTriggerType ett);
    public boolean Delete(int eventTriggerTypeId);
    public EventTriggerType Update(EventTriggerType ett, int eventTriggerTypeId);
    public List<EventTriggerType> getAll();
}

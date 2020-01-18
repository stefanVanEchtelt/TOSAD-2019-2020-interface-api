package HU.Tosad.dao;

import HU.Tosad.model.EventTriggerType;

import java.util.List;

public interface EventTriggerTypeStorage {
    public void Save(EventTriggerTypeStorage ett);
    public void Delete(EventTriggerType ett);
    public void Update(EventTriggerType ett);
    public List<EventTriggerType> getAll();
}

package HU.Tosad.dao;

import HU.Tosad.model.BusinessRuleEventTrigger;

import java.util.List;

public interface BusinessRuleEventTriggerStorage {
    public void Save(BusinessRuleEventTrigger bret);
    public void Delete(BusinessRuleEventTrigger bret);
    public void Update(BusinessRuleEventTrigger bret);
    public List<BusinessRuleEventTrigger> getAll();
}

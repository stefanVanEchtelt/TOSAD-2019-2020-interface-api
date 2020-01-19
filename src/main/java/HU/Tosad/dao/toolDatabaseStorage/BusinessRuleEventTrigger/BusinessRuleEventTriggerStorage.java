package HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger;

import HU.Tosad.businessRule.BusinessRuleEventTrigger;

import java.util.List;

public interface BusinessRuleEventTriggerStorage {
    public BusinessRuleEventTrigger Save(BusinessRuleEventTrigger bret);
    public boolean Delete(int BusinessRuleEventTriggerId);
    public List<BusinessRuleEventTrigger> getAll();
}

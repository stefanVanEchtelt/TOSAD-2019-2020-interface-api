package HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger;

import HU.Tosad.businessRule.BusinessRuleEventTrigger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BusinessRuleEventTriggerStorage {
    public BusinessRuleEventTrigger Save(BusinessRuleEventTrigger bret);
    public boolean addBusinessRule(int businessRuleId, List<Integer> ettId) throws SQLException;;
    public boolean Delete(int BusinessRuleEventTriggerId);
    public List<BusinessRuleEventTrigger> getAll();
}

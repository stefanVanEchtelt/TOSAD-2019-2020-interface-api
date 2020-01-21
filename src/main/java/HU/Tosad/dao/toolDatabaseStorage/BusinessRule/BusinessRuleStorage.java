package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.businessRule.BusinessRule;

import java.util.List;
import java.util.Map;

public interface BusinessRuleStorage {

    public int Save(Map<String, String> br);
    public boolean Delete(int businessRuleId);
    public BusinessRule Update(BusinessRule br, int businessRuleId);
    public List<BusinessRule> getAll();
    public BusinessRule getBusinessRulesByName(String name);
    public List<BusinessRule> getBusinessRulesByColumn(String name);
    public List<BusinessRule> getBusinessRulesByTable(String name);

}


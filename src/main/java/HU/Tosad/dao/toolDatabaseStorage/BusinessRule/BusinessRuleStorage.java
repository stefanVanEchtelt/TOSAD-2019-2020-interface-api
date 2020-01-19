package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.businessRule.BusinessRule;

import java.util.List;

public interface BusinessRuleStorage {

    public BusinessRule Save(BusinessRule br);
    public boolean Delete(int businessRuleId);
    public BusinessRule Update(BusinessRule br, int businessRuleId);
    public List<BusinessRule> getAll();
    public BusinessRule getBusinessRuleByName(String name);
}


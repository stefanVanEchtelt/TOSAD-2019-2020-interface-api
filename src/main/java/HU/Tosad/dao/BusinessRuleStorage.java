package HU.Tosad.dao;

import HU.Tosad.model.BusinessRule;

import java.util.List;

public interface BusinessRuleStorage {

    public void Save(BusinessRule br);
    public void Delete(BusinessRule br);
    public void Update(BusinessRule br);
    public List<BusinessRule> getAll();
    public BusinessRule getBusinessRuleByName(String name);
}


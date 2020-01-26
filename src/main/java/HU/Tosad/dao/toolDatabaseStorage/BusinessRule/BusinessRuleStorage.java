package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.businessRule.BusinessRule;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BusinessRuleStorage {

    public int Save(MultiValueMap<String, String> br);
    public boolean Delete(int businessRuleId);
    public Map<String, String> getBusinessRulesById(int id) throws SQLException;
    public BusinessRule getBusinessRulesByName(String name);
    public List<BusinessRule> getBusinessRulesByColumn(String name);
    public List<BusinessRule> getBusinessRulesByTable(String name);
}


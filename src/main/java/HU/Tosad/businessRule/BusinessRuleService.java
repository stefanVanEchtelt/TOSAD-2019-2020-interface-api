package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.BusinessRule.BusinessRuleStorage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BusinessRuleService {
    private final BusinessRuleStorage businessRuleStorage;

    @Autowired
    public BusinessRuleService(@Qualifier("OracleBusinessRuleStorage") BusinessRuleStorage businessRuleStorage) { this.businessRuleStorage = businessRuleStorage;}

    public int Save(MultiValueMap<String, String> businessRule) throws SQLException{
        return businessRuleStorage.Save(businessRule);
    }

    public Map<String, String> getBusinessRulesById(int BusinessRuleId) throws SQLException {
        return businessRuleStorage.getBusinessRulesById(BusinessRuleId);
    }

    public boolean Delete(int businessRuleId) throws SQLException {
        return businessRuleStorage.Delete(businessRuleId);
    }

    public List<BusinessRule> getBusinessRulesByColumn(String columnName) {
        return businessRuleStorage.getBusinessRulesByColumn(columnName);
    }

    public List<BusinessRule> getBusinessRulesByTable(String tableName) {
        return businessRuleStorage.getBusinessRulesByTable(tableName);
    }

    public BusinessRule getBusinessRulesByName(String BusinessRule) {
        return businessRuleStorage.getBusinessRulesByName(BusinessRule);
    }
}

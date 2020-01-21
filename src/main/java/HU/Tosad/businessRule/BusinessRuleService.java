package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.BusinessRule.BusinessRuleStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BusinessRuleService {
    private final BusinessRuleStorage businessRuleStorage;

    @Autowired
    public BusinessRuleService(@Qualifier("OracleBusinessRuleStorage") BusinessRuleStorage businessRuleStorage) { this.businessRuleStorage = businessRuleStorage;}

    public List<BusinessRule> getAll() throws SQLException {
        return businessRuleStorage.getAll();
    }

    public int Save(Map<String, String> businessRule) throws SQLException{
        return businessRuleStorage.Save(businessRule);
    }

    public BusinessRule Update(BusinessRule businessRule, int businessRuleId) throws SQLException {
        return businessRuleStorage.Update(businessRule, businessRuleId);
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

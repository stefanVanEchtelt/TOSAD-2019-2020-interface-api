package HU.Tosad.dao.toolDatabaseStorage.Failure;

import HU.Tosad.businessRule.Failure;
import org.springframework.util.MultiValueMap;
import java.sql.SQLException;
import java.util.Map;

public interface FailureStorage {
    public boolean addBusinessRule(MultiValueMap<String, String> f, int businessRuleId) throws SQLException;
    public Map<String, String> getFailureById(int businessRuleId);
}


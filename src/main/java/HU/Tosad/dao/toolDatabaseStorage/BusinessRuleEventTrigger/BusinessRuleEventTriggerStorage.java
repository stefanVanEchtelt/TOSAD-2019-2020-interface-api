package HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger;

import java.sql.SQLException;
import java.util.List;

public interface BusinessRuleEventTriggerStorage {
    public boolean addBusinessRule(int businessRuleId, List<Integer> ettId) throws SQLException;;
}

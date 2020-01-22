package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger.BusinessRuleEventTriggerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BusinessRuleEventTriggerService {
    private static BusinessRuleEventTriggerStorage businessRuleEventTriggerStorage = null;

    @Autowired
    public BusinessRuleEventTriggerService(@Qualifier("OracleBusinessRuleEventTriggerStorage") BusinessRuleEventTriggerStorage businessRuleEventTriggerStorage) { this.businessRuleEventTriggerStorage = businessRuleEventTriggerStorage;}

    public List<BusinessRuleEventTrigger> getAll() throws SQLException {
        return businessRuleEventTriggerStorage.getAll();
    }

    public static boolean addBusinessRule(int businessRuleId, List<Integer> ettIds) throws SQLException{
        return businessRuleEventTriggerStorage.addBusinessRule(businessRuleId, ettIds);
    }

    public BusinessRuleEventTrigger Save(BusinessRuleEventTrigger bret) throws SQLException{
        return businessRuleEventTriggerStorage.Save(bret);
    }

//    public BusinessRuleEventTrigger Update(BusinessRuleEventTrigger bret, int businessRuleEventTriggerId) throws SQLException {
//        return businessRuleEventTriggerStorage.Update(bret, businessRuleEventTriggerId);
//    }

    public boolean Delete(int businessRuleEventTriggerId) throws SQLException {
        return businessRuleEventTriggerStorage.Delete(businessRuleEventTriggerId);
    }
}

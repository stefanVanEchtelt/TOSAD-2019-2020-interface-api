package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger.BusinessRuleEventTriggerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BusinessRuleEventTriggerService {
    private final BusinessRuleEventTriggerStorage businessRuleEventTriggerStorage;

    @Autowired
    public BusinessRuleEventTriggerService(@Qualifier("OracleBusinessRuleEventTriggerStorage") BusinessRuleEventTriggerStorage businessRuleEventTriggerStorage) { this.businessRuleEventTriggerStorage = businessRuleEventTriggerStorage;}

    public List<BusinessRuleEventTrigger> getAll() throws SQLException {
        return businessRuleEventTriggerStorage.getAll();
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

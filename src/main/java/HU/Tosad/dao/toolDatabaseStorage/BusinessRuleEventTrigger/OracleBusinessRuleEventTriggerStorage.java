package HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger;

import HU.Tosad.businessRule.BusinessRuleEventTrigger;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("OracleBusinessRuleEventTriggerStorage")
public class OracleBusinessRuleEventTriggerStorage implements BusinessRuleEventTriggerStorage {

    @Override
    public BusinessRuleEventTrigger Save(BusinessRuleEventTrigger businessRuleEventTrigger){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO BUSINESS_RULE_TRIGGER_EVENTS (BUSINESS_RULES_ID, EVENT_TRIGGER_TYPE_ID) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, businessRuleEventTrigger.getBusinessRuleId());
            pstmt.setInt(2, businessRuleEventTrigger.getEventTriggerTypeId());
            pstmt.executeQuery();

            pstmt.close();
            return(businessRuleEventTrigger);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return businessRuleEventTrigger;
    }


    @Override
    public boolean Delete(int businessRuleEventTriggerId){
    //in OracleBusinessRule, EventTriggerType of automatisch in db?
        return false;
    }


    @Override
    public List<BusinessRuleEventTrigger> getAll() {
        List<BusinessRuleEventTrigger> BusinessRuleEventTriggers = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from BUSINESS_RULE_TRIGGER_EVENTS";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();
            System.out.println(con);

            while (dbResultSet.next()) {
                int businessRulesId = dbResultSet.getInt("business_rules_id");
                int eventTriggerTypeId = dbResultSet.getInt("event_trigger_type_id");
                BusinessRuleEventTriggers.add(new BusinessRuleEventTrigger(businessRulesId, eventTriggerTypeId));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return BusinessRuleEventTriggers;
    }

}

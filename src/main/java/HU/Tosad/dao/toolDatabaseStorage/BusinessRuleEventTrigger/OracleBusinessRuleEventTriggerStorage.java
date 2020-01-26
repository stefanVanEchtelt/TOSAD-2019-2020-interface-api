package HU.Tosad.dao.toolDatabaseStorage.BusinessRuleEventTrigger;

import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository("OracleBusinessRuleEventTriggerStorage")
public class OracleBusinessRuleEventTriggerStorage implements BusinessRuleEventTriggerStorage {

    @Override
    public boolean addBusinessRule(int businessRuleId, List<Integer> ettId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            for(int ettid : ettId) {
                //Database adds RuleID
                String query = "INSERT INTO BUSINESS_RULE_TRIGGER_EVENTS (BUSINESS_RULES_ID, EVENT_TRIGGER_TYPE_ID) VALUES (?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);

                pstmt.setInt(1, businessRuleId);
                pstmt.setInt(2, ettid);
                pstmt.executeQuery();

                pstmt.close();
            }
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return false;
    }
}

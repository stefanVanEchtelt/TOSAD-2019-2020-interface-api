package HU.Tosad.dao.toolDatabaseStorage.Rule;

import HU.Tosad.businessRule.Rule;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("OracleRuleStorage")
public class OracleRuleStorage implements RuleStorage {

    @Override
    public Rule Save(Rule rule){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, rule.getType());
            pstmt.setInt(2, rule.getSortOrder());
            pstmt.setInt(3, rule.getBusinessRuleId());
            pstmt.executeQuery();

            pstmt.close();
            return(rule);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return rule;
    }


    @Override
    public boolean Delete(int ruleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM RULES WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, ruleId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return false;

    }

    @Override
    public Rule Update(Rule rule, int ruleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "UPDATE RULES SET TYPE_EID = ?, SORT_ORDER = ?, BUSINESS_RULES_ID = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, rule.getType());
            pstmt.setInt(2, rule.getSortOrder());
            pstmt.setInt(3, rule.getBusinessRuleId());
            pstmt.setInt(4, ruleId);

            pstmt.executeQuery();
            pstmt.close();
            return rule;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return rule;
    }

    @Override
    public List<Rule> getAll() {
        List<Rule> Rules = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from RULES";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String typeEID = dbResultSet.getString("type_eid");
                int sortOrder = dbResultSet.getInt("sort_order");
                int businessRulesId = dbResultSet.getInt("business_rules_id");
                Rules.add(new Rule(id, typeEID, sortOrder, businessRulesId));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return Rules;
    }
}

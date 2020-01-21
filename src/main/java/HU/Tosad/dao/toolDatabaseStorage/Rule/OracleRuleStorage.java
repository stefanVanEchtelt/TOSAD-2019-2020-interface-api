package HU.Tosad.dao.toolDatabaseStorage.Rule;

import HU.Tosad.businessRule.Rule;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public int addBusinessRule(Map<String, String> body, int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //make body easy to read
            JSONObject json = new JSONObject(body);

            //Database adds RuleID
            String query = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, json.getString("value1"));
            pstmt.setString(2, json.getString("column1"));
            pstmt.setInt(3, businessRuleId);
            pstmt.executeQuery();

            pstmt.close();

            //get RULEID
            String getIdRules = ("SELECT RULES_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdRules);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            int Rulesid = dbResultSet.getInt("id");
            pstmtGetId.close();

            return Rulesid;
        } catch (SQLException | JSONException sqle) { sqle.printStackTrace(); }
        return 0;
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

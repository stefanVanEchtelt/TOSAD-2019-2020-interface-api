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
    public List<Integer> addBusinessRule(Map<String, String> body, int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //make body easy to read
            JSONObject json = new JSONObject(body);

            int[] TypeEid = getTypeEid(json);

            //Database adds RuleID
            String query = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, TypeEid[0]);
            pstmt.setInt(2, 1);
            pstmt.setInt(3, businessRuleId);
            pstmt.executeQuery();

            pstmt.close();

            //make list of ruleIds for fk in values
            String getIdRules = ("SELECT RULES_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdRules);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            List<Integer> rulesIds = new ArrayList<Integer>();
            rulesIds.add(dbResultSet.getInt("id"));
            pstmtGetId.close();

            if(TypeEid[1] != 0){
                //Database adds RuleID
                String query2 = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
                PreparedStatement pstmt2 = con.prepareStatement(query2);

                pstmt2.setInt(1, TypeEid[1]);
                pstmt2.setInt(2, 2);
                pstmt2.setInt(3, businessRuleId);
                pstmt2.executeQuery();

                pstmt.close();

                //make list of ruleIds for fk in values
                String getIdRules2 = ("SELECT RULES_ID_SEQ.currval FROM dual");
                PreparedStatement pstmtGetId2 = con.prepareStatement(getIdRules2);
                ResultSet dbResultSet2 = pstmtGetId2.executeQuery();
                rulesIds.add(dbResultSet2.getInt("id"));
                pstmtGetId2.close();
            }

            return rulesIds;
        } catch (SQLException | JSONException sqle) { sqle.printStackTrace(); }
        return null;
    }

    private int[] getTypeEid(JSONObject json) throws JSONException {
        String operator = json.getString("operator");
        int TypeEid = 0;
        int TypeEid2 = 0;


        if(operator == ("=")) {
            TypeEid = 3;
        }

        else if(operator == ("!=")) {
            TypeEid = 2;
            TypeEid2 = 3;
        }

        else if(operator == (">")) {
            TypeEid = 6;
        }

        else if(operator == ("<")) {
            TypeEid = 4;
        }

        else if(operator == (">=")) {
            TypeEid = 6;
            TypeEid2 = 3;
        }

        else if(operator == ("<=")) {
            TypeEid = 4;
            TypeEid2 = 3;
        }

        else if(operator == ("LIKE")) {
            TypeEid = 5;
        }

        else if(operator == ("BETWEEN")) {
            TypeEid = 7;
        }

        else if(operator == ("NOT BETWEEN")) {
            TypeEid = 8;
        }

        return new int[] {TypeEid, TypeEid2};
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

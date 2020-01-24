package HU.Tosad.dao.toolDatabaseStorage.Rule;

import HU.Tosad.businessRule.Rule;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

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
    public List<Integer> addBusinessRule(MultiValueMap<String, String> body, int businessRuleId) throws JSONException {

        //make body easy to read
        JSONObject json = new JSONObject(body);
        int[] TypesEid = getTypeEid(json);

        List<Integer> rulesIds = new ArrayList<Integer>();
        int sortOrder = 1;
        for(int typeEid : TypesEid){
            System.out.println(typeEid);

            if(typeEid !=0) {
                rulesIds.add(addBusinessRuleSub(typeEid, sortOrder, businessRuleId));
                sortOrder++;
            }
        }

        return rulesIds;
    }

    private int addBusinessRuleSub(int typeEid, int sortOrder, int  businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //Database adds RuleID
            String query = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, typeEid);
            pstmt.setInt(2, sortOrder);
            pstmt.setInt(3, businessRuleId);
            pstmt.executeQuery();

            pstmt.close();

            //get ruleIds for fk in values
            String getIdRules = ("SELECT RULES_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdRules);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            dbResultSet.next();
            int ruleId = dbResultSet.getInt("currval");
            pstmtGetId.close();

            return ruleId;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return 0;
    }


    private String removeBrackString(String Stringnm){
        return Stringnm.replaceAll("(\"|\\[|\\]|\")","");
    }

    private int[] getTypeEid(JSONObject json) throws JSONException {
        String relational_operator = removeBrackString(json.getString("relational_operator"));
        String comparison_operator = removeBrackString(json.getString("comparison_operator"));
        System.out.println(relational_operator);
        System.out.println(comparison_operator);
        int TypeEid = 0;
        int TypeEid2 = 0;
        int TypeEid3 = 0;


        if(relational_operator.equals("=")) {
            TypeEid = 3;
        }

        else if(relational_operator.equals("!=")) {
            TypeEid = 2;
            TypeEid2 = 3;
        }

        else if(relational_operator.equals(">")) {
            TypeEid = 6;
        }

        else if(relational_operator.equals("<")) {
            TypeEid = 4;
        }

        else if(relational_operator.equals(">=")) {
            TypeEid = 6;
            TypeEid2 = 1;
            TypeEid3 = 3;

        }

        else if(relational_operator.equals("<=")) {
            TypeEid = 4;
            TypeEid2 = 1;
            TypeEid3 = 3;
        }

        else if(relational_operator.equals("LIKE")) {
            TypeEid = 9;
        }

        else if(comparison_operator.equals("BETWEEN")) {
            TypeEid = 8;
        }

        else if(comparison_operator.equals("NOT BETWEEN")) {
            TypeEid = 2;
            TypeEid2 = 8;
        }

        return new int[] {TypeEid, TypeEid2, TypeEid3};
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

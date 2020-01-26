package HU.Tosad.dao.toolDatabaseStorage.Rule;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("OracleRuleStorage")
public class OracleRuleStorage implements RuleStorage {

    @Override
    public List<Integer> addBusinessRule(MultiValueMap<String, String> body, int businessRuleId) throws JSONException {

        JSONObject json = new JSONObject(body);
        int[] TypesEid = getTypeEid(json);
        String eventType = removeBrackString(json.getString("rule"));
        List<Integer> rulesIds = new ArrayList<Integer>();
        //keeping track of sortorder + creating Rule
        int sortOrder = 1;
        for(int typeEid : TypesEid){
            if(typeEid !=0) {
                rulesIds.add(addBusinessRuleSub(typeEid, sortOrder, businessRuleId, eventType));
                sortOrder++;
            }
        }
        return rulesIds;
    }

    private int addBusinessRuleSub(int typeEid, int sortOrder, int  businessRuleId, String eventType){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //Database adds RuleID
            String query = "INSERT INTO RULES (TYPE_EID, SORT_ORDER, BUSINESS_RULES_ID, EVENT_TYPE) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, typeEid);
            pstmt.setInt(2, sortOrder);
            pstmt.setInt(3, businessRuleId);
            pstmt.setString(4, eventType);

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

//clean incomming data
    private String removeBrackString(String Stringnm){
        return Stringnm.replaceAll("(\"|\\[|\\]|\")","");
    }

    private int[] getTypeEid(JSONObject json) throws JSONException {
        String relational_operator = removeBrackString(json.getString("relational_operator"));
        String comparison_operator = removeBrackString(json.getString("comparison_operator"));
        int TypeEid = 0;
        int TypeEid2 = 0;
        int TypeEid3 = 0;

        if(!relational_operator.equals("")){
            switch (relational_operator) {
                case "=":
                    TypeEid = 3;
                    break;
                case "!=":
                    TypeEid = 2;
                    TypeEid2 = 3;
                    break;
                case ">":
                    TypeEid = 6;
                    break;
                case "<":
                    TypeEid = 4;
                    break;
                case ">=":
                    TypeEid = 6;
                    TypeEid2 = 1;
                    TypeEid3 = 3;
                    break;
                case "<=":
                    TypeEid = 4;
                    TypeEid2 = 1;
                    TypeEid3 = 3;
                    break;
            }
        }else {
            switch (comparison_operator) {
                case "LIKE":
                    TypeEid = 9;
                    break;
                case "BETWEEN":
                    TypeEid = 8;
                    break;
                case "NOT BETWEEN":
                    TypeEid = 2;
                    TypeEid2 = 8;
                    break;
                case "IN":
                    TypeEid = 5;
                    break;
                case "NOT IN":
                    TypeEid = 2;
                    TypeEid2 = 5;
                    break;
            }
        }
        return new int[] {TypeEid, TypeEid2, TypeEid3};
    }

    @Override
    public Map<String, String> getBusinessRuleById(int businessRuleId) {
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            Map<String, String> BusinessRuleInf = new HashMap<>();

            String query = "SELECT * from RULES R where BUSINESS_RULES_ID = " + businessRuleId + " order by r.sort_order";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetRL = pstmt.executeQuery();
            StringBuilder operator = new StringBuilder();
            while (dbResultSetRL.next()) {
                String nmr = dbResultSetRL.getString("TYPE_EID");
                if (nmr.equals("2")) {
                    operator.append("!");
                }
                if (nmr.equals("4")) {
                    operator.append("<");
                }
                if (nmr.equals("6")) {
                    operator.append(">");
                }
                if (nmr.equals("3")) {
                    operator.append("=");
                }
                if (nmr.equals("9")) {
                    operator.append("LIKE");
                }
                if (nmr.equals("8")) {
                    operator.append("BETWEEN");
                }
                if (nmr.equals("5")) {
                    operator.append("IN");
                }
            }
            BusinessRuleInf.put("operator", operator.toString());
            return BusinessRuleInf;
        } catch (SQLException sqle) {
            sqle.printStackTrace();

        }
        return null;
    }
}

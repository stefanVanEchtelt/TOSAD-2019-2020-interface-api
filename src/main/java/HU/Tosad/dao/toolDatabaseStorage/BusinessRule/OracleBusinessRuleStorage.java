package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository("OracleBusinessRuleStorage")
public class OracleBusinessRuleStorage implements BusinessRuleStorage {

    @Override
    public int Save(MultiValueMap<String, String> businessRule) {
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            JSONObject json = new JSONObject(businessRule);

            String query = "INSERT INTO BUSINESS_RULES (NAME, ON_COLUMN, ON_TABLE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, removeBrackString(json.getString("rule_name")));
            pstmt.setString(2, removeBrackString(json.getString("current_column")));
            pstmt.setString(3, removeBrackString(json.getString("current_table")));
            pstmt.executeQuery();

            pstmt.close();

            //get BUSINESS_RULE_ID
            String getIdRules = ("SELECT BUSINESS_RULE_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdRules);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            dbResultSet.next();
            int businessRuleid = dbResultSet.getInt("currval");
            pstmtGetId.close();

            return businessRuleid;
        } catch (SQLException | JSONException sqle) {
            sqle.printStackTrace();
        }
        return 0;
    }

    //cleaning up dirty data
    private String removeBrackString(String Stringnm) {
        return Stringnm.replaceAll("(\"|\\[|\\]|\")", "");
    }

    @Override
    public boolean Delete(int businessRuleId) {
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM BUSINESS_RULES WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, businessRuleId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;

    }

    @Override
    public Map<String, String> getBusinessRulesById(int businessRuleId) throws SQLException {
        Map<String, String> BusinessRuleInf = new HashMap<>();

        //get businessRule information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "SELECT * from BUSINESS_RULES where id = " + businessRuleId;
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetBR = pstmt.executeQuery();
            while (dbResultSetBR.next()) {
                BusinessRuleInf.put("id", dbResultSetBR.getString("id"));
                BusinessRuleInf.put("rule_name", dbResultSetBR.getString("name"));
                BusinessRuleInf.put("current_column", dbResultSetBR.getString("on_column"));
                BusinessRuleInf.put("current_table", dbResultSetBR.getString("on_table"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return BusinessRuleInf;
    }
}


package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.businessRule.BusinessRule;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import HU.Tosad.dao.toolDatabaseStorage.Value.OracleValueStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("OracleBusinessRuleStorage")
public class OracleBusinessRuleStorage implements BusinessRuleStorage {

    @Override
    public int Save(Map<String, String> businessRule){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            JSONObject json = new JSONObject(businessRule);

            String query = "INSERT INTO BUSINESS_RULES (NAME, ON_COLUMN, ON_TABLE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, json.getString("rule_name"));
            pstmt.setString(2, json.getString("column1"));
            pstmt.setString(3, json.getString("table1"));
            pstmt.executeQuery();

            pstmt.close();

            //get BUSINESS_RULE_ID
            String getIdRules = ("SELECT BUSINESS_RULE_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdRules);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            int businessRuleid = dbResultSet.getInt("id");
            pstmtGetId.close();

            return businessRuleid;
        } catch (SQLException | JSONException sqle) { sqle.printStackTrace(); }
        return 0;
    }


    @Override
    public boolean Delete(int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM BUSINESS_RULES WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, businessRuleId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return false;

    }

    @Override
    public BusinessRule Update(BusinessRule businessRule, int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "UPDATE BUSINESS_RULES SET NAME = ?, ON_COLUMN = ?, ON_TABLE = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, businessRule.getName());
            pstmt.setString(2, businessRule.getColumn());
            pstmt.setString(3, businessRule.getTable());
            pstmt.setInt(4, businessRule.getId());

            pstmt.executeQuery();
            pstmt.close();
            return businessRule;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return businessRule;
    }

    @Override
    public List<BusinessRule> getAll() {
        List<BusinessRule> BusinessRules = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from BUSINESS_RULES";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();
            System.out.println(con);

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String name = dbResultSet.getString("name");
                String onColumn = dbResultSet.getString("on_column");
                String onTable = dbResultSet.getString("on_table");
                BusinessRules.add(new BusinessRule(id, name, onColumn, onTable));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return BusinessRules;
    }

    @Override
    public List<BusinessRule> getBusinessRulesByColumn(String name) {
        return getBusinessRulesBy("ON_COLUMN", name);
    }

    @Override
    public List<BusinessRule> getBusinessRulesByTable(String name) {
        System.out.println(name);
        return getBusinessRulesBy("ON_TABLE", name);
    }

    @Override
    public BusinessRule getBusinessRulesByName(String name) {
        List<BusinessRule> businessRules = getBusinessRulesBy("NAME", name);
        BusinessRule businessRule = businessRules.get(0);
        System.out.println(businessRule.getName());
        return businessRule;
    }

    public List<BusinessRule> getBusinessRulesBy(String onWhat, String onWhatName) {
        List<BusinessRule> BusinessRules = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from BUSINESS_RULES where " + onWhat + " = " + "'" + onWhatName +"'";
            PreparedStatement pstmt = con.prepareStatement(query);
            System.out.println(query);

            ResultSet dbResultSet = pstmt.executeQuery();
            System.out.println(con);

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String name = dbResultSet.getString("name");
                String onColumn = dbResultSet.getString("on_column");
                String onTable = dbResultSet.getString("on_table");
                BusinessRules.add(new BusinessRule(id, name, onColumn, onTable));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return BusinessRules;
    }
}

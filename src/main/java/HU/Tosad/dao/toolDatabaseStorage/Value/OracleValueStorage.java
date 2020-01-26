package HU.Tosad.dao.toolDatabaseStorage.Value;

import HU.Tosad.businessRule.Value;
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

@Repository("OracleValueStorage")
public class OracleValueStorage implements ValueStorage {

    @Override
    public Value Save(Value value){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO \"VALUES\" (\"VALUE\", IS_COLUMN, SORT_ORDER, RULE_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, value.getValue());
            pstmt.setInt(2, value.getIsColumn());
            pstmt.setInt(3, value.getSortOrder());
            pstmt.setInt(4, value.getRuleId());
            pstmt.executeQuery();

            pstmt.close();
            return(value);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return value;
    }

    @Override
    public boolean addBusinessRule(MultiValueMap<String, String> body, List<Integer> Rulesids) throws JSONException {
        for(int ruleid : Rulesids) {
            if (ruleid != 0) {
                addBusinessRuleSub(body, ruleid);
            }
        }
        return true;
    }

    private boolean addBusinessRuleSub(MultiValueMap<String, String> body, int Ruleid) throws JSONException {

        JSONObject json = new JSONObject(body);

        //every ruleType has it's own way of Storing
        String ruleType = removeBrackString(json.getString("rule"));

        //storing value Attribute compare Rule
        if (ruleType.equals("ACMP")) {
            StoreBusinessRule(removeBrackString(json.getString("value1")), 0, 1, Ruleid);
        }

        //storing value List Compare Rule
        if (ruleType.equals("ALIS")) {
            String[] values = json.getString("value1").split(", ");
            System.out.println(values);
            int sortOrder = 1;
            for(String value : values) {
                StoreBusinessRule(value, 0, sortOrder, Ruleid);
                sortOrder++;
            }
        }

        //storing value Attribute Range Rule
        else if (ruleType.equals("ARNG")) {
            StoreBusinessRule(removeBrackString(json.getString("value1")), 0, 1, Ruleid);
            StoreBusinessRule(removeBrackString(json.getString("value2")), 0, 2, Ruleid);
        }

        //storing value Tuple Compare Rule
        else if (ruleType.equals("TCMP")) {
            StoreBusinessRule(removeBrackString(json.getString("current_table"))+"."+ removeBrackString(json.getString("column1")), 1, 1, Ruleid);
            StoreBusinessRule(removeBrackString(json.getString("current_table"))+"."+ removeBrackString(json.getString("column2")), 1, 2, Ruleid);
        }

        // inter-entity Tuple compare Rule
        else if (ruleType.equals("ICMP")) {
            StoreBusinessRule(removeBrackString(json.getString("table"))+"."+ removeBrackString(json.getString("column2")), 1, 2, Ruleid);
        }
        return true;
    }

    private String removeBrackString(String Stringnm){
        return Stringnm.replaceAll("(\"|\\[|\\]|\")","");
    }

    private boolean StoreBusinessRule(String value, int isColumn, int sort_order, int ruleID) {
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            String query = "INSERT INTO \"VALUES\" (\"VALUE\", IS_COLUMN, SORT_ORDER, RULE_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, value);
            pstmt.setInt(2, isColumn);
            pstmt.setInt(3, sort_order);
            pstmt.setInt(4, ruleID);
            pstmt.executeQuery();

            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean Delete(int valueId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM \"VALUES\" WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, valueId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
            return false;

    }

    @Override
    public Value Update(Value value, int valueId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "UPDATE \"VALUES\" SET VALUE = ?, IS_COLUMN = ?, SORT_ORDER = ?, RULE_ID = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, value.getValue());
            pstmt.setInt(2, value.getIsColumn());
            pstmt.setInt(3, value.getSortOrder());
            pstmt.setInt(4, value.getRuleId());
            pstmt.setInt(5, valueId);

            pstmt.executeQuery();
            pstmt.close();
            return value;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return value;
    }

    @Override
    public List<Value> getAll() {
        List<Value> Values = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from \"VALUES\"";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();


            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String value = dbResultSet.getString("value");
                int isColumn = dbResultSet.getInt("is_column");
                int sortOrder = dbResultSet.getInt("sort_order");
                int ruleId = dbResultSet.getInt("rule_id");
                Values.add(new Value(id, value, isColumn, sortOrder, ruleId));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return Values;
    }
}

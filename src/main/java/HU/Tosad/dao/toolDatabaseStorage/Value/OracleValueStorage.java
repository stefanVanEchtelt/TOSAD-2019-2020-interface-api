package HU.Tosad.dao.toolDatabaseStorage.Value;

import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("OracleValueStorage")
public class OracleValueStorage implements ValueStorage {

    @Override
    public boolean addBusinessRule(MultiValueMap<String, String> body, List<Integer> Rulesids) throws JSONException {
        //make values for every ruleid
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

        switch (ruleType) {
            //storing value Attribute compare Rule
            case "ACMP":
                StoreBusinessRule(removeBrackString(json.getString("value1")), 0, 1, Ruleid);
                break;

            //storing value List Compare Rule
            case "ALIS":
                String[] values = json.getString("form_list").split(", ");
                int sortOrder = 1;
                for (String value : values) {
                    StoreBusinessRule(value, 0, sortOrder, Ruleid);
                    sortOrder++;
                }
                break;

            //storing value Attribute Range Rule
            case "ARNG":
                StoreBusinessRule(removeBrackString(json.getString("value1")), 0, 1, Ruleid);
                StoreBusinessRule(removeBrackString(json.getString("value2")), 0, 2, Ruleid);
                break;

            //storing value Tuple Compare Rule
            case "TCMP":
                StoreBusinessRule(removeBrackString(json.getString("current_table")) + "." + removeBrackString(json.getString("column1")), 1, 1, Ruleid);
                StoreBusinessRule(removeBrackString(json.getString("current_table")) + "." + removeBrackString(json.getString("column2")), 1, 2, Ruleid);
                break;

            // inter-entity Tuple compare Rule
            case "ICMP":
                StoreBusinessRule(removeBrackString(json.getString("table")) + "." + removeBrackString(json.getString("column2")), 1, 2, Ruleid);
                break;
        }
        return true;
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
    public Map<String, String> getBusinessRuleById(int businessRuleId) {
        Map<String, String> BusinessRuleInf = new HashMap<>();
        //get Values information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "SELECT * from \"VALUES\" V JOIN RULES R on R.ID = V.RULE_ID where BUSINESS_RULES_ID = " + businessRuleId + " order by R.SORT_ORDER";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetVLS = pstmt.executeQuery();
            String eventType = "";
            while (dbResultSetVLS.next()) {
                eventType = dbResultSetVLS.getString("EVENT_TYPE");
            }
            BusinessRuleInf.put("rule", eventType);

            String form_list = "";
            String value1 = "";
            String value2 = "";
            String column1 = "";
            String column2 = "";
            String table = "";
            String current_table = "";
            String current_column = "";


            switch (eventType) {
                //getting value Attribute compare Rule
                case "ACMP":
                    while (dbResultSetVLS.next()) {
                        value1 = dbResultSetVLS.getString("VALUE");
                    }
                    break;

                //getting value List Compare Rule
                case "ALIS":
                    String values = "";
                    while (dbResultSetVLS.next()) {
                        String value = dbResultSetVLS.getString("VALUE") + ", ";
                        values += value;
                    }
                    form_list = values.substring(0, values.length() - 2);
                    break;

                //storing value Attribute Range Rule
                case "ARNG": {
                    int counter = 0;
                    while (dbResultSetVLS.next()) {
                        if (counter == 0) {
                            value1 = dbResultSetVLS.getString("VALUE");
                            counter++;
                        } else {
                            value2 = dbResultSetVLS.getString("VALUE");
                        }
                    }
                    break;
                }

                //getting value Tuple Compare Rule
                case "TCMP": {
                    int counter = 0;
                    while (dbResultSetVLS.next()) {
                        String value = dbResultSetVLS.getString("VALUE");
                        String[] values1 = value.split(".");
                        if (counter == 0) {
                            column1 = values1[0];
                            counter++;
                        } else {
                            column2 = values1[1];
                        }
                    }
                    break;
                }

                //getting inter-entity Tuple compare Rule
                case "ICMP":
                    while (dbResultSetVLS.next()) {
                        String value = dbResultSetVLS.getString("VALUE");
                        String[] values2 = value.split(".");
                        table = values2[0];
                        column2 = values2[1];
                    }
                    break;
            }

            //save the values
            BusinessRuleInf.put("form_list", form_list);
            BusinessRuleInf.put("value1", value1);
            BusinessRuleInf.put("value2", value2);
            BusinessRuleInf.put("column1", column1);
            BusinessRuleInf.put("column2", column2);
            BusinessRuleInf.put("table", table);
            BusinessRuleInf.put("current_table", current_table);
            BusinessRuleInf.put("current_column", current_column);

            return BusinessRuleInf;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    //for cleaning up incomming data
    private String removeBrackString(String Stringnm){
        return Stringnm.replaceAll("(\"|\\[|\\]|\")","");
    }
}

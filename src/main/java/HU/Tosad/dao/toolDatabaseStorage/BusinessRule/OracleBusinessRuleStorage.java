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


        //get failure information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "SELECT * from FAILURES where BUSINESS_RULES_ID = " + businessRuleId;
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetFLR = pstmt.executeQuery();
            while (dbResultSetFLR.next()) {
                BusinessRuleInf.put("message", dbResultSetFLR.getString("MESSAGE"));
                BusinessRuleInf.put("code", dbResultSetFLR.getString("CODE"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        //get EventTriggerType information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "SELECT * from EVENT_TRIGGER_TYPES EVT JOIN BUSINESS_RULE_TRIGGER_EVENTS BRTE on " +
                    "EVT.ID = BRTE.EVENT_TRIGGER_TYPE_ID JOIN BUSINESS_RULES BR on BRTE.BUSINESS_RULES_ID = BR.ID " +
                    "where BUSINESS_RULES_ID = " + businessRuleId;
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetEVT = pstmt.executeQuery();
            while (dbResultSetEVT.next()) {
                String evt = dbResultSetEVT.getString("NAME");
                if (evt.equals("INSERT")) {
                    BusinessRuleInf.put("event_types_INSERT", "INSERT");
                } else if (evt.equals("UPDATE")) {
                    BusinessRuleInf.put("event_types_UPDATE", "UPDATE");
                } else if (evt.equals("DELETE")) {
                    BusinessRuleInf.put("event_types_DELETE", "DELETE");
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        //get Rule information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
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
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        //get Values information
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "SELECT * from \"VALUES\" V JOIN RULES R on R.ID = V.RULE_ID where BUSINESS_RULES_ID = " + businessRuleId + " order by R.SORT_ORDER";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetVLS = pstmt.executeQuery();
            String eventType = "";
            while (dbResultSetVLS.next()) {
                eventType = dbResultSetVLS.getString("EVENT_TYPE");
            }
            BusinessRuleInf.put("eventType", eventType);

            //getting value Attribute compare Rule
            if (eventType.equals("ACMP")) {
                while (dbResultSetVLS.next()) {
                    BusinessRuleInf.put("value1", dbResultSetVLS.getString("VALUE"));
                }
            }

            //getting value List Compare Rule
            else if (eventType.equals("ALIS")) {
                String values = "";
                while (dbResultSetVLS.next()) {
                    String value = dbResultSetVLS.getString("VALUE") + ", ";
                    values += value;
                }
                BusinessRuleInf.put("value1", values.substring(0, values.length() - 2));
            }

            //storing value Attribute Range Rule
            else if (eventType.equals("ARNG")) {
                int counter = 0;
                while (dbResultSetVLS.next()) {
                    if (counter == 0) {
                        BusinessRuleInf.put("value1", dbResultSetVLS.getString("VALUE"));
                        counter++;
                    } else {
                        BusinessRuleInf.put("value2", dbResultSetVLS.getString("VALUE"));
                    }
                }
            }

            //getting value Tuple Compare Rule
            else if (eventType.equals("TCMP")) {
                int counter = 0;
                while (dbResultSetVLS.next()) {
                    String value = dbResultSetVLS.getString("VALUE");
                    String[] values = value.split(".");
                    if (counter == 0) {
                        BusinessRuleInf.put("column1", values[1]);
                        counter++;
                    } else {
                        BusinessRuleInf.put("column2", values[1]);
                    }
                }
            }

            //getting inter-entity Tuple compare Rule
            else if (eventType.equals("ICMP")) {
                while (dbResultSetVLS.next()) {
                    String value = dbResultSetVLS.getString("VALUE");
                    String[] values = value.split(".");
                    BusinessRuleInf.put("table", values[0]);
                    BusinessRuleInf.put("column2", values[1]);
                }
            }
            return BusinessRuleInf;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return BusinessRuleInf;
    }
}


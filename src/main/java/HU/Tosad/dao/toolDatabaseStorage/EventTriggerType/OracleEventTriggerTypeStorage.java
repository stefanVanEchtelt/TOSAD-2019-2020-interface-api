package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("OracleEventTriggerTypeStorage")
public class OracleEventTriggerTypeStorage implements EventTriggerTypeStorage {

    @Override
    public List<Integer> addBusinessRule(MultiValueMap<String, String> body) throws JSONException {
        String ettInsert = "";
        String ettUpdate = "";
        String ettDelete = "";

        //make body easy to read
        JSONObject json = new JSONObject(body);
        Iterator<String> keys = json.keys();
        List<Integer> ettIds = new ArrayList<>();

        while(keys.hasNext()){
            String key = keys.next();
            if(key.equals("event_types_insert")){
                ettInsert = "INSERT";
            }
            if(key.equals("event_types_delete")){
                ettDelete = "DELETE";
            }
            if(key.equals("event_types_update")){
                ettUpdate = "UPDATE";
            }
        }

        if(ettInsert.contains("INSERT")){
            ettIds.add(addBusinessRuleSub("INSERT"));
        }
        if(ettUpdate.contains("UPDATE")){
            ettIds.add(addBusinessRuleSub("UPDATE"));
        }
        if(ettDelete.contains("DELETE")){
            ettIds.add(addBusinessRuleSub("DELETE"));
        }
        return ettIds;
    }

    private int addBusinessRuleSub(String eventType){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            String query = "INSERT INTO EVENT_TRIGGER_TYPES (NAME) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, eventType);

            pstmt.executeQuery();
            pstmt.close();

            //get event_Trigger_Types_ID
            String getIdEtt = ("SELECT EVENT_TRIGGER_TYPES_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdEtt);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            dbResultSet.next();
            int ettId = dbResultSet.getInt("currval");
            pstmtGetId.close();
            return ettId;

        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return 0;
    }

    @Override
    public Map<String, String> getBusinessRuleById(int businessRuleId) {
        Map<String, String> BusinessRuleInf = new HashMap<>();

        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            //joins so you get all the event types from one businessRule
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
        return BusinessRuleInf;
    }

}

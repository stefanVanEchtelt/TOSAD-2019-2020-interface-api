package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import HU.Tosad.businessRule.EventTriggerType;
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

@Repository("OracleEventTriggerTypeStorage")
public class OracleEventTriggerTypeStorage implements EventTriggerTypeStorage {

    @Override
    public EventTriggerType Save(EventTriggerType eventTriggerType){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO EVENT_TRIGGER_TYPES (NAME) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, eventTriggerType.getName());
            pstmt.executeQuery();

            pstmt.close();
            return(eventTriggerType);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return eventTriggerType;
    }

    @Override
    public List<Integer> addBusinessRule(Map<String, String> body) throws JSONException {

        //make body easy to read
        JSONObject json = new JSONObject(body);
        List<Integer> ettIds = null;
        String ett = json.getString("event_types[]");
        if(ett.contains("INSERT")){
            ettIds.add(addBusinessRuleSub("INSERT"));
        }
        if(ett.contains("UPDATE")){
            ettIds.add(addBusinessRuleSub("UPDATE"));
        }
        if(ett.contains("DELETE")){
            ettIds.add(addBusinessRuleSub("DELETE"));
        }
        return ettIds;
    }

    private int addBusinessRuleSub(String eventType){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //Database adds EttID
            String query = "INSERT INTO EVENT_TRIGGER_TYPES (NAME) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, eventType);

            pstmt.executeQuery();
            pstmt.close();

            //get event_Trigger_Types_ID
            String getIdEtt = ("SELECT EVENT_TRIGGER_TYPES_ID_SEQ.currval FROM dual");
            PreparedStatement pstmtGetId = con.prepareStatement(getIdEtt);
            ResultSet dbResultSet = pstmtGetId.executeQuery();
            int ettId = dbResultSet.getInt("id");
            pstmtGetId.close();

            return ettId;

        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return 0;
    }

    @Override
    public boolean Delete(int eventTriggerTypeId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM EVENT_TRIGGER_TYPES WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, eventTriggerTypeId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return false;

    }

    @Override
    public EventTriggerType Update(EventTriggerType eventTriggerType, int eventTriggerTypeId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "UPDATE EVENT_TRIGGER_TYPES SET NAME = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, eventTriggerType.getName());
            pstmt.setInt(2, eventTriggerTypeId);

            pstmt.executeQuery();
            pstmt.close();
            return eventTriggerType;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return eventTriggerType;
    }

    @Override
    public List<EventTriggerType> getAll() {
        List<EventTriggerType> EventTriggerTypes = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from EVENT_TRIGGER_TYPES";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String name = dbResultSet.getString("name");
                EventTriggerTypes.add(new EventTriggerType(id, name));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return EventTriggerTypes;
    }
}

package HU.Tosad.dao.toolDatabaseStorage.EventTriggerType;

import HU.Tosad.businessRule.EventTriggerType;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

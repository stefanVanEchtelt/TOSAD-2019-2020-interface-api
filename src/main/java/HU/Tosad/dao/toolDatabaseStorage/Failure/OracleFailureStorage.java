package HU.Tosad.dao.toolDatabaseStorage.Failure;

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
import java.util.Map;

@Repository("OracleFailureStorage")
public class OracleFailureStorage implements FailureStorage {

    @Override
    public boolean addBusinessRule(MultiValueMap<String, String> body, int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            JSONObject json = new JSONObject(body);
            String query = "INSERT INTO FAILURES (MESSAGE, CODE, BUSINESS_RULES_ID, NAME) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, removeBrackString(json.getString("error")));
            pstmt.setInt(2, removeBrackInt(json.getString("code")));
            pstmt.setInt(3, businessRuleId);
            pstmt.setString(4, "FAILURENAME");
            pstmt.executeQuery();

            pstmt.close();
            return true;
        } catch (SQLException | JSONException sqle) { sqle.printStackTrace(); }
        return false;
    }

    @Override
    public Map<String, String> getFailureById(int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            Map<String, String> BusinessRuleInf = new HashMap<>();

            String query = "SELECT * from FAILURES where BUSINESS_RULES_ID = " + businessRuleId;
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet dbResultSetFLR = pstmt.executeQuery();
            while (dbResultSetFLR.next()) {
                BusinessRuleInf.put("message", dbResultSetFLR.getString("MESSAGE"));
                BusinessRuleInf.put("code", dbResultSetFLR.getString("CODE"));
            }
            return BusinessRuleInf;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    //cleaning dirty incomming data
    private int removeBrackInt(String Stringnm){
        String remBrackString = Stringnm.replaceAll("(\"|\\[|\\]|\")","");
        int num = Integer.parseInt(remBrackString);
        return num;
    }

    //cleaning dirty incomming data
    private String removeBrackString(String Stringnm){
        return Stringnm.replaceAll("(\"|\\[|\\]|\")","");
    }
}

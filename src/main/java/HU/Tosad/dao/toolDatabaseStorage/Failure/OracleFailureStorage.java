package HU.Tosad.dao.toolDatabaseStorage.Failure;

import HU.Tosad.businessRule.Failure;
import HU.Tosad.businessRule.Rule;
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

@Repository("OracleFailureStorage")
public class OracleFailureStorage implements FailureStorage {

    @Override
    public Failure Save(Failure failure){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO FAILURES (MESSAGE, CODE, NAME, BUSINESS_RULES_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, failure.getMessage());
            pstmt.setInt(2, failure.getCode());
            pstmt.setString(3, failure.getName());
            pstmt.setInt(4, failure.getBusinessRuleId());
            pstmt.executeQuery();

            pstmt.close();
            return(failure);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return failure;
    }

    @Override
    public boolean addBusinessRule(MultiValueMap<String, String> body, int businessRuleId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {

            //make body easy to read
            JSONObject json = new JSONObject(body);

            //Database adds FailureID
            String query = "INSERT INTO FAILURES (MESSAGE, CODE, BUSINESS_RULES_ID) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, removeBrackString(json.getString("error")));
            pstmt.setInt(2, removeBrackInt(json.getString("code")));
            pstmt.setInt(3, businessRuleId);
            pstmt.executeQuery();

            pstmt.close();
            return true;
        } catch (SQLException | JSONException sqle) { sqle.printStackTrace(); }
        return false;
    }

    private int removeBrackInt(String Stringnm){
        String remBrackString = Stringnm.replaceAll("\\p{P}","");
        int num = Integer.parseInt(remBrackString);
        return num;
    }

    private String removeBrackString(String Stringnm){
        String remBrackString = Stringnm.replaceAll("\\p{P}","");
        return remBrackString;
    }

    @Override
    public boolean Delete(int FailureId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "DELETE FROM FAILURES WHERE ID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, FailureId);
            pstmt.executeQuery();
            pstmt.close();
            return true;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return false;

    }

    @Override
    public Failure Update(Failure failure, int failureId){
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "UPDATE FAILURES SET MESSAGE = ?, CODE = ?, NAME = ?, BUSINESS_RULES_ID = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, failure.getMessage());
            pstmt.setInt(2, failure.getCode());
            pstmt.setString(3, failure.getName());
            pstmt.setInt(4, failure.getBusinessRuleId());
            pstmt.setInt(5, failureId);

            pstmt.executeQuery();
            pstmt.close();
            return failure;
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return failure;
    }

    @Override
    public List<Failure> getAll() {
        List<Failure> Failures = new ArrayList<>();
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query =  "SELECT * from FAILURES";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String message = dbResultSet.getString("message");
                int code = dbResultSet.getInt("code");
                String name = dbResultSet.getString("name");
                int businessRulesId = dbResultSet.getInt("business_rules_id");
                Failures.add(new Failure(id, message, code, name, businessRulesId));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return Failures;
    }
}

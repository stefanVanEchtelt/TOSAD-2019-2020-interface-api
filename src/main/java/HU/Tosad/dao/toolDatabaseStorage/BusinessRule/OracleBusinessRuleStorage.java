package HU.Tosad.dao.toolDatabaseStorage.BusinessRule;

import HU.Tosad.businessRule.BusinessRule;
import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("OracleBusinessRuleStorage")
public class OracleBusinessRuleStorage implements BusinessRuleStorage {

    @Override
    public BusinessRule Save(BusinessRule businessRule){
        //Database adds ID
        try (Connection con = OracleToolDatabaseStorage.getInstance().getConnection()) {
            String query = "INSERT INTO BUSINESS_RULES (NAME, ON_COLUMN, ON_TABLE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, businessRule.getName());
            pstmt.setString(2, businessRule.getColumn());
            pstmt.setString(3, businessRule.getTable());
            pstmt.executeQuery();

            pstmt.close();
            return(businessRule);
        } catch (SQLException sqle) { sqle.printStackTrace(); }
        return businessRule;
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
    public BusinessRule getBusinessRuleByName(String name) {
        return null;
    }
}

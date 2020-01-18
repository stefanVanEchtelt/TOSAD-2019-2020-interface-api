package HU.Tosad.dao;

import HU.Tosad.model.Rule;
import HU.Tosad.model.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleValueStorage implements ValueStorage {
    private List<Value> Values = new ArrayList<Value>();

    @Override
    public Value Save(Value v) {

        return v;
    }

    @Override
    public boolean Delete(Value v) {

        return false;
    }

    @Override
    public Value Update(Value v) {

        return v;
    }

    @Override
    public List<Value> getAll() {
        try (Connection con = OracleConnection.getInstance().getToolConnection()) {
            String query =  "SELECT * FROM values";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();
            System.out.println(con);

            while (dbResultSet.next()) {
                int id = dbResultSet.getInt("id");
                String value = dbResultSet.getString("value");
                int isColumn = dbResultSet.getInt("is_column");
                int sortOrder = dbResultSet.getInt("sort_order");
                int ruleId = dbResultSet.getInt("rule_Id");
                this.Values.add(new Value(id, value, isColumn, sortOrder, ruleId));

            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }

        return Values;
    }
}

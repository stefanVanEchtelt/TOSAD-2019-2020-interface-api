package HU.Tosad.dao;

import HU.Tosad.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleTableStorage implements TableStorage {
    @Override
    public List<Table> getAll() {
        List<Table> tables = new ArrayList<Table>();
        try (Connection con = OracleConnection.getInstance().getTargetConnection()) {
            String query =  "SELECT table_name FROM USER_TABLES";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet dbResultSet = pstmt.executeQuery();
            System.out.println(con);
            while (dbResultSet.next()) {
                dbResultSet.getString("table_name");
                System.out.println(dbResultSet.getString("table_name"));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }

        return tables;
    }

    @Override
    public Table getByName(String name) {
        return null;
    }
}


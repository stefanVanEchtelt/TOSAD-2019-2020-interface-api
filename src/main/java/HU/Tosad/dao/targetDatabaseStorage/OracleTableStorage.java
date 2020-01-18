package HU.Tosad.dao.targetDatabaseStorage;

import HU.Tosad.table.Table;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("oracleTableStorage")
public class OracleTableStorage implements TableStorage {
    @Override
    public List<Table> getAll() {
        List<Table> tables = new ArrayList<>();
        try (Connection con = OracleTargetDatabaseStorage.getInstance().getConnection()) {
            String preparedQuery =  "SELECT table_name FROM USER_TABLES";
            PreparedStatement pstmt = con.prepareStatement(preparedQuery);
            ResultSet dbResultSet = pstmt.executeQuery();

            while (dbResultSet.next()) {
                tables.add(new Table(dbResultSet.getString("table_name")));
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

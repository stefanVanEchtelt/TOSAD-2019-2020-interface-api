package HU.Tosad.dao.targetDatabaseStorage;

import HU.Tosad.table.Column;
import HU.Tosad.table.Table;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("oracleColumnStorage")
public class OracleColumnStorage implements ColumnStorage {
    @Override
    public List<Column> getAll() {
        return null;
    }

    @Override
    public List<Column> getByName(String name) {
        List<Column> columns = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        try (Connection con = OracleTargetDatabaseStorage.getInstance().getConnection()) {
            String preparedQuery =  "SELECT column_name FROM ALL_TAB_COLS WHERE table_name = '"+ name + "'";
            PreparedStatement pstmt = con.prepareStatement(preparedQuery);
            ResultSet dbResultSet = pstmt.executeQuery();
            while (dbResultSet.next()){
                columns.add(new Column(dbResultSet.getString("COLUMN_NAME")));
                System.out.println(dbResultSet.getString("COLUMN_NAME"));
            }

        } catch (SQLException sqle) { sqle.printStackTrace(); }

        return columns;
    }
}

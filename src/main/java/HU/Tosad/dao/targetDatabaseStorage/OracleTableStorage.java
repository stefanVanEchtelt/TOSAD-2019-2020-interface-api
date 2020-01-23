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

    @Override
    public List<Table> getForeignByTable(String name) {
        List<Table> tables = new ArrayList<>();
        try (Connection con = OracleTargetDatabaseStorage.getInstance().getConnection()) {
            String preparedQuery =  "SELECT a.constraint_name,\n" +
                    "       a.table_name,\n" +
                    "       a.column_name,\n" +
                    "       c.owner,\n" +
                    "       c_pk.table_name r_table_name,\n" +
                    "       b.column_name   r_column_name\n" +
                    "FROM user_cons_columns a\n" +
                    "       JOIN user_constraints c ON a.owner = c.owner\n" +
                    "  AND a.constraint_name = c.constraint_name\n" +
                    "       JOIN user_constraints c_pk ON c.r_owner = c_pk.owner\n" +
                    "  AND c.r_constraint_name = c_pk.constraint_name\n" +
                    "       JOIN user_cons_columns b ON C_PK.owner = b.owner\n" +
                    "  AND C_PK.CONSTRAINT_NAME = b.constraint_name AND b.POSITION = a.POSITION\n" +
                    "WHERE c.constraint_type = 'R'\n" +
                    "  AND c_pk.table_name = '" + name + "'";
            PreparedStatement pstmt = con.prepareStatement(preparedQuery);
            ResultSet dbResultSet = pstmt.executeQuery();

            while (dbResultSet.next()) {
                tables.add(new Table(dbResultSet.getString("table_name")));
            }
        } catch (SQLException sqle) { sqle.printStackTrace(); }

        return tables;

    }
}

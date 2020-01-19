package HU.Tosad.dao.toolDatabaseStorage;

import HU.Tosad.dao.toolDatabaseStorage.OracleToolDatabaseStorage;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleToolDatabaseStorage implements ToolDatabaseConnection {
    public static OracleToolDatabaseStorage instance;

    public static OracleToolDatabaseStorage getInstance() {
        if (OracleToolDatabaseStorage.instance == null) {
            OracleToolDatabaseStorage.instance = new OracleToolDatabaseStorage();
        }
        return OracleToolDatabaseStorage.instance;
    }

    public Connection getConnection() {
        String url = "jdbc:oracle:thin:@//ondora04.hu.nl:8521/educ27";
        String username = "TOSAD_TOOL_DB";
        String password = "tosadtooldb";

        Connection result = null;
        try {
            result = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}

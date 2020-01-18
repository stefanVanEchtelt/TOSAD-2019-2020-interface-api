package HU.Tosad.dao.targetDatabaseStorage;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleTargetDatabaseStorage implements TargetDatabaseConnection {
    public static OracleTargetDatabaseStorage instance;

    public static OracleTargetDatabaseStorage getInstance() {
        if (OracleTargetDatabaseStorage.instance == null) {
            OracleTargetDatabaseStorage.instance = new OracleTargetDatabaseStorage();
        }
        return OracleTargetDatabaseStorage.instance;
    }

    public Connection getTargetConnection() {
        String url = "jdbc:oracle:thin:@//ondora04.hu.nl:8521/educ27";
        String username = "TOSAD_TARGET_DB";
        String password = "tosadtargetdb";

        Connection result = null;
        try {
            result = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    public Connection getToolConnection() {
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

package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.Value.ValueStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ValueService {
    private static ValueStorage valueStorage = null;

    @Autowired
    public ValueService(@Qualifier("OracleValueStorage") ValueStorage valueStorage) { this.valueStorage = valueStorage;}

    public List<Value> getAll() throws SQLException {
        return valueStorage.getAll();
    }

    public static boolean addBusinessRule(Map<String, String> body, int rulesid) throws SQLException{
        return valueStorage.addBusinessRule(body, rulesid);
    }

    public Value Save(Value value) throws SQLException{
        return valueStorage.Save(value);
    }

    public Value Update(Value value, int valueId) throws SQLException {
        return valueStorage.Update(value, valueId);
    }

    public boolean Delete(int valueId) throws SQLException {
        return valueStorage.Delete(valueId);
    }
}

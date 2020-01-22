package HU.Tosad.dao.toolDatabaseStorage.Failure;

import HU.Tosad.businessRule.Failure;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FailureStorage {
    public Failure Save(Failure f) throws SQLException;;
    public boolean addBusinessRule(Map<String, String> f, int businessRuleId) throws SQLException;;
    public boolean Delete(int FailureId) throws SQLException;;
    public Failure Update(Failure f, int FailureId) throws SQLException;;
    public List<Failure> getAll() throws SQLException;;
}


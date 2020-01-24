package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.Failure.FailureStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class FailureService {
    private static FailureStorage failureStorage = null;

    @Autowired
    public FailureService(@Qualifier("OracleFailureStorage") FailureStorage failureStorage) { this.failureStorage = failureStorage;}

    public List<Failure> getAll() throws SQLException {
        return failureStorage.getAll();
    }

    public static boolean addBusinessRule(MultiValueMap<String, String> body, int businessRuleId) throws SQLException{
        return failureStorage.addBusinessRule(body, businessRuleId);
    }

    public Failure Save(Failure failure) throws SQLException{
        return failureStorage.Save(failure);
    }

    public Failure Update(Failure failure, int failureId) throws SQLException {
        return failureStorage.Update(failure, failureId);
    }

    public boolean Delete(int failureId) throws SQLException {
        return failureStorage.Delete(failureId);
    }

}
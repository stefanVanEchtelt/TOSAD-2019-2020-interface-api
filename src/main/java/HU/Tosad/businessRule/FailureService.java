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

    public static Map<String, String> getBusinessRulesById(int id) {
        return failureStorage.getFailureById(id);
    }


    public static boolean addBusinessRule(MultiValueMap<String, String> body, int businessRuleId) throws SQLException{
        return failureStorage.addBusinessRule(body, businessRuleId);
    }

}
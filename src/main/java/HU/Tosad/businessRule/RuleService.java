package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.Rule.RuleStorage;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org. springframework.util.MultiValueMap;
import businessRuleBuilder.BusinessRuleGenerator;
import businessRuleBuilder.OracleBusinessRuleGenerator;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Service
public class RuleService {
    private static RuleStorage ruleStorage = null;

    @Autowired
    public RuleService(@Qualifier("OracleRuleStorage") RuleStorage ruleStorage) { this.ruleStorage = ruleStorage;}


    public static List<Integer> addBusinessRule(MultiValueMap<String, String> body, int businessRuleId) throws SQLException, JSONException {
        return ruleStorage.addBusinessRule(body, businessRuleId);
    }

    public static Map<String, String> getBusinessRuleById(int businessRuleId) {
        return ruleStorage.getBusinessRuleById(businessRuleId);
    }

    public String Example(int ruleId) {
        BusinessRuleGenerator x = new OracleBusinessRuleGenerator();
        return x.example(ruleId);
    }

    public Boolean Execute(int ruleId) {
        BusinessRuleGenerator businessRuleGenerator = new OracleBusinessRuleGenerator();
        return businessRuleGenerator.execute(ruleId);
    }

}
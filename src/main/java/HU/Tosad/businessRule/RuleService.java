package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.Rule.RuleStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
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

    public List<Rule> getAll() throws SQLException {
        return ruleStorage.getAll();
    }

    public static List<Integer> addBusinessRule(Map<String, String> body, int businessRuleId) throws SQLException{
        return ruleStorage.addBusinessRule(body, businessRuleId);
    }

    public Rule Save(Rule rule) throws SQLException{
        return ruleStorage.Save(rule);
    }

    public Rule Update(Rule rule, int ruleId) throws SQLException {
        return ruleStorage.Update(rule, ruleId);
    }

    public boolean Delete(int ruleId) throws SQLException {
        return ruleStorage.Delete(ruleId);
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
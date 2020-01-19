package HU.Tosad.businessRule;

import HU.Tosad.dao.toolDatabaseStorage.Rule.RuleStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RuleService {
    private final RuleStorage ruleStorage;

    @Autowired
    public RuleService(@Qualifier("OracleRuleStorage") RuleStorage ruleStorage) { this.ruleStorage = ruleStorage;}

    public List<Rule> getAll() throws SQLException {
        return ruleStorage.getAll();
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

}
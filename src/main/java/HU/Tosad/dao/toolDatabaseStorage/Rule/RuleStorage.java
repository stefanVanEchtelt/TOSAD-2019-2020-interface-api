package HU.Tosad.dao.toolDatabaseStorage.Rule;

import HU.Tosad.businessRule.Rule;

import java.sql.SQLException;
import java.util.List;

public interface RuleStorage {
    public Rule Save(Rule r) throws SQLException;;
    public boolean Delete(int ruleId) throws SQLException;;
    public Rule Update(Rule r, int ruleId) throws SQLException;;
    public List<Rule> getAll() throws SQLException;;
}


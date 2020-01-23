package HU.Tosad.api;

import HU.Tosad.businessRule.Rule;
import HU.Tosad.businessRule.RuleService;
import HU.Tosad.dao.toolDatabaseStorage.Rule.RuleStorage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Path;
import java.sql.SQLException;
import java.util.Map;

@CrossOrigin
@RequestMapping("/api/tosad/businessRule/rules")
@RestController
public class RuleController {

    private final RuleService RuleService;

    @Autowired
    public RuleController(RuleService ruleService) { this.RuleService = ruleService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(RuleService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody Rule rule) throws SQLException {
        RuleService.Save(rule);
        return null;
    }

    @PutMapping(value = "/{ruleId}", consumes = "application/json", produces = "application/json")
    public Boolean Update(@RequestBody Rule rule, @PathVariable int ruleId) throws SQLException {

        RuleService.Update(rule, ruleId);
        return true;
    }

    @DeleteMapping(value = "/{ruleId}")
    public Boolean delete(@PathVariable int ruleId) throws SQLException {
        RuleService.Delete(ruleId);
        return true;
    }

    @GetMapping(path="/businessRules/example/{ruleId}")
    public String getBusinessRuleExample(@PathVariable("ruleId") String ruleIdString) {
        int ruleId = Integer.parseInt(ruleIdString);
        String json = new Gson().toJson(RuleService.Example(ruleId));
        return json;
    }

    @PostMapping(path="/businessRules/execute/{ruleId}")
    public String executeBusinessRule(@PathVariable("ruleId") String ruleIdString) {
        int ruleId = Integer.parseInt(ruleIdString);
        String json = new Gson().toJson(RuleService.Execute(ruleId));
        return json;
    }


}
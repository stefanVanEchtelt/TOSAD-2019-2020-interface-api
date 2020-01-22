package HU.Tosad.api;

import HU.Tosad.businessRule.*;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;


@CrossOrigin(origins = "*")
@RequestMapping("/api/tosad/businessRule/businessRule")
@RestController
public class BusinessRuleController {

    private final BusinessRuleService BusinessRuleService;

    @Autowired
    public BusinessRuleController(BusinessRuleService businessRuleService) { this.BusinessRuleService = businessRuleService;}


    @PostMapping
    public Boolean addBusinessRule(@RequestParam Map<String, String> body) throws SQLException, JSONException {
        System.out.println(body);
        int businessRuleId = BusinessRuleService.Save(body);
        ValueService.addBusinessRule(body, RuleService.addBusinessRule(body, businessRuleId));
        BusinessRuleEventTriggerService.addBusinessRule(businessRuleId, EventTriggerTypeService.addBusinessRule(body));
        FailureService.addBusinessRule(body, businessRuleId);
        return true;
    }

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(BusinessRuleService.getAll());
        System.out.println(json);
        return json;
    }


    @PutMapping(value = "/{businessRuleId}", consumes = "application/json", produces = "application/json")
    public Boolean Update(@RequestBody BusinessRule businessRule, @PathVariable int businessRuleId) throws SQLException {

        BusinessRuleService.Update(businessRule, businessRuleId);
        return true;
    }

    @DeleteMapping(value = "/{businessRuleId}")
    public Boolean delete(@PathVariable int businessRuleId) throws SQLException {
        BusinessRuleService.Delete(businessRuleId);
        return true;
    }

    @GetMapping(path="/businessRules/column/{name}")
    public String getBusinessRulesByColumn(@PathVariable("name") String name) {
        String json = new Gson().toJson(BusinessRuleService.getBusinessRulesByColumn(name));
        return json;
    }

    @GetMapping(path="/businessRules/table/{name}")
    public String getBusinessRulesByTable(@PathVariable("name") String name) {
        String json = new Gson().toJson(BusinessRuleService.getBusinessRulesByTable(name));
        return json;
    }

    @GetMapping(path="/businessRules/naam/{name}")
    public String getBusinessRulesByName(@PathVariable("name") String name) {
        String json = new Gson().toJson(BusinessRuleService.getBusinessRulesByName(name));
        return json;
    }

}
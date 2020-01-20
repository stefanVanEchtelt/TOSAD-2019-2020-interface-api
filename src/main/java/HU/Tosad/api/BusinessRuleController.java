package HU.Tosad.api;

import HU.Tosad.businessRule.BusinessRule;
import HU.Tosad.businessRule.BusinessRuleService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RequestMapping("/api/tosad/businessRule/businessRule")
@RestController
public class BusinessRuleController {

    private final BusinessRuleService BusinessRuleService;

    @Autowired
    public BusinessRuleController(BusinessRuleService businessRuleService) { this.BusinessRuleService = businessRuleService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(BusinessRuleService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody BusinessRule businessRule) throws SQLException {
        BusinessRuleService.Save(businessRule);
        return null;
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
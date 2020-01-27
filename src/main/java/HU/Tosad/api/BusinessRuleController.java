package HU.Tosad.api;

import HU.Tosad.businessRule.*;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")
@RequestMapping("/api/tosad/businessRule/businessRule")
@RestController
public class BusinessRuleController {

    private final BusinessRuleService BusinessRuleService;

    @Autowired
    public BusinessRuleController(BusinessRuleService businessRuleService) { this.BusinessRuleService = businessRuleService;}


    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Boolean addBusinessRule(@RequestBody MultiValueMap<String, String> body)throws SQLException, JSONException {
        System.out.println(body);
        int businessRuleId = BusinessRuleService.Save(body);
        ValueService.addBusinessRule(body, RuleService.addBusinessRule(body, businessRuleId));
        BusinessRuleEventTriggerService.addBusinessRule(businessRuleId, EventTriggerTypeService.addBusinessRule(body));
        FailureService.addBusinessRule(body, businessRuleId);
        return true;
    }

    @GetMapping(path="/businessRules/id/{id}")
    public String getBusinessRulesById(@PathVariable("id") int id) throws SQLException {
        Map<String, String> BusinessRuleInf = new HashMap<>();
        BusinessRuleInf.putAll(BusinessRuleService.getBusinessRulesById(id));
        BusinessRuleInf.putAll(FailureService.getBusinessRulesById(id));
        BusinessRuleInf.putAll(EventTriggerTypeService.getBusinessRuleById(id));
        BusinessRuleInf.putAll(RuleService.getBusinessRuleById(id));
        BusinessRuleInf.putAll(ValueService.getBusinessRuleById(id));

        String json = new Gson().toJson(BusinessRuleInf);
        return json;
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

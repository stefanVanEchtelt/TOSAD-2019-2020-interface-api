package HU.Tosad.api;

import HU.Tosad.businessRule.*;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.ws.rs.FormParam;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
        String json = new Gson().toJson(BusinessRuleService.getBusinessRulesById(id));
        return json;
    }

    @DeleteMapping(value = "/{businessRuleId}")
    public Boolean delete(@PathVariable int businessRuleId) throws SQLException {
        BusinessRuleService.Delete(businessRuleId);
        return true;
    }
}
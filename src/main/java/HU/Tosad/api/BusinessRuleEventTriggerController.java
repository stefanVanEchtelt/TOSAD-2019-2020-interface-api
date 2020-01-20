package HU.Tosad.api;

import HU.Tosad.businessRule.BusinessRuleEventTrigger;
import HU.Tosad.businessRule.BusinessRuleEventTriggerService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin
@RequestMapping("/api/tosad/businessRule/businessRuleEventTrigger")
@RestController
public class BusinessRuleEventTriggerController {

    private final BusinessRuleEventTriggerService BusinessRuleEventTriggerService;

    @Autowired
    public BusinessRuleEventTriggerController(BusinessRuleEventTriggerService businessRuleEventTriggerService) { this.BusinessRuleEventTriggerService = businessRuleEventTriggerService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(BusinessRuleEventTriggerService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody BusinessRuleEventTrigger bret) throws SQLException {
        BusinessRuleEventTriggerService.Save(bret);
        return null;
    }

//    @PutMapping(value = "/{businessRuleEventTriggerId}", consumes = "application/json", produces = "application/json")
//    public Boolean Update(@RequestBody BusinessRuleEventTrigger bret, @PathVariable int businessRuleEventTriggerId) throws SQLException {
//
//        BusinessRuleEventTriggerService.Update(bret, businessRuleEventTriggerId);
//        return true;
//    }

    @DeleteMapping(value = "/{businessRuleEventTriggerId}")
    public Boolean delete(@PathVariable int businessRuleEventTriggerId) throws SQLException {
        BusinessRuleEventTriggerService.Delete(businessRuleEventTriggerId);
        return true;
    }

}
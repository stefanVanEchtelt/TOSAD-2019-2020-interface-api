package HU.Tosad.api;

import HU.Tosad.businessRule.EventTriggerType;
import HU.Tosad.businessRule.EventTriggerTypeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/api/tosad/businessRule/eventTriggerType")
@RestController
public class EventTriggerTypeController {

    private final EventTriggerTypeService EventTriggerTypeService;

    @Autowired
    public EventTriggerTypeController(EventTriggerTypeService eventTriggerTypeService) { this.EventTriggerTypeService = eventTriggerTypeService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(EventTriggerTypeService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody EventTriggerType eventTriggerType) throws SQLException {
        EventTriggerTypeService.Save(eventTriggerType);
        return null;
    }

    @PutMapping(value = "/{eventTriggerTypeId}", consumes = "application/json", produces = "application/json")
    public Boolean Update(@RequestBody EventTriggerType eventTriggerType, @PathVariable int eventTriggerTypeId) throws SQLException {

        EventTriggerTypeService.Update(eventTriggerType, eventTriggerTypeId);
        return true;
    }

    @DeleteMapping(value = "/{eventTriggerTypeId}")
    public Boolean delete(@PathVariable int eventTriggerTypeId) throws SQLException {
        EventTriggerTypeService.Delete(eventTriggerTypeId);
        return true;
    }


}
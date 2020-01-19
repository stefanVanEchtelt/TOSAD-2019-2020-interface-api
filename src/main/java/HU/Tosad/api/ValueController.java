package HU.Tosad.api;

import HU.Tosad.businessRule.Value;
import HU.Tosad.businessRule.ValueService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/api/tosad/businessRule/value")
@RestController
public class ValueController {

    private final ValueService ValueService;

    @Autowired
    public ValueController(ValueService valueService) { this.ValueService = valueService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(ValueService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody Value value) throws SQLException {
        ValueService.Save(value);
        return null;
    }

    @PutMapping(value = "/{valueId}", consumes = "application/json", produces = "application/json")
    public Boolean Update(@RequestBody Value value, @PathVariable int valueId) throws SQLException {

        ValueService.Update(value, valueId);
        return true;
    }

    @DeleteMapping(value = "/{valueId}")
    public Boolean delete(@PathVariable int valueId) throws SQLException {
        ValueService.Delete(valueId);
        return true;
    }

}
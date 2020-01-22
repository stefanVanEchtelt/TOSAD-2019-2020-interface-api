package HU.Tosad.api;

        import HU.Tosad.businessRule.Failure;
        import HU.Tosad.businessRule.FailureService;
        import HU.Tosad.dao.toolDatabaseStorage.Failure.FailureStorage;
        import com.google.gson.Gson;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.sql.SQLException;
        import java.util.Map;

@CrossOrigin
@RequestMapping("/api/tosad/businessRule/failures")
@RestController
public class FailuresController {

    private final HU.Tosad.businessRule.FailureService FailureService;

    @Autowired
    public FailuresController(FailureService failureService) { this.FailureService = failureService;}

    @GetMapping
    public String getAll() throws SQLException {
        String json = new Gson().toJson(FailureService.getAll());
        System.out.println(json);
        return json;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Boolean Save(@RequestBody Failure failure) throws SQLException {
        FailureService.Save(failure);
        return null;
    }

    @PutMapping(value = "/{FailureId}", consumes = "application/json", produces = "application/json")
    public Boolean Update(@RequestBody Failure failure, @PathVariable int failureId) throws SQLException {

        FailureService.Update(failure, failureId);
        return true;
    }

    @DeleteMapping(value = "/{failureId}")
    public Boolean delete(@PathVariable int failureId) throws SQLException {
        FailureService.Delete(failureId);
        return true;
    }


}
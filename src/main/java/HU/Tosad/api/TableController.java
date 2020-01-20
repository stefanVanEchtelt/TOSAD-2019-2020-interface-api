package HU.Tosad.api;

import HU.Tosad.table.TableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/tosad/table")
@RestController
@CrossOrigin
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) { this.tableService = tableService;}

    @GetMapping
    public String getAll() {
        String json = new Gson().toJson(tableService.getAll());
        return json;
    }

}

package HU.Tosad.api;

import HU.Tosad.table.TableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(path="/{name}")
    public String getForeignTables(@PathVariable("name") String name){
        String json = new Gson().toJson(tableService.getForeignTables(name));
        return json;
    }


}

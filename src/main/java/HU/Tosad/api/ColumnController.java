package HU.Tosad.api;


import HU.Tosad.table.ColumnService;
import HU.Tosad.table.TableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RequestMapping("/api/tosad/column")
@RestController
public class ColumnController {

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) { this.columnService = columnService;}

    @GetMapping(path="/{name}")
    public String getByName(@PathVariable("name") String name) {
        String json = new Gson().toJson(columnService.getByName(name));
        return json;
    }
}

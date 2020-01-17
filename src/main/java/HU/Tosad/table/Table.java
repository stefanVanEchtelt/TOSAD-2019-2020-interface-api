package HU.Tosad.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Table> tables;

    public Table(String name) {
        this.name = name;
    }

    public static List<Table> getAll() {
        List<Table> tables = new ArrayList<Table>();
        return tables;
    }
}

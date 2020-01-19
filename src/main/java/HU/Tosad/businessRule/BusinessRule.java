package HU.Tosad.businessRule;

import java.util.ArrayList;
import java.util.List;

public class BusinessRule {
    private int id;
    private String name;
    private String column;
    private String table;

    public BusinessRule(int id, String name, String onColumn, String onTable) {
        this.id = id;
        this.name = name;
        this.column = onColumn;
        this.table = onTable;
    }

    public static List<BusinessRule> getAll() {
        List<BusinessRule> BusinessRules = new ArrayList<BusinessRule>();
        return BusinessRules;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColumn() {
        return column;
    }

    public String getTable() {
        return table;
    }
}

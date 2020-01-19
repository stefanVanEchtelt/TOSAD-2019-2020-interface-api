package HU.Tosad.businessRule;

import HU.Tosad.table.Table;

import java.util.ArrayList;
import java.util.List;

public class Value {
    private int id;
    private String value;
    private int isColumn;
    private int sortOrder;
    private int ruleId;
    private List<Value> values;

    public Value(int id, String value, int isColumn, int sortOrder, int ruleId) {
        this.id = id;
        this.value = value;
        this.isColumn = isColumn;
        this.sortOrder = sortOrder;
        this.ruleId = ruleId;
    }

    public static List<Value> getAll() {
        List<Value> values = new ArrayList<Value>();

        return values;
    }

    public int getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public int getIsColumn() {
        return this.isColumn;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public int getRuleId() {
        return this.ruleId;
        }
        }

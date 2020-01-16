package HU.Tosad.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private List<Table> tables;

    public List<Table> getAll(){
        List<Table> tables = new ArrayList<Table>();
        return tables;
    }

    public List<Column> getColumnsByTable(String ColumnName){
        List<Column> columns = new ArrayList<Column>();
        return columns;
    }

    public Table getByName(String name){
        Table table = new Table();
        return table;
    }

    public BusinessRule getBusinessRuleByTable(Table t){
        BusinessRule br = new BusinessRule();
        return br;
    }
}

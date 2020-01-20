package HU.Tosad.table;

import HU.Tosad.dao.targetDatabaseStorage.ColumnStorage;
import HU.Tosad.dao.targetDatabaseStorage.TableStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ColumnService {
    private final ColumnStorage columnStorage;

    @Autowired
    public ColumnService(@Qualifier("oracleColumnStorage") ColumnStorage columnStorage) { this.columnStorage = columnStorage;}

    @PostMapping
    public List<Column> getByName(String columnName) {
        return columnStorage.getByName(columnName);
    }
}

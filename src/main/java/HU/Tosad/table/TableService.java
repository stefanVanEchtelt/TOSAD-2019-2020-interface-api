package HU.Tosad.table;

import java.util.List;

import HU.Tosad.dao.targetDatabaseStorage.TableStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    private final TableStorage tableStorage;

    @Autowired
    public TableService(@Qualifier("oracleTableStorage") TableStorage tableStorage) { this.tableStorage = tableStorage;}

    public List<Table> getAll() {
        return tableStorage.getAll();
    }
}

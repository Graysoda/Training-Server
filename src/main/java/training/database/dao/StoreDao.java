package training.database.dao;

import training.generated.Store;

public interface StoreDao {
    Store getById(long id);
}

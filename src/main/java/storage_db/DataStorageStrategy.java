package storage_db;

import java.util.Optional;

public interface DataStorageStrategy<T>{

    boolean salva(T entity);
    Optional<T> trova(T entity);
    boolean aggiorna(T entity);
    boolean elimina(T entity);
}

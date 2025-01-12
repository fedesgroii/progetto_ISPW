package storage_db;

public interface DataStorageStrategy<T>{

    boolean salva(T entity);
    T trova(T entity);
    boolean aggiorna(T entity);
    boolean elimina(T entity);
}

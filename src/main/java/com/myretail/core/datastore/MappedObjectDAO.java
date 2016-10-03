package com.myretail.core.datastore;

public class MappedObjectDAO<T> implements DataAccessObject<T> {
    
    private String name;
    
    private DataStore dataStore;

    public MappedObjectDAO(String name, DataStore dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }
    
    @Override
    public T getItemById(String id) {
        return (T)dataStore.getDataItem(name, id);
    }

    @Override
    public Boolean addItemAtId(String id, T item) {
        return dataStore.addDataItem(name, id, item);
    }
    
}

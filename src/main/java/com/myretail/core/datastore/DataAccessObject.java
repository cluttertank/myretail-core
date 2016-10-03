package com.myretail.core.datastore;

public interface DataAccessObject<T> {

    T getItemById(String id);

    Boolean addItemAtId(String id, T item);
    
}

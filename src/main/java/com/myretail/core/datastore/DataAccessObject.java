package com.myretail.core.datastore;

import java.util.Map;

public interface DataAccessObject {

    <T> T getItemById(String id, Class<T> clazz);

    Map getItemById(String id);
    
    Boolean addItemAtId(String id, Object item);
    
}

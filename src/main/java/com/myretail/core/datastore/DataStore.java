package com.myretail.core.datastore;

public interface DataStore {
    
    Object getDataItem(String group, String id);

    Boolean addDataItem(String group, String id, Object dataItem);
}

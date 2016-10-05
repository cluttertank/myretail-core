package com.myretail.core.datastore;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.core.exception.InternalServerErrorException;

public class MappedObjectDAO implements DataAccessObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappedObjectDAO.class);
    
    private String name;
    
    private DataStore dataStore;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    public MappedObjectDAO(String name, DataStore dataStore) {
        this.name = name;
        this.dataStore = dataStore;
    }
    
    @Override
    public <T> T getItemById(String id, Class<T> clazz) {
        T dataObject = null;
        try {
            String dataString = objectMapper.writeValueAsString(dataStore.getDataItem(name, id));
            LOGGER.debug( "dataStore: {}", dataString );
            dataObject = objectMapper.readValue(dataString, clazz);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error getting data from data store", e);
        }
        return dataObject;
    }

    @Override
    public Map getItemById(String id) {
        return (Map)dataStore.getDataItem(name, id);
    }

    @Override
    public Boolean addItemAtId(String id, Object item) {
        return dataStore.addDataItem(name, id, item);
    }
    
}

package com.myretail.core.datastore;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.core.exception.InternalServerErrorException;

public class MapDataStore implements DataStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapDataStore.class);
    
    private Map<String, Map<String, Object>> dataStore;

    ObjectMapper objectMapper = new ObjectMapper();
    
    public MapDataStore() {
        dataStore = new ConcurrentHashMap<>();
    }

    public MapDataStore(String dataJsonFile) {
        dataStore = new ConcurrentHashMap<>();
        Resource resource = new ClassPathResource("data.json");

        try {
            dataStore = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, Map<String,Object>>>() {});
            LOGGER.debug( "dataStore: {}", objectMapper.writeValueAsString(dataStore) );
        } catch (IOException e) {
            throw new InternalServerErrorException("Unable to read json data file" + dataJsonFile, e);
        }
    }

    public MapDataStore(final Map<String, Map<String, Object>> dataStore) {
        this.dataStore = dataStore;
    }

    public void clearGroup(final String group) {
        Map<String, Object> dataGroup = this.dataStore.get(group);
        if( dataGroup != null ) {
            dataGroup.clear();
        }
    }

    public void addGroup(final String group, final Map<String, Object> dataGroup) {
        this.dataStore.put(group, dataGroup);
    }

    public Boolean addDataItem(final String group, final String id, final Object dataItem) {
        Map<String, Object> dataGroup = this.dataStore.get(group);
        if( dataGroup == null ) {
            dataGroup = new ConcurrentHashMap<>();
            this.dataStore.put(group, dataGroup);
        }
        dataGroup.put(id, dataItem);
        return true;
    }

    @Override
    public Object getDataItem(String group, String id) {
        Object item = null;
        Map<String, Object> dataGroup = this.dataStore.get(group);
        if( dataGroup != null ) {
            item = dataGroup.get(id);
        }
        return item;
    }
}

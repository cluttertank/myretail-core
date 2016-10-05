package com.myretail.core.ipc;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.core.config.PropertyManager;
import com.myretail.core.datastore.MapDataStore;
import com.myretail.core.exception.ErrorInfo;
import com.myretail.core.exception.InternalServerErrorException;

public class RestTemplateIPCClient implements IPCClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateIPCClient.class);
    
    private String clientName;
    
    private String baseUrl;
    
    private RestTemplate restTemplate;
    MultiValueMap<String, String> headers;

    private ObjectMapper objectMapper = new ObjectMapper();

    public RestTemplateIPCClient(String clientName) {
        this.clientName = clientName;
        
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");
        
        baseUrl = PropertyManager.getProperty("restTemplate.baseUrl."+clientName, "http://localhost:8081");
    }
    
    public RestTemplateIPCClient header(String headerName, String headerValue) {
        this.headers.add("Content-Type", "application/json");
        return this;
    }

    public RestTemplateIPCClient baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    @Override
    public <T> T getForObject(String uri, Class<T> clazz) throws IPCClientException {
        return getForObject(null, uri, clazz);
    }

    @Override
    public <T> T postForObject(String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return postForObject(null, uri, requestBody, clazz);
    }

    @Override
    public <T> T putForObject(String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return putForObject(null, uri, requestBody, clazz);
    }

    @Override
    public <T> T getForObject(Map<String, String> headerMap, String uri, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.GET, uri, null, clazz);
    }

    @Override
    public <T> T postForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.POST, uri, requestBody, clazz);
    }

    @Override
    public <T> T putForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.PUT, uri, requestBody, clazz);
    }

    public <T> T exchange(Map<String, String> headerMap, HttpMethod httpMethod, String uri, Object requestBody, Class<T> clazz) {
        if( headerMap != null && !headerMap.isEmpty() ) {
            headerMap.forEach( (key, value) -> {
                headers.add(key, value);
            } );
        }
        RequestEntity<Object> request = null;
        if( requestBody != null ) {
            request = new RequestEntity<Object>(requestBody, headers, httpMethod, URI.create(baseUrl+uri));
        } else {
            request = new RequestEntity<Object>(headers, httpMethod, URI.create(baseUrl+uri));
        }
        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(request, clazz);
        } catch (RestClientException exception) {
            if( exception instanceof HttpStatusCodeException ) {
                HttpStatusCodeException statusCodeException = (HttpStatusCodeException)exception;
                int statusCode = statusCodeException.getRawStatusCode();
                String errorResponseString = statusCodeException.getResponseBodyAsString();
                ErrorInfo errorInfo = null;
                try {
                    LOGGER.info( "errorResponseString: {}", errorResponseString );
                    errorInfo = objectMapper.readValue(errorResponseString, ErrorInfo.class);
                } catch (IOException e) {}
                throw new IPCClientException(errorInfo.getMessage(), exception, statusCode, errorInfo);
            } else {
                throw new IPCClientException("Error occurred during rest call for " + clientName, exception);
            }
        }
        
        T responseObject = null;
        
        Object responseBodyObject = response.getBody();
        LOGGER.debug( "Response body: {}", responseBodyObject );
        LOGGER.debug( "Response body class: {}", responseBodyObject.getClass() );
        if( clazz.isInstance(responseBodyObject) ) {
            responseObject = clazz.cast(responseBodyObject);
        }
        
        LOGGER.debug( "Response object: {}", responseObject );
        
        return responseObject;
    }

}

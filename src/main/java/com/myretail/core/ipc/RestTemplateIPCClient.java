package com.myretail.core.ipc;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.myretail.core.config.PropertyManager;
import com.myretail.core.exception.ErrorInfo;

public class RestTemplateIPCClient implements IPCClient {
    
    private String clientName;
    
    private String baseUrl;
    
    private RestTemplate restTemplate;
    MultiValueMap<String, String> headers;

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
        return getForObject(null, baseUrl+uri, clazz);
    }

    @Override
    public <T> T postForObject(String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return postForObject(null, baseUrl+uri, requestBody, clazz);
    }

    @Override
    public <T> T putForObject(String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return putForObject(null, baseUrl+uri, requestBody, clazz);
    }

    @Override
    public <T> T getForObject(Map<String, String> headerMap, String uri, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.GET, baseUrl+uri, null, clazz);
    }

    @Override
    public <T> T postForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.POST, baseUrl+uri, requestBody, clazz);
    }

    @Override
    public <T> T putForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz) throws IPCClientException {
        return exchange(headerMap, HttpMethod.PUT, baseUrl+uri, requestBody, clazz);
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
        ResponseEntity<Object> response = null;
        try {
            response = restTemplate.exchange(request, Object.class);
        } catch (RestClientException exception) {
            throw new IPCClientException("Error occurred during rest call for " + clientName, exception);
        }
        
//        if( response.getStatusCode() != HttpStatus.OK ) {
//            ErrorInfo errorInfo = new ErrorInfo(message, status, internalCode);
//            throw new IPCClientException("Error occurred during rest call for " + clientName, exception, internalCode, status, errorInfo);
//        }
        
        T responseObject = null;
        
        Object responseBodyObject = response.getBody();
        if( clazz.isInstance(responseBodyObject) ) {
            responseObject = clazz.cast(responseBodyObject);
        }
        
        return responseObject;
    }

}

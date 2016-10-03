package com.myretail.core.ipc;

import java.util.Map;

public interface IPCClient {

    <T> T getForObject(String uri, Class<T> clazz) throws IPCClientException;

    <T> T postForObject(String uri, Object requestBody, Class<T> clazz)
            throws IPCClientException;

    <T> T putForObject(String uri, Object requestBody, Class<T> clazz)
            throws IPCClientException;

    <T> T getForObject(Map<String, String> headerMap, String uri, Class<T> clazz) throws IPCClientException;

    <T> T postForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz)
            throws IPCClientException;

    <T> T putForObject(Map<String, String> headerMap, String uri, Object requestBody, Class<T> clazz)
            throws IPCClientException;

}

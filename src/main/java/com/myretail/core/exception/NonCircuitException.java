package com.myretail.core.exception;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class NonCircuitException extends HystrixBadRequestException {

    private static final long serialVersionUID = 2684755728803563060L;

    private final Object[] params;

    private static final String MESSAGE_CODE = "hystrix.bad.request.exception";
    
    private String internalCode;

    public NonCircuitException() {
        super(MESSAGE_CODE);
        this.params = null;
    }

    public NonCircuitException(String message) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
        this.params = null;
    }

    public NonCircuitException(String message, Throwable cause) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
        this.params = null;
    }

    public NonCircuitException(String messageKey, Object[] params) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey);
        this.params = params == null ? null : params.clone();
    }

    public NonCircuitException(String messageKey, Object[] params, Throwable cause) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, cause);
        this.params = params == null ? null : params.clone();
    }
    
    public NonCircuitException(String message, String internalCode) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
        this.params = null;
        this.internalCode = internalCode;
    }

    public NonCircuitException(String message, Throwable cause, String internalCode) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
        this.params = null;
        this.internalCode = internalCode;
    }

    public NonCircuitException(String messageKey, Object[] params, String internalCode) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey);
        this.params = params == null ? null : params.clone();
        this.internalCode = internalCode;
    }

    public NonCircuitException(String messageKey, Object[] params, Throwable cause, String internalCode) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, cause);
        this.params = params == null ? null : params.clone();
        this.internalCode = internalCode;
    }

    public Object[] getParams() {
        if (params != null) {
            return Arrays.copyOf(params, params.length);
        } else {
            return new Object[0];
        }
    }
    
    public String getInternalCode() {
    	return internalCode;
    }

}

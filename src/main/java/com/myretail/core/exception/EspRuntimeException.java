package com.myretail.core.exception;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public abstract class EspRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -9106109257735859407L;

    private EspErrorCode errorCode = null; // NOSONAR

    private Object[] params = null;

    private static final String MESSAGE_CODE = "esp.runtime.exception";
    
    private String internalCode;

    public EspRuntimeException() {
    	this(MESSAGE_CODE, null, null, null);
    }

    public EspRuntimeException(String messageKey) {
    	this(messageKey, null, null, null);
    }

    public EspRuntimeException(String messageKey, Throwable cause) {
    	this(messageKey, null, cause, null);
    }

    public EspRuntimeException(String messageKey, Object[] params) {
    	this(messageKey, params, null, null);
    }

    public EspRuntimeException(String messageKey, Object[] params, Throwable cause) {
    	this(messageKey, params, cause, null);
    }
    
    public EspRuntimeException(String messageKey, String internalCode) {
    	this(messageKey, null, null, internalCode);
    }

    public EspRuntimeException(String messageKey, Throwable cause, String internalCode) {
    	this(messageKey, null, cause, internalCode);
    }

    public EspRuntimeException(String messageKey, Object[] params, String internalCode) {
    	this(messageKey, params, null, internalCode);
    }

    public EspRuntimeException(String messageKey, Object[] params, Throwable cause, String internalCode) {
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

    public EspRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EspRuntimeException(Throwable cause) {
        super(cause);
    }

    public EspRuntimeException(EspErrorCode errorCode, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public EspRuntimeException(EspErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public EspRuntimeException(EspErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public EspRuntimeException(EspErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public EspRuntimeException(EspErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public EspErrorCode getErrorCode() {
        return errorCode;
    }
    
}

package com.myretail.core.ipc;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.myretail.core.exception.ErrorInfo;
import com.myretail.core.exception.MyRetailRuntimeException;
import com.myretail.core.exception.ValidationErrors;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class IPCClientException extends MyRetailRuntimeException {

    private static final long serialVersionUID = 4703835689781194908L;

    private static final String MESSAGE_CODE = "interal.server.error.exception";

    private final int httpStatusCode;
    
    private final ErrorInfo errorInfo;
    
    private final ValidationErrors validationErrors;

    public IPCClientException(String messageKey) {
        this(messageKey, null, null, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Throwable cause) {
        this(messageKey, null, cause, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Object[] params) {
        this(messageKey, params, null, null, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Object[] params, int httpStatusCode) {
        this(messageKey, params, null, null, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, int httpStatusCode) {
        this(messageKey, null, null, null, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, Throwable cause, int httpStatusCode) {
        this(messageKey, null, cause, null, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, Object[] params, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, params, null, null, httpStatusCode, errorInfo);
    }
    
    public IPCClientException(String messageKey, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, null, null, null, httpStatusCode, errorInfo);
    }

    public IPCClientException(String messageKey, Throwable cause, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, null, cause, null, httpStatusCode, errorInfo);
    }

    public IPCClientException(String messageKey, String internalCode) {
        this(messageKey, null, null, internalCode, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Throwable cause, String internalCode) {
        this(messageKey, null, cause, internalCode, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Object[] params, String internalCode) {
        this(messageKey, params, null, internalCode, HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public IPCClientException(String messageKey, Object[] params, String internalCode, int httpStatusCode) {
        this(messageKey, params, null, internalCode, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, String internalCode, int httpStatusCode) {
        this(messageKey, null, null, internalCode, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, Throwable cause, String internalCode, int httpStatusCode) {
        this(messageKey, null, cause, internalCode, httpStatusCode, null);
    }

    public IPCClientException(String messageKey, Object[] params, String internalCode, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, params, null, internalCode, httpStatusCode, errorInfo);
    }

    public IPCClientException(String messageKey, String internalCode, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, null, null, internalCode, httpStatusCode, errorInfo);
    }

    public IPCClientException(String messageKey, Throwable cause, String internalCode, int httpStatusCode, ErrorInfo errorInfo) {
        this(messageKey, null, cause, internalCode, httpStatusCode, errorInfo);
    }
    
    public IPCClientException(String messageKey, int httpStatusCode, ValidationErrors validationErrors) {
        super(messageKey, null, null, null);
        this.httpStatusCode = httpStatusCode;
        this.validationErrors = validationErrors;
        this.errorInfo = null;
    }

    public IPCClientException(String messageKey, Object[] params, Throwable cause, String internalCode, int httpStatusCode, ErrorInfo errorInfo) {
        super(messageKey, params, cause, internalCode);
        this.httpStatusCode = httpStatusCode;
        this.errorInfo = errorInfo;
        this.validationErrors = null;
    }
    
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
    
    public ValidationErrors getValidationErrors() {
        return validationErrors;
    }

}


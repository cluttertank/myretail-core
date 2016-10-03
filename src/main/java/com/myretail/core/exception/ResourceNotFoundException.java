package com.myretail.core.exception;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ResourceNotFoundException extends NonCircuitException implements Serializable {

    private static final long serialVersionUID = -5343731987042002588L;

    private static final String MESSAGE_CODE = "resource.notfound.exception";

    public ResourceNotFoundException(String message) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
    }

    public ResourceNotFoundException(String messageKey, Object[] params) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params);
    }

    public ResourceNotFoundException(String messageKey, Object[] params, Throwable cause) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params, cause);
    }

}

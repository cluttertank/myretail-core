package com.myretail.core.exception;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class AuthorizationException extends NonCircuitException implements Serializable {

    private static final long serialVersionUID = -4591939994371526941L;

    private static final String MESSAGE_CODE = "authorization.exception";

    public AuthorizationException(String message) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
    }

    public AuthorizationException(String messageKey, Object[] params) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params);
    }

    public AuthorizationException(String messageKey, Object[] params, Throwable cause) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params, cause);
    }

}

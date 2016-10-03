package com.myretail.core.exception;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CryptoException extends NonCircuitException implements Serializable {

    private static final long serialVersionUID = -7748880238584219920L;

    private static final String MESSAGE_CODE = "crypto.exception";

    public CryptoException(String message) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
    }

    public CryptoException(String message, Throwable cause) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
    }

    public CryptoException(String messageKey, Object[] params) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params);
    }

    public CryptoException(String messageKey, Object[] params, Throwable cause) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params, cause);
    }

}

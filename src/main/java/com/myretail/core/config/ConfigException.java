package com.myretail.core.config;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.myretail.core.exception.NonCircuitException;

public class ConfigException extends NonCircuitException implements Serializable {

    private static final long serialVersionUID = -7108506429965650765L;

    private static final String MESSAGE_CODE = "config.exception";

    public ConfigException(String message) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message);
    }

    public ConfigException(String message, Throwable cause) {
        super(StringUtils.isBlank(message) ? MESSAGE_CODE : message, cause);
    }

    public ConfigException(String messageKey, Object[] params) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params);
    }

    public ConfigException(String messageKey, Object[] params, Throwable cause) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params, cause);
    }

}

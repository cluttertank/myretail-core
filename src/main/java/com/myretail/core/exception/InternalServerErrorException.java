package com.myretail.core.exception;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends MyRetailRuntimeException {

    private static final long serialVersionUID = 4703835689781194908L;

    private static final String MESSAGE_CODE = "interal.server.error.exception";

    public InternalServerErrorException(String messageKey) {
    	this(messageKey, null, null, null);
    }

    public InternalServerErrorException(String messageKey, Throwable cause) {
    	this(messageKey, null, cause, null);
    }

    public InternalServerErrorException(String messageKey, Object[] params) {
    	this(messageKey, params, null, null);
    }

    public InternalServerErrorException(String messageKey, Object[] params, Throwable cause) {
    	this(messageKey, params, cause, null);
    }
    
    public InternalServerErrorException(String messageKey, String internalCode) {
    	this(messageKey, null, null, internalCode);
    }

    public InternalServerErrorException(String messageKey, Throwable cause, String internalCode) {
    	this(messageKey, null, cause, internalCode);
    }

    public InternalServerErrorException(String messageKey, Object[] params, String internalCode) {
    	this(messageKey, params, null, internalCode);
    }

    public InternalServerErrorException(String messageKey, Object[] params, Throwable cause, String internalCode) {
        super(StringUtils.isBlank(messageKey) ? MESSAGE_CODE : messageKey, params, cause, internalCode);
    }

}

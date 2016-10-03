package com.myretail.core.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carey Boldenow
 */

@XmlRootElement(name = "FieldError")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestPropertyError {

    private final String field;

    private final String message;
    
    public RequestPropertyError() {
    	this.field = null;
    	this.message = null;
    }

    public RequestPropertyError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}

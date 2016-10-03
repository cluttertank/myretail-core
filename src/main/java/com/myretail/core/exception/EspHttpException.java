package com.myretail.core.exception;


public class EspHttpException extends RuntimeException {

    private static final long serialVersionUID = -6269538665379160366L;
    
    private String internalCode;

    public EspHttpException(String message) {
        super(message);
    }

    public EspHttpException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public EspHttpException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public EspHttpException(String message, Throwable cause, String internalCode) {
        super(message, cause);
        this.internalCode = internalCode;
    }
    
    public String getInternalCode() {
    	return internalCode;
    }
}
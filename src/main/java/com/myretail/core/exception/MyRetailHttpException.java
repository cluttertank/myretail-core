package com.myretail.core.exception;


public class MyRetailHttpException extends RuntimeException {

    private static final long serialVersionUID = -6269538665379160366L;
    
    private String internalCode;

    public MyRetailHttpException(String message) {
        super(message);
    }

    public MyRetailHttpException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MyRetailHttpException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }

    public MyRetailHttpException(String message, Throwable cause, String internalCode) {
        super(message, cause);
        this.internalCode = internalCode;
    }
    
    public String getInternalCode() {
    	return internalCode;
    }
}
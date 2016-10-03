package com.myretail.core.exception;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "ErrorInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfo {

    private String message;

    private String status;
    
    private String internalCode;

    public ErrorInfo() { }

    public ErrorInfo(String message, String status) {
        this.message = message;
        this.status = status;
    }
    
    public ErrorInfo(String message, String status, String internalCode) {
        this.message = message;
        this.status = status;
        this.internalCode = internalCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
	}
    
    

}
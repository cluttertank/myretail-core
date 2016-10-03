package com.myretail.core.exception;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Carey Boldenow
 */
@XmlRootElement(name = "requestErrors")
@XmlAccessorType(XmlAccessType.NONE)
public class ValidationErrors {

    private List<RequestPropertyError> requestErrors = new ArrayList<RequestPropertyError>();

    public ValidationErrors() {
        // empty constructor
    }

    public void addRequestError(String path, String message) {
        RequestPropertyError error = new RequestPropertyError(path, message);
        requestErrors.add(error);
    }

    @XmlElement(name = "requestErrors")
    public List<RequestPropertyError> getRequestErrors() {
        return requestErrors;
    }
}

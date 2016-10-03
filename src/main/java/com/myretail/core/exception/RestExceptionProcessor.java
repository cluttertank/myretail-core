package com.myretail.core.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.netflix.hystrix.exception.HystrixRuntimeException;

@ControllerAdvice
public class RestExceptionProcessor implements MessageSourceAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionProcessor.class);

    private MessageSource messageSource;

    @Autowired
    public RestExceptionProcessor(MessageSource messageSource) {
        this.messageSource = messageSource;
        LOGGER.info( "RestExceptionProcessor created" );
    }
    
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler(HystrixRuntimeException.class)
    @ResponseBody
    public ErrorInfo hystrixRuntimeException(HttpServletRequest req, HystrixRuntimeException ex) {
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Hystrix Runtime Exception]", ex.getMessage())), ex);
        return new ErrorInfo(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT.toString());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo internalServerErrorException(HttpServletRequest req, InternalServerErrorException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Internal Server Exception]", errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getInternalCode());
    }

    @ExceptionHandler(MyRetailRuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo espRuntimeException(HttpServletRequest req, MyRetailRuntimeException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Esp Runtime Exception]", errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getInternalCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo generalException(HttpServletRequest req, Exception ex) {
        String errorMessage = ex.getClass().getName() + ": " + ex.getMessage();
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Exception]", errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo processBadRequestError(HttpServletRequest req, BadRequestException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Bad Request Exception]", errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.BAD_REQUEST.toString(), ex.getInternalCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo processRequestNotReadableError(HttpServletRequest req, HttpMessageNotReadableException ex) {
        String errorMessage = messageSource.getMessage("requestnotreadable.exception", new Object[] { ex.getMessage() },
                LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Http Request Not Readable Exception]",errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo processResourceNotFoundError(HttpServletRequest req, ResourceNotFoundException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Resource Not Found Exception]", errorMessage)), ex);

        return new ErrorInfo(errorMessage, HttpStatus.NOT_FOUND.toString(), ex.getInternalCode());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrors processValidationError(BindException ex) {
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Validation Bind Exception]", ex.getMessage())), ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrors processValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Validation Error]",ex.getMessage())), ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorInfo authorizationException(HttpServletRequest req, AuthorizationException ex) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), LocaleContextHolder.getLocale());
        LOGGER.error(StringEscapeUtils.escapeJava(getErrorMessage("[Authorization Error]", errorMessage)), ex);
        return new ErrorInfo(errorMessage, HttpStatus.UNAUTHORIZED.toString(), ex.getInternalCode());
    }

    private ValidationErrors processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrors dto = new ValidationErrors();
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addRequestError(fieldError.getField(), localizedErrorMessage);
        }
        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        String localizedErrorMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
        // If a message was not found, return the most accurate field error code instead.
        // You can remove this check if you prefer to get the default error message.
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getErrorMessage(String prefix, String message) {
        return String.format("%s %s", prefix, message);
    }

}

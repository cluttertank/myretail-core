package com.myretail.core.exception;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;

@Component
public class CustomExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

    Logger LOG = LoggerFactory.getLogger(CustomExceptionHandlerExceptionResolver.class);

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Map<String, HttpMessageConverter> mapHttpMessageConverter = appContext.getBeansOfType(HttpMessageConverter.class);
        for (HttpMessageConverter<?> httpMessageConverter : mapHttpMessageConverter.values()) {
            getMessageConverters().add(httpMessageConverter);
        }
    }

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
            HandlerMethod handlerMethod, Exception exception) {
        if (exception instanceof HystrixRuntimeException) {
            Throwable cause = exception.getCause();
            FailureType hystrixFailureType = ((HystrixRuntimeException) exception).getFailureType();
            if (cause != null && cause instanceof Exception && (hystrixFailureType == FailureType.COMMAND_EXCEPTION)) {
                exception = (Exception) cause;
            }
        }
        return super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

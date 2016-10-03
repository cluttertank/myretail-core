package com.myretail.core.app;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

//import org.jminix.console.servlet.MiniConsoleServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

//import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

public class WebAppInitializer implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) {

        AbstractRefreshableWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.getEnvironment().setActiveProfiles("prod");
        rootContext.setConfigLocations( "SpringConfiguration.class" );

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(RequestContextListener.class);
        
        FilterRegistration.Dynamic logFilter = servletContext.addFilter( "logFilter", DelegatingFilterProxy.class );
        logFilter.setAsyncSupported(true);
        logFilter.addMappingForServletNames( EnumSet.of(DispatcherType.REQUEST), true, "dispatcher" );

        FilterRegistration.Dynamic asynchDispatchFilter = servletContext.addFilter( "asynchDispatchFilter", DelegatingFilterProxy.class );
        asynchDispatchFilter.setAsyncSupported(true);
        asynchDispatchFilter.addMappingForServletNames( EnumSet.of(DispatcherType.ASYNC), true, "dispatcher" );

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);
        dispatcher.addMapping("/*");

//        ServletRegistration.Dynamic hystrixMetricsStreamServlet =
//        		servletContext.addServlet("hystrixMetricsStreamServlet", new HystrixMetricsStreamServlet());
//        hystrixMetricsStreamServlet.setAsyncSupported(true);
//        hystrixMetricsStreamServlet.addMapping("/hystrix.stream");

//        ServletRegistration.Dynamic jMiniX = servletContext.addServlet("jMiniX", new MiniConsoleServlet());
//        jMiniX.setAsyncSupported(true);
//        jMiniX.addMapping("/jmx/*");
        
    }
 
 }
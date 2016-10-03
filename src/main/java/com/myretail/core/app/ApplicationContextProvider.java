package com.myretail.core.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This class provides access to the Application Context for those objects that need access to
 * application properties files, but cannot leverage Spring's @Value annotation mechanism.
 * @author Carey Boldenow
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextProvider.class);

    /** The application context. */
    private static ApplicationContext ctx = null;

    /**
     * Gets the application context.
     * @return the application context
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework .context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext ctx) {
        ApplicationContextProvider.ctx = ctx; // NOSONAR
    }

    /**
     * Gets the property value.
     * @param property the property
     * @return the property value
     */
    public static String getPropertyValue(String property) {
        return ((ConfigurableApplicationContext) ctx).getBeanFactory().resolveEmbeddedValue("${" + property + "}");
    }

    /**
     * Gets the bean.
     * @param beanName the bean name
     * @return the requested bean
     */
    public static Object getBean(String beanName) {
        Object o = null;
        if (ctx != null) {
            if (ctx.containsBeanDefinition(beanName)) {
                o = ctx.getBean(beanName);
            } else {
                LOGGER.info("[ApplicationContext] No Bean found with name (" + beanName + ")");
            }
        }
        return o;
    }

    public static <T> T getBean(String beanName, Class<T> T) {
        T o = null;
        if (ctx != null) {
            if (ctx.containsBeanDefinition(beanName)) {
                o = T.cast(ctx.getBean(beanName));
            } else {
                LOGGER.info("[ApplicationContext] No Bean found with name (" + beanName + ")");
            }
        }
        return o;
    }
}

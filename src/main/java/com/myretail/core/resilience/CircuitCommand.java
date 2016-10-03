package com.myretail.core.resilience;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myretail.core.config.PropertyManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class CircuitCommand<R> extends HystrixCommand<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitCommand.class);

    private static final String groupKeyPrefix = "hystrix.command.";

    private static final String groupKeySuffix = ".group.key";

    private final Supplier<? extends R> supplier;
    
    public CircuitCommand(Class<? extends CircuitCommand<R>> clasz, Supplier<? extends R> supplier) {
        this(getCommandKey(clasz), supplier);
    }

    public CircuitCommand(String commandKey, Supplier<? extends R> supplier) {
        super(getSetter(commandKey));
        this.supplier = supplier;
    }

    private static final Setter getSetter(String commandKey) {
        String defaultPropKey = groupKeyPrefix + "default" + groupKeySuffix;
        String propKey = groupKeyPrefix + commandKey + groupKeySuffix;
        String groupKey = PropertyManager.getProperty(propKey, PropertyManager.getProperty(defaultPropKey, "DEFAULT"));
        LOGGER.debug("{} created with groupKey: {}", commandKey, groupKey);
        return Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
    }

    private static final String getCommandKey(Class<? extends CircuitCommand> clasz) {
        return clasz.getSimpleName().replaceAll("Command", "").replaceAll("Service", "");
    }

    @Override
    protected R run() {
        return this.supplier.get();
    }
}


package com.bernardomg.async;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public final class LoggingAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(LoggingAsyncExceptionHandler.class);

    public LoggingAsyncExceptionHandler() {
        super();
    }

    @Override
    public final void handleUncaughtException(final Throwable ex, final Method method, final Object... params) {
        log.error("Failure at {} with params {}: {}", method.getName(), params, ex.getMessage());
    }

}

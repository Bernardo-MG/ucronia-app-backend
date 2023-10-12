
package com.bernardomg.async;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LoggingAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    public LoggingAsyncExceptionHandler() {
        super();
    }

    @Override
    public final void handleUncaughtException(final Throwable ex, final Method method, final Object... params) {
        log.error("Failure at {} with params {}: {}", method.getName(), params, ex.getMessage());
    }

}

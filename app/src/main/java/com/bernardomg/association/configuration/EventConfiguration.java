
package com.bernardomg.association.configuration;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bernardomg.event.emitter.AsynchronousEventEmitter;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.event.emitter.TransactionalEventEmitterWrapper;
import com.bernardomg.event.listener.EventListener;

@Configuration
public class EventConfiguration {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService eventExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    @Primary
    public EventEmitter testEventEmitter(final Collection<EventListener<?>> listeners,
            final ExecutorService eventExecutor) {
        final EventEmitter asyncEmitter;

        // TODO: add a better error logging
        asyncEmitter = new AsynchronousEventEmitter(listeners, eventExecutor,
            (event, exception) -> LoggerFactory.getLogger(EventConfiguration.class)
                .error("Event handling failed: {}", event, exception));

        return new TransactionalEventEmitterWrapper(asyncEmitter);
    }

}

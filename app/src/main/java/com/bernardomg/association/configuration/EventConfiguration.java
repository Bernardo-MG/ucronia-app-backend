
package com.bernardomg.association.configuration;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.event.emitter.SynchronousEventEmitter;
import com.bernardomg.event.listener.EventListener;

@Configuration
public class EventConfiguration {

    @Bean("eventEmitter")
    public EventEmitter getEventEmitter(final Collection<EventListener<?>> listeners) {
        return new SynchronousEventEmitter(listeners);
    }

}

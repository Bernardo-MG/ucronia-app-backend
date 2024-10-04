
package com.bernardomg.event.emitter;

import java.util.Objects;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public final class SpringEventEmitter<E extends ApplicationEvent> implements EventEmitter<E> {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventEmitter(final ApplicationEventPublisher eventPub) {
        super();

        eventPublisher = Objects.requireNonNull(eventPub);
    }

    @Override
    public final void emit(final E event) {
        eventPublisher.publishEvent(event);
    }

}


package com.bernardomg.event.bus;

import java.util.Objects;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

public final class SpringEventBus<E extends ApplicationEvent> implements EventBus<E> {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventBus(final ApplicationEventPublisher eventPub) {
        super();

        eventPublisher = Objects.requireNonNull(eventPub);
    }

    @Override
    public final void emit(final E event) {
        eventPublisher.publishEvent(event);
    }

}

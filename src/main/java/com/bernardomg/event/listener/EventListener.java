
package com.bernardomg.event.listener;

import com.bernardomg.event.domain.AbstractEvent;

public interface EventListener<E extends AbstractEvent> {

    public Class<E> getEventType();

    public void handle(final E event);

}

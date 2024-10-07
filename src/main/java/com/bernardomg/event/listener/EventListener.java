
package com.bernardomg.event.listener;

public interface EventListener<E> {

    public Class<E> getEventType();

    public void handle(final E event);

}

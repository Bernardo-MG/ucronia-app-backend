
package com.bernardomg.event.bus;

public interface EventBus<E> {

    public void emit(final E event);

}

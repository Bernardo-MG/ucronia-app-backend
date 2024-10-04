
package com.bernardomg.event.emitter;

public interface EventEmitter<E> {

    public void emit(final E event);

}

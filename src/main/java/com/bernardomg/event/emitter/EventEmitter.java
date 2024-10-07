
package com.bernardomg.event.emitter;

public interface EventEmitter {

    public <E> void emit(final E event);

}

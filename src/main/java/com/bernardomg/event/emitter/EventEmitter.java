
package com.bernardomg.event.emitter;

import com.bernardomg.event.domain.AbstractEvent;

public interface EventEmitter {

    public <E extends AbstractEvent> void emit(final E event);

}

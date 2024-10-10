
package com.bernardomg.event.emitter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bernardomg.event.domain.AbstractEvent;
import com.bernardomg.event.listener.EventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SynchronousEventEmitter implements EventEmitter {

    private final Map<Class<?>, List<EventListener<?>>> listeners;

    public SynchronousEventEmitter(final Collection<EventListener<?>> lsts) {
        super();

        listeners = lsts.stream()
            .collect(Collectors.groupingBy(EventListener::getEventType));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <E extends AbstractEvent> void emit(final E event) {
        final List<EventListener<?>> found;

        log.debug("Emiting event of type {} to listeners", event.getClass());

        found = listeners.getOrDefault(event.getClass(), List.of());

        log.debug("Found listeners for event of type {}: {}", event.getClass(), found);

        found.stream()
            .map(l -> (EventListener<E>) l)
            .forEach(l -> l.handle(event));
    }

}

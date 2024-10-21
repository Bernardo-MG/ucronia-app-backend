
package com.bernardomg.association.schedule.usecase.service;

import java.util.Objects;

import com.bernardomg.association.event.domain.MonthStartEvent;
import com.bernardomg.event.emitter.EventEmitter;

public final class DefaultScheduleService implements ScheduleService {

    private final EventEmitter eventEmitter;

    public DefaultScheduleService(final EventEmitter eventEmit) {
        super();

        eventEmitter = Objects.requireNonNull(eventEmit);
    }

    @Override
    public void monthStarts() {
        eventEmitter.emit(new MonthStartEvent(this));
    }

}

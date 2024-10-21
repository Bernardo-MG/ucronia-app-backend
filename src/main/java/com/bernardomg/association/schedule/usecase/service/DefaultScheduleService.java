
package com.bernardomg.association.schedule.usecase.service;

import java.time.YearMonth;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.bernardomg.association.event.domain.MonthStartEvent;
import com.bernardomg.event.emitter.EventEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class DefaultScheduleService implements ScheduleService {

    private final EventEmitter eventEmitter;

    public DefaultScheduleService(final EventEmitter eventEmit) {
        super();

        eventEmitter = Objects.requireNonNull(eventEmit);
    }

    @Override
    public void monthStarts() {
        log.debug("Sending month start event");
        eventEmitter.emit(new MonthStartEvent(this, YearMonth.now()));
        log.debug("Sent month start event");
    }

}

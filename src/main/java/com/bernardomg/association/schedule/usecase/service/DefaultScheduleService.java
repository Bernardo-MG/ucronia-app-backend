
package com.bernardomg.association.schedule.usecase.service;

import java.time.YearMonth;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.event.domain.MonthStartEvent;
import com.bernardomg.event.emitter.EventEmitter;

/**
 * TODO: this doesn't have to be transactional
 */
@Service
@Transactional
public final class DefaultScheduleService implements ScheduleService {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultScheduleService.class);

    private final EventEmitter  eventEmitter;

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

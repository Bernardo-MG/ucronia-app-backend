
package com.bernardomg.association.calendar.fee.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;
import com.bernardomg.association.calendar.fee.persistence.repository.FeeCalendarRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeCalendarService implements FeeCalendarService {

    private static final String         CACHE = "fee_calendar";

    private final FeeCalendarRepository repository;

    @Override
    public final FeeCalendarRange getRange(final boolean onlyActive) {
        // TODO: Shouldn't be cached?

        return repository.findRange(onlyActive);
    }

    @Override
    @Cacheable(cacheNames = CACHE)
    public final Iterable<UserFeeCalendar> getYear(final int year, final boolean onlyActive, final Sort sort) {
        // TODO: It seems the sort is applied to the months, not the calendar itself
        // TODO: Make sure the months are being sorted

        return repository.findAllForYear(onlyActive, year, sort);
    }

}

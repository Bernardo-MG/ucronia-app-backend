
package com.bernardomg.association.fee.calendar.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.persistence.repository.FeeCalendarRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeCalendarService implements FeeCalendarService {

    private static final String         CACHE = "fee_calendar";

    private final FeeCalendarRepository repository;

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    @Cacheable(cacheNames = CACHE)
    public final Iterable<UserFeeCalendar> getAll(final int year, final boolean onlyActive, final Sort sort) {
        final Iterable<UserFeeCalendar> result;

        if (onlyActive) {
            result = repository.findAllForYearWithActiveMember(year, sort);
        } else {
            result = repository.findAllForYear(year, sort);
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('FEE:READ')")
    public final FeeCalendarRange getRange(final boolean onlyActive) {
        final FeeCalendarRange range;

        // TODO: Shouldn't be cached?

        if (onlyActive) {
            range = repository.findRangeWithActiveMember();
        } else {
            range = repository.findRange();
        }

        return range;
    }

}

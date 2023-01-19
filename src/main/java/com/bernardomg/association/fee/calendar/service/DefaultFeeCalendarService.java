
package com.bernardomg.association.fee.calendar.service;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;
import com.bernardomg.association.fee.calendar.repository.FeeCalendarRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultFeeCalendarService implements FeeCalendarService {

    private final FeeCalendarRepository repository;

    @Override
    @PreAuthorize("hasAuthority('READ_FEE_YEAR')")
    public final Iterable<? extends UserFeeCalendar> getAll(final Integer year, final Boolean onlyActive,
            final Sort sort) {
        final Iterable<? extends UserFeeCalendar> result;

        if (onlyActive) {
            result = repository.findAllForYearWithActiveMember(year, sort);
        } else {
            result = repository.findAllForYear(year, sort);
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('READ_FEE_YEAR')")
    public final FeeCalendarRange getRange(final Boolean onlyActive) {
        final FeeCalendarRange range;

        if (onlyActive) {
            range = repository.findRangeWithActiveMember();
        } else {
            range = repository.findRange();
        }

        return range;
    }

}

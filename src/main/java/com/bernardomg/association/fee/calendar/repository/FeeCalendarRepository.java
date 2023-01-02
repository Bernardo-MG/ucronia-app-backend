
package com.bernardomg.association.fee.calendar.repository;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.calendar.model.FeeCalendar;
import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;

public interface FeeCalendarRepository {

    public Iterable<? extends FeeCalendar> findAllForYear(final Integer year, final Sort sort);

    public Iterable<? extends FeeCalendar> findAllForYearWithActiveMember(final Integer year, final Sort sort);

    public FeeCalendarRange findRange();

    public FeeCalendarRange findRangeWithActiveMember();

}

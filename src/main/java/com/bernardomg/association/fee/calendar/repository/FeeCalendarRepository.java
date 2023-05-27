
package com.bernardomg.association.fee.calendar.repository;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;

public interface FeeCalendarRepository {

    public Iterable<UserFeeCalendar> findAllForYear(final Integer year, final Sort sort);

    public Iterable<UserFeeCalendar> findAllForYearWithActiveMember(final Integer year, final Sort sort);

    public FeeCalendarRange findRange();

    public FeeCalendarRange findRangeWithActiveMember();

}

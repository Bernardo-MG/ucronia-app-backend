
package com.bernardomg.association.fee.calendar.persistence.repository;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;

public interface FeeCalendarRepository {

    public Collection<UserFeeCalendar> findAllForYear(final Integer year, final Sort sort);

    public Collection<UserFeeCalendar> findAllForYearWithActiveMember(final Integer year, final Sort sort);

    public FeeCalendarRange findRange();

    public FeeCalendarRange findRangeWithActiveMember();

}

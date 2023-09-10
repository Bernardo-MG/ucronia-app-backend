
package com.bernardomg.association.calendar.fee.persistence.repository;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;

public interface FeeCalendarRepository {

    public Collection<UserFeeCalendar> findAllForYear(final boolean onlyActive, final int year, final Sort sort);

    public FeeCalendarRange findRange(final boolean onlyActive);

}

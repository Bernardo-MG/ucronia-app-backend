
package com.bernardomg.association.calendar.fee.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeCalendarService {

    public FeeCalendarRange getRange(final boolean onlyActive);

    public Iterable<UserFeeCalendar> getYear(final int year, final boolean onlyActive, final Sort sort);

}

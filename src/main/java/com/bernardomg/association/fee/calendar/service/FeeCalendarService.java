
package com.bernardomg.association.fee.calendar.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeCalendarService {

    public Iterable<UserFeeCalendar> getAll(final int year, final boolean onlyActive, final Sort sort);

    public FeeCalendarRange getRange(final boolean onlyActive);

}

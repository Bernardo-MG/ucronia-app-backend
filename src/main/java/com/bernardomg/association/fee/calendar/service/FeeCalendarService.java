
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

    public Iterable<? extends UserFeeCalendar> getAll(final Integer year, final Boolean onlyActive, final Sort sort);

    public FeeCalendarRange getRange(final Boolean onlyActive);

}

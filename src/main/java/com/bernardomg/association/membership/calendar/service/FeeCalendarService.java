
package com.bernardomg.association.membership.calendar.service;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.FeeCalendarRange;
import com.bernardomg.association.membership.calendar.model.UserFeeCalendar;
import com.bernardomg.association.membership.member.model.MemberStatus;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeCalendarService {

    public FeeCalendarRange getRange();

    public Iterable<UserFeeCalendar> getYear(final int year, final MemberStatus active, final Sort sort);

}

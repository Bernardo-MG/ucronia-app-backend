
package com.bernardomg.association.funds.calendar.service;

import java.time.YearMonth;

import com.bernardomg.association.funds.calendar.model.CalendarFundsDate;
import com.bernardomg.association.funds.calendar.model.TransactionRange;

/**
 * Transaction service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FundsCalendarService {

    public TransactionRange getRange();

    public Iterable<? extends CalendarFundsDate> getYearMonth(final YearMonth date);

}


package com.bernardomg.association.funds.calendar.service;

import java.time.YearMonth;

import com.bernardomg.association.funds.calendar.model.TransactionRange;
import com.bernardomg.association.funds.transaction.model.Transaction;

/**
 * Transaction service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface TransactionCalendarService {

    public TransactionRange getRange();

    public Iterable<? extends Transaction> getYearMonth(final YearMonth date);

}

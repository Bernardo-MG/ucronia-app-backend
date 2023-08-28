
package com.bernardomg.association.calendar.transaction.service;

import java.time.YearMonth;

import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionRange;

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

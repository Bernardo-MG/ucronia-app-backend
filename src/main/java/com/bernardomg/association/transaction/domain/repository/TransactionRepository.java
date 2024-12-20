
package com.bernardomg.association.transaction.domain.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;

public interface TransactionRepository {

    public void delete(final long index);

    public boolean exists(final long index);

    public Collection<Transaction> findAll(final Sort sort);

    public Iterable<Transaction> findAll(final TransactionQuery query, final Pageable pageable);

    public TransactionCalendarMonth findInMonth(final YearMonth date);

    public long findNextIndex();

    public Optional<Transaction> findOne(final Long index);

    public TransactionCalendarMonthsRange findRange();

    public Transaction save(final Transaction transaction);

}

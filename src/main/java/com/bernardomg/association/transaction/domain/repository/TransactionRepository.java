
package com.bernardomg.association.transaction.domain.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface TransactionRepository {

    public void delete(final long index);

    public boolean exists(final long index);

    public Collection<Transaction> findAll(final Sorting sorting);

    public Page<Transaction> findAll(final TransactionQuery query, final Pagination pagination, final Sorting sorting);

    public Collection<Transaction> findInRange(final Instant from, final Instant to, final Sorting sorting);

    public long findNextIndex();

    public Optional<Transaction> findOne(final Long index);

    public TransactionCalendarMonthsRange findRange();

    public Transaction save(final Transaction transaction);

}

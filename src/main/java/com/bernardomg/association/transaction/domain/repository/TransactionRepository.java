
package com.bernardomg.association.transaction.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;

public interface TransactionRepository {

    public void delete(final long index);

    public boolean exists(final long index);

    public Iterable<Transaction> findAll(final TransactionQuery query, final Pageable pageable);

    public long findNextIndex();

    public Optional<Transaction> findOne(final Long index);

    public Transaction save(final Transaction transaction);

}

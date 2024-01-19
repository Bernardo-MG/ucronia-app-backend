
package com.bernardomg.association.transaction.usecase;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.domain.exception.MissingTransactionIdException;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionChange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;

    public DefaultTransactionService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final Transaction create(final TransactionChange change) {
        final Transaction transaction;
        final Long        index;

        log.debug("Creating transaction {}", change);

        transaction = toDomain(change);

        // Set index
        index = transactionRepository.findNextIndex();
        transaction.setIndex(index);

        // Trim strings
        transaction.setDescription(transaction.getDescription()
            .trim());

        return transactionRepository.save(transaction);
    }

    @Override
    public final void delete(final long index) {

        log.debug("Deleting transaction {}", index);

        if (!transactionRepository.exists(index)) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        // TODO: Check this deletes on cascade
        transactionRepository.delete(index);
    }

    @Override
    public final Iterable<Transaction> getAll(final TransactionQuery query, final Pageable pageable) {
        return transactionRepository.findAll(query, pageable);
    }

    @Override
    public final Optional<Transaction> getOne(final long index) {
        final Optional<Transaction> transaction;

        log.debug("Reading member with index {}", index);

        transaction = transactionRepository.findOne(index);
        if (transaction.isEmpty()) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        return transaction;
    }

    @Override
    public final Transaction update(final long index, final TransactionChange transaction) {
        final boolean     exists;
        final Transaction toUpdate;

        log.debug("Updating transaction with index {} using data {}", index, transaction);

        exists = transactionRepository.exists(index);
        if (!exists) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        toUpdate = toDomain(transaction);

        // Set index
        toUpdate.setIndex(index);

        // TODO: the model should do this
        // Trim strings
        toUpdate.setDescription(toUpdate.getDescription()
            .trim());

        return transactionRepository.save(toUpdate);
    }

    private final Transaction toDomain(final TransactionChange transaction) {
        return Transaction.builder()
            .date(transaction.getDate())
            .description(transaction.getDescription())
            .amount(transaction.getAmount())
            .build();
    }

}

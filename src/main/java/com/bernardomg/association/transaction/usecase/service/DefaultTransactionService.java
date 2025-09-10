
package com.bernardomg.association.transaction.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultTransactionService implements TransactionService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultTransactionService.class);

    private final TransactionRepository transactionRepository;

    public DefaultTransactionService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final Transaction create(final Transaction transaction) {
        final Long        index;
        final Transaction toCreate;

        log.debug("Creating transaction {}", transaction);

        // Get index
        index = transactionRepository.findNextIndex();

        toCreate = new Transaction(index, transaction.date(), transaction.amount(), transaction.description());

        return transactionRepository.save(toCreate);
    }

    @Override
    public final Transaction delete(final long index) {
        final Transaction transaction;

        log.debug("Deleting transaction {}", index);

        transaction = transactionRepository.findOne(index)
            .orElseThrow(() -> {
                log.error("Missing transaction {}", index);
                throw new MissingTransactionException(index);
            });

        // TODO: Check this deletes on cascade
        transactionRepository.delete(index);

        return transaction;
    }

    @Override
    public final Page<Transaction> getAll(final TransactionQuery query, final Pagination pagination,
            final Sorting sorting) {
        return transactionRepository.findAll(query, pagination, sorting);
    }

    @Override
    public final Optional<Transaction> getOne(final long index) {
        final Optional<Transaction> transaction;

        log.debug("Reading member with index {}", index);

        transaction = transactionRepository.findOne(index);
        if (transaction.isEmpty()) {
            log.error("Missing transaction {}", index);
            throw new MissingTransactionException(index);
        }

        return transaction;
    }

    @Override
    public final Transaction update(final Transaction transaction) {
        final boolean     exists;
        final Transaction toUpdate;

        log.debug("Updating transaction with index {} using data {}", transaction.index(), transaction);

        exists = transactionRepository.exists(transaction.index());
        if (!exists) {
            log.error("Missing transaction {}", transaction.index());
            throw new MissingTransactionException(transaction.index());
        }

        toUpdate = new Transaction(transaction.index(), transaction.date(), transaction.amount(),
            transaction.description());

        return transactionRepository.save(toUpdate);
    }

}


package com.bernardomg.association.transaction.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.model.Transaction;
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
@Transactional
public final class DefaultTransactionService implements TransactionService {

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

        toCreate = Transaction.builder()
            .withIndex(transaction.getIndex())
            .withAmount(transaction.getAmount())
            .withDate(transaction.getDate())
            .withDescription(transaction.getDescription())
            .build();

        // Set index
        index = transactionRepository.findNextIndex();
        toCreate.setIndex(index);

        // Trim strings
        // TODO: should be done by the model
        toCreate.setDescription(toCreate.getDescription()
            .trim());

        return transactionRepository.save(toCreate);
    }

    @Override
    public final void delete(final long index) {

        log.debug("Deleting transaction {}", index);

        if (!transactionRepository.exists(index)) {
            // TODO: change exception name
            throw new MissingTransactionException(index);
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
            throw new MissingTransactionException(index);
        }

        return transaction;
    }

    @Override
    public final Transaction update(final Transaction transaction) {
        final boolean     exists;
        final Transaction toUpdate;

        log.debug("Updating transaction with index {} using data {}", transaction.getIndex(), transaction);

        exists = transactionRepository.exists(transaction.getIndex());
        if (!exists) {
            // TODO: change exception name
            throw new MissingTransactionException(transaction.getIndex());
        }

        toUpdate = Transaction.builder()
            .withIndex(transaction.getIndex())
            .withAmount(transaction.getAmount())
            .withDate(transaction.getDate())
            .withDescription(transaction.getDescription())
            .build();

        // TODO: the model should do this
        // Trim strings
        toUpdate.setDescription(toUpdate.getDescription()
            .trim());

        return transactionRepository.save(toUpdate);
    }

}

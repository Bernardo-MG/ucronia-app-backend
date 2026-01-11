/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import com.bernardomg.association.transaction.usecase.validation.TransactionNotPaidInFutureRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

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
    private static final Logger          log = LoggerFactory.getLogger(DefaultTransactionService.class);

    private final TransactionRepository  transactionRepository;

    private final Validator<Transaction> validatorCreate;

    private final Validator<Transaction> validatorUpdate;

    public DefaultTransactionService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);

        validatorCreate = new FieldRuleValidator<>(new TransactionNotPaidInFutureRule());
        validatorUpdate = new FieldRuleValidator<>(new TransactionNotPaidInFutureRule());
    }

    @Override
    public final Transaction create(final Transaction transaction) {
        final Long        index;
        final Transaction toCreate;
        final Transaction saved;

        log.debug("Creating transaction {}", transaction);

        validatorCreate.validate(transaction);

        // TODO: set inside the repository
        index = transactionRepository.findNextIndex();

        toCreate = new Transaction(index, transaction.date(), transaction.amount(), transaction.description());

        saved = transactionRepository.save(toCreate);

        log.debug("Created transaction {}", saved);

        return saved;
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

        log.debug("Deleted transaction {}", index);

        return transaction;
    }

    @Override
    public final Page<Transaction> getAll(final TransactionQuery query, final Pagination pagination,
            final Sorting sorting) {
        final Page<Transaction> transactions;

        log.info("Getting all transactions with query {}, pagination {} and sorting {}", query, pagination, sorting);

        transactions = transactionRepository.findAll(query, pagination, sorting);

        log.debug("Got all transactions with query {}, pagination {} and sorting {}: {}", query, pagination, sorting,
            transactions);

        return transactions;
    }

    @Override
    public final Optional<Transaction> getOne(final long index) {
        final Optional<Transaction> transaction;

        log.debug("Reading transaction with index {}", index);

        transaction = transactionRepository.findOne(index);
        if (transaction.isEmpty()) {
            log.error("Missing transaction {}", index);
            throw new MissingTransactionException(index);
        }

        log.debug("Read transaction with index {}: {}", index, transaction);

        return transaction;
    }

    @Override
    public final Transaction update(final Transaction transaction) {
        final boolean     exists;
        final Transaction toUpdate;
        final Transaction updated;

        log.debug("Updating transaction with index {} using data {}", transaction.index(), transaction);

        exists = transactionRepository.exists(transaction.index());
        if (!exists) {
            log.error("Missing transaction {}", transaction.index());
            throw new MissingTransactionException(transaction.index());
        }

        toUpdate = new Transaction(transaction.index(), transaction.date(), transaction.amount(),
            transaction.description());

        validatorUpdate.validate(toUpdate);

        updated = transactionRepository.save(toUpdate);

        log.debug("Updated transaction with index {}: {}", transaction.index(), updated);

        return updated;
    }

}

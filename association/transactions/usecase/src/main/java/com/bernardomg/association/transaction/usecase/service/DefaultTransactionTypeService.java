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

import com.bernardomg.association.transaction.domain.exception.MissingTransactionException;
import com.bernardomg.association.transaction.domain.exception.MissingTransactionTypeException;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultTransactionTypeService implements TransactionTypeService {

    /**
     * Logger for the class.
     */
    private static final Logger             log = LoggerFactory.getLogger(DefaultTransactionTypeService.class);

    private final TransactionTypeRepository transactionTypeRepository;

    public DefaultTransactionTypeService(final TransactionTypeRepository transactionTypeRepo) {
        super();

        transactionTypeRepository = Objects.requireNonNull(transactionTypeRepo);
    }

    @Override
    public final TransactionType create(final TransactionType transactionType) {
        final TransactionType saved;

        log.debug("Creating transaction {}", transactionType);

        saved = transactionTypeRepository.save(transactionType);

        log.debug("Created transaction {}", saved);

        return saved;
    }

    @Override
    public final TransactionType delete(final long number) {
        final TransactionType transactionType;

        log.debug("Deleting transaction {}", number);

        transactionType = transactionTypeRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing transaction type {}", number);
                throw new MissingTransactionTypeException(number);
            });

        transactionTypeRepository.delete(number);

        log.debug("Deleted transaction type {}", number);

        return transactionType;
    }

    @Override
    public final Page<TransactionType> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<TransactionType> transactionTypes;

        log.info("Getting all transaction types with pagination {} and sorting {}", pagination, sorting);

        transactionTypes = transactionTypeRepository.findAll(pagination, sorting);

        log.debug("Got all transaction types with pagination {} and sorting {}: {}", pagination, sorting,
            transactionTypes);

        return transactionTypes;
    }

    @Override
    public final Optional<TransactionType> getOne(final long index) {
        final Optional<TransactionType> transactionType;

        log.debug("Reading transaction type with index {}", index);

        transactionType = transactionTypeRepository.findOne(index);
        if (transactionType.isEmpty()) {
            log.error("Missing transaction type {}", index);
            throw new MissingTransactionTypeException(index);
        }

        log.debug("Read transaction type with index {}: {}", index, transactionType);

        return transactionType;
    }

    @Override
    public final TransactionType update(final TransactionType transactionType) {
        final boolean         exists;
        final TransactionType toUpdate;
        final TransactionType updated;

        log.debug("Updating transaction with number {} using data {}", transactionType.number(), transactionType);

        exists = transactionTypeRepository.exists(transactionType.number());
        if (!exists) {
            log.error("Missing transaction {}", transactionType.number());
            throw new MissingTransactionException(transactionType.number());
        }

        toUpdate = new TransactionType(transactionType.number(), transactionType.description(),
            transactionType.color());

        updated = transactionTypeRepository.save(toUpdate);

        log.debug("Updated transaction type with number {}: {}", transactionType.number(), updated);

        return updated;
    }

}

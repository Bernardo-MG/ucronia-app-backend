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

package com.bernardomg.association.transaction.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionTypeEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionTypeEntityMapper;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.domain.repository.TransactionTypeRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

public final class JpaTransactionTypeRepository implements TransactionTypeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                   log = LoggerFactory.getLogger(JpaTransactionTypeRepository.class);

    private final TransactionTypeSpringRepository transactionTypeSpringRepository;

    public JpaTransactionTypeRepository(final TransactionTypeSpringRepository transactionTypeRepo) {
        super();

        transactionTypeSpringRepository = Objects.requireNonNull(transactionTypeRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<TransactionTypeEntity> transaction;

        log.debug("Deleting transaction {}", number);

        transaction = transactionTypeSpringRepository.findByNumber(number);
        if (transaction.isPresent()) {
            transactionTypeSpringRepository.deleteById(transaction.get()
                .getId());

            log.debug("Deleted transaction {}", number);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete transaction {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if transaction {} exists", number);

        exists = transactionTypeSpringRepository.existsByNumber(number);

        log.debug("Transaction {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<TransactionType> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<TransactionTypeEntity> page;
        final org.springframework.data.domain.Page<TransactionType>       read;
        final Pageable                                                    pageable;

        log.debug("Finding transactions with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = transactionTypeSpringRepository.findAll(pageable);

        read = page.map(TransactionTypeEntityMapper::toDomain);

        log.debug("Found transactions {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<TransactionType> findOne(final Long index) {
        final Optional<TransactionType> transactionType;

        log.debug("Finding transaction with index {}", index);

        transactionType = transactionTypeSpringRepository.findByNumber(index)
            .map(TransactionTypeEntityMapper::toDomain);

        log.debug("Found transaction with index {}: {}", index, transactionType);

        return transactionType;
    }

    @Override
    public final TransactionType save(final TransactionType transactionType) {
        final Optional<TransactionTypeEntity> existing;
        final TransactionTypeEntity           entity;
        final TransactionTypeEntity           created;
        final TransactionType                 saved;
        final Long                            index;

        log.debug("Saving transaction type {}", transactionType);

        entity = TransactionTypeEntityMapper.toEntity(transactionType);

        existing = transactionTypeSpringRepository.findByNumber(transactionType.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            index = transactionTypeSpringRepository.findNextNumber();
            entity.setNumber(index);
        }

        created = transactionTypeSpringRepository.save(entity);
        saved = TransactionTypeEntityMapper.toDomain(created);

        log.debug("Saved transaction {}", saved);

        return saved;
    }

}

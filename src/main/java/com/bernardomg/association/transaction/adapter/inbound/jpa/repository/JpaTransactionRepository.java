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

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntityMapper;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.TransactionSpecifications;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaTransactionRepository implements TransactionRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaTransactionRepository.class);

    private final TransactionSpringRepository transactionSpringRepository;

    public JpaTransactionRepository(final TransactionSpringRepository transactionRepo) {
        super();

        transactionSpringRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final void delete(final long index) {
        final Optional<TransactionEntity> transaction;

        log.debug("Deleting transaction {}", index);

        transaction = transactionSpringRepository.findByIndex(index);
        if (transaction.isPresent()) {
            transactionSpringRepository.deleteById(transaction.get()
                .getId());

            log.debug("Deleted transaction {}", index);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete transaction {} as it doesn't exist", index);
        }
    }

    @Override
    public final boolean exists(final long index) {
        final boolean exists;

        log.debug("Checking if transaction {} exists", index);

        exists = transactionSpringRepository.existsByIndex(index);

        log.debug("Transaction {} exists: {}", index, exists);

        return exists;
    }

    @Override
    public final Collection<Transaction> findAll(final Sorting sorting) {
        final Collection<Transaction> read;
        final Sort                    sort;

        log.debug("Finding all transactions sorting by {}", sorting);

        sort = SpringSorting.toSort(sorting);
        read = transactionSpringRepository.findAll(sort)
            .stream()
            .map(TransactionEntityMapper::toDomain)
            .toList();

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final Page<Transaction> findAll(final TransactionQuery query, final Pagination pagination,
            final Sorting sorting) {
        final org.springframework.data.domain.Page<TransactionEntity> page;
        final Optional<Specification<TransactionEntity>>              spec;
        final org.springframework.data.domain.Page<Transaction>       read;
        final Pageable                                                pageable;

        log.debug("Finding transactions with sample {} and pagination {} and sorting {}", query, pagination, sorting);

        spec = TransactionSpecifications.fromQuery(query);

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (spec.isEmpty()) {
            page = transactionSpringRepository.findAll(pageable);
        } else {
            page = transactionSpringRepository.findAll(spec.get(), pageable);
        }

        read = page.map(TransactionEntityMapper::toDomain);

        log.debug("Found transactions {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextIndex() {
        final long index;

        log.debug("Finding next index for the transactions");

        index = transactionSpringRepository.findNextIndex();

        log.debug("Found index {}", index);

        return index;
    }

    @Override
    public final Optional<Transaction> findOne(final Long index) {
        final Optional<Transaction> transaction;

        log.debug("Finding transaction with index {}", index);

        transaction = transactionSpringRepository.findByIndex(index)
            .map(TransactionEntityMapper::toDomain);

        log.debug("Found transaction with index {}: {}", index, transaction);

        return transaction;
    }

    @Override
    public final TransactionCalendarMonthsRange findRange() {
        final Collection<YearMonth>          months;
        final TransactionCalendarMonthsRange range;

        log.debug("Finding the transactions range");

        months = transactionSpringRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        range = new TransactionCalendarMonthsRange(months);

        log.debug("Found the transactions range: {}", range);

        return range;
    }

    @Override
    public final Transaction save(final Transaction transaction) {
        final Optional<TransactionEntity> existing;
        final TransactionEntity           entity;
        final TransactionEntity           created;
        final Transaction                 saved;

        log.debug("Saving transaction {}", transaction);

        entity = TransactionEntityMapper.toEntity(transaction);

        existing = transactionSpringRepository.findByIndex(transaction.index());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = transactionSpringRepository.save(entity);
        saved = TransactionEntityMapper.toDomain(created);

        log.debug("Saved transaction {}", saved);

        return saved;
    }

}

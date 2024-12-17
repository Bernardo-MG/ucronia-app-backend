
package com.bernardomg.association.transaction.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.TransactionSpecifications;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaTransactionRepository implements TransactionRepository {

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
            .map(this::toDomain)
            .toList();

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final Iterable<Transaction> findAll(final TransactionQuery query, final Pagination pagination,
            final Sorting sorting) {
        final Page<TransactionEntity>                    page;
        final Optional<Specification<TransactionEntity>> spec;
        final Iterable<Transaction>                      read;
        final Pageable                                   pageable;
        final Sort                                       sort;

        log.debug("Finding transactions with sample {} and pagination {} and sorting {}", query, pagination, sorting);

        spec = TransactionSpecifications.fromQuery(query);

        sort = SpringSorting.toSort(sorting);
        pageable = PageRequest.of(pagination.page(), pagination.size(), sort);
        if (spec.isEmpty()) {
            page = transactionSpringRepository.findAll(pageable);
        } else {
            page = transactionSpringRepository.findAll(spec.get(), pageable);
        }

        read = page.map(this::toDomain);

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final TransactionCalendarMonth findInMonth(final YearMonth date) {
        final Specification<TransactionEntity> spec;
        final Collection<TransactionEntity>    read;
        final Collection<Transaction>          transactions;
        final TransactionCalendarMonth         monthCalendar;
        final Sort                             sort;

        log.debug("Finding all the transactions for the month {}", date);

        sort = Sort.by("date", "description", "amount");

        spec = TransactionSpecifications.on(date);
        read = transactionSpringRepository.findAll(spec, sort);

        transactions = read.stream()
            .map(this::toDomain)
            .toList();
        monthCalendar = new TransactionCalendarMonth(date, transactions);

        log.debug("Found all the transactions for the month {}: {}", date, monthCalendar);

        return monthCalendar;
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
            .map(this::toDomain);

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

        entity = toEntity(transaction);

        existing = transactionSpringRepository.findByIndex(transaction.index());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = transactionSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved transaction {}", saved);

        return saved;
    }

    private final Transaction toDomain(final TransactionEntity transaction) {
        return Transaction.builder()
            .withIndex(transaction.getIndex())
            .withDate(transaction.getDate())
            .withDescription(transaction.getDescription())
            .withAmount(transaction.getAmount())
            .build();
    }

    private final TransactionEntity toEntity(final Transaction transaction) {
        return TransactionEntity.builder()
            .withIndex(transaction.index())
            .withDescription(transaction.description())
            .withDate(transaction.date())
            .withAmount(transaction.amount())
            .build();
    }

}

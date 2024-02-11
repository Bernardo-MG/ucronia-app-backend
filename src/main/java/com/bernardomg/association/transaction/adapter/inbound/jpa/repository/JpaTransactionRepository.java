
package com.bernardomg.association.transaction.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.TransactionSpecifications;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonth;
import com.bernardomg.association.transaction.domain.model.TransactionCalendarMonthsRange;
import com.bernardomg.association.transaction.domain.model.TransactionQuery;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaTransactionRepository implements TransactionRepository {

    private final TransactionSpringRepository transactionRepository;

    public JpaTransactionRepository(final TransactionSpringRepository transactionRepo) {
        super();

        transactionRepository = transactionRepo;
    }

    @Override
    public final void delete(final long index) {
        final Optional<TransactionEntity> member;

        log.debug("Deleting transaction {}", index);

        member = transactionRepository.findOneByIndex(index);

        transactionRepository.deleteById(member.get()
            .getId());

        log.debug("Deleted transaction {}", index);
    }

    @Override
    public final boolean exists(final long index) {
        final boolean exists;

        log.debug("Checking if transaction {} exists", index);

        exists = transactionRepository.existsByIndex(index);

        log.debug("Transaction {} exists: {}", index, exists);

        return exists;
    }

    @Override
    public final Iterable<Transaction> findAll(final TransactionQuery query, final Pageable pageable) {
        final Page<TransactionEntity>                    page;
        final Optional<Specification<TransactionEntity>> spec;
        final Iterable<Transaction>                      read;

        log.debug("Finding transactions with sample {} and pagination {}", query, pageable);

        spec = TransactionSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = transactionRepository.findAll(pageable);
        } else {
            page = transactionRepository.findAll(spec.get(), pageable);
        }

        read = page.map(this::toDomain);

        log.debug("Found transactions {}", read);

        return read;
    }

    @Override
    public final TransactionCalendarMonthsRange findDates() {
        final Collection<YearMonth>          months;
        final TransactionCalendarMonthsRange dates;

        log.debug("Finding the transactions range");

        months = transactionRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        dates = TransactionCalendarMonthsRange.builder()
            .withMonths(months)
            .build();

        log.debug("Found the transactions range: {}", dates);

        return dates;
    }

    @Override
    public final TransactionCalendarMonth findInMonth(final YearMonth date) {
        final Specification<TransactionEntity> spec;
        final Collection<TransactionEntity>    read;
        final Collection<Transaction>          transactions;
        final TransactionCalendarMonth         monthCalendar;

        log.debug("Finding all the transactions for the month {}", date);

        spec = TransactionSpecifications.on(date);
        read = transactionRepository.findAll(spec);

        transactions = read.stream()
            .map(this::toDomain)
            .toList();
        monthCalendar = TransactionCalendarMonth.builder()
            .withDate(date)
            .withTransactions(transactions)
            .build();

        log.debug("Found all the transactions for the month {}: {}", date, monthCalendar);

        return monthCalendar;
    }

    @Override
    public final long findNextIndex() {
        final long index;

        log.debug("Finding next index for the transactions");

        index = transactionRepository.findNextIndex();

        log.debug("Found index {}", index);

        return index;
    }

    @Override
    public final Optional<Transaction> findOne(final Long index) {
        final Optional<Transaction> transaction;

        log.debug("Finding transaction with index {}", index);

        transaction = transactionRepository.findOneByIndex(index)
            .map(this::toDomain);

        log.debug("Found transaction with index {}: {}", index, transaction);

        return transaction;
    }

    @Override
    public final Transaction save(final Transaction transaction) {
        final Optional<TransactionEntity> existing;
        final TransactionEntity           entity;
        final TransactionEntity           created;
        final Transaction                 saved;

        log.debug("Saving transaction {}", transaction);

        entity = toEntity(transaction);

        existing = transactionRepository.findOneByIndex(transaction.getIndex());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = transactionRepository.save(entity);

        saved = transactionRepository.findOneByIndex(created.getIndex())
            .map(this::toDomain)
            .get();

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
            .withIndex(transaction.getIndex())
            .withDescription(transaction.getDescription())
            .withDate(transaction.getDate())
            .withAmount(transaction.getAmount())
            .build();
    }

}

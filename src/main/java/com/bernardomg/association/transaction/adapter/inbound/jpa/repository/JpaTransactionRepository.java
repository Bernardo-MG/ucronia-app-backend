
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

        log.debug("Deleting member {}", index);

        member = transactionRepository.findOneByIndex(index);

        transactionRepository.deleteById(member.get()
            .getId());
    }

    @Override
    public final boolean exists(final long index) {
        return transactionRepository.existsByIndex(index);
    }

    @Override
    public final Iterable<Transaction> findAll(final TransactionQuery query, final Pageable pageable) {
        final Page<TransactionEntity>                    page;
        final Optional<Specification<TransactionEntity>> spec;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        spec = TransactionSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = transactionRepository.findAll(pageable);
        } else {
            page = transactionRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDomain);
    }

    @Override
    public final TransactionCalendarMonthsRange findDates() {
        final Collection<YearMonth> months;

        log.debug("Reading the transactions range");

        months = transactionRepository.findMonths()
            .stream()
            .map(m -> YearMonth.of(m.getYear(), m.getMonth()))
            .toList();

        return TransactionCalendarMonthsRange.builder()
            .withMonths(months)
            .build();
    }

    @Override
    public final TransactionCalendarMonth findInMonth(final YearMonth date) {
        final Specification<TransactionEntity> spec;
        final Collection<TransactionEntity>    read;
        final Collection<Transaction>          transactions;

        spec = TransactionSpecifications.on(date);
        read = transactionRepository.findAll(spec);

        transactions = read.stream()
            .map(this::toDomain)
            .toList();
        return TransactionCalendarMonth.builder()
            .withDate(date)
            .withTransactions(transactions)
            .build();
    }

    @Override
    public final long findNextIndex() {
        return transactionRepository.findNextIndex();
    }

    @Override
    public final Optional<Transaction> findOne(final Long index) {
        return transactionRepository.findOneByIndex(index)
            .map(this::toDomain);
    }

    @Override
    public final Transaction save(final Transaction transaction) {
        final Optional<TransactionEntity> existing;
        final TransactionEntity           entity;
        final TransactionEntity           created;

        log.debug("Saving transaction {}", transaction);

        entity = toEntity(transaction);

        existing = transactionRepository.findOneByIndex(transaction.getIndex());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = transactionRepository.save(entity);

        return transactionRepository.findOneByIndex(created.getIndex())
            .map(this::toDomain)
            .get();
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

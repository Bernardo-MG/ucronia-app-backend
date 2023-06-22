
package com.bernardomg.association.transaction.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.transaction.model.ImmutableTransaction;
import com.bernardomg.association.transaction.model.ImmutableTransactionRange;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.request.TransactionCreationQuery;
import com.bernardomg.association.transaction.model.request.TransactionQueryRequest;
import com.bernardomg.association.transaction.model.request.TransactionUpdateQuery;
import com.bernardomg.association.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.transaction.persistence.repository.TransactionSpecifications;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
public final class DefaultTransactionService implements TransactionService {

    private final TransactionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:CREATE')")
    public final Transaction create(final TransactionCreationQuery transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        entity = toEntity(transaction);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:DELETE')")
    public final Boolean delete(final Long id) {
        repository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    public final Iterable<Transaction> getAll(final TransactionQueryRequest request, final Pageable pageable) {
        final Page<PersistentTransaction>                    page;
        final Optional<Specification<PersistentTransaction>> spec;

        spec = TransactionSpecifications.fromRequest(request);

        if (spec.isEmpty()) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    public final Optional<Transaction> getOne(final Long id) {
        final Optional<PersistentTransaction> found;
        final Optional<Transaction>           result;
        final Transaction                     data;

        found = repository.findById(id);

        if (found.isPresent()) {
            data = toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final TransactionRange getRange() {
        final Calendar min;
        final Calendar max;
        final Integer  startMonth;
        final Integer  startYear;
        final Integer  endMonth;
        final Integer  endYear;

        min = repository.findMinDate();
        max = repository.findMaxDate();

        if (min != null) {
            startMonth = min.get(Calendar.MONTH);
            startYear = min.get(Calendar.YEAR);
        } else {
            startMonth = 0;
            startYear = 0;
        }

        if (max != null) {
            endMonth = max.get(Calendar.MONTH);
            endYear = max.get(Calendar.YEAR);
        } else {
            endMonth = 0;
            endYear = 0;
        }

        return ImmutableTransactionRange.builder()
            .startMonth(startMonth)
            .endMonth(endMonth)
            .startYear(startYear)
            .endYear(endYear)
            .build();
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:UPDATE')")
    public final Transaction update(final Long id, final TransactionUpdateQuery transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction updated;

        entity = toEntity(transaction);
        entity.setId(id);

        updated = repository.save(entity);
        return toDto(updated);
    }

    private final Transaction toDto(final PersistentTransaction transaction) {
        return ImmutableTransaction.builder()
            .id(transaction.getId())
            .description(transaction.getDescription())
            .date(transaction.getDate())
            .amount(transaction.getAmount())
            .build();
    }

    private final PersistentTransaction toEntity(final TransactionCreationQuery transaction) {
        return PersistentTransaction.builder()
            .date(transaction.getDate())
            .description(transaction.getDescription())
            .amount(transaction.getAmount())
            .build();
    }

    private final PersistentTransaction toEntity(final TransactionUpdateQuery transaction) {
        return PersistentTransaction.builder()
            .id(transaction.getId())
            .date(transaction.getDate())
            .description(transaction.getDescription())
            .amount(transaction.getAmount())
            .build();
    }

}

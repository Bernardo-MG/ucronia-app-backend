
package com.bernardomg.association.transaction.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.transaction.model.ImmutableTransactionRange;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.mapper.TransactionMapper;
import com.bernardomg.association.transaction.model.request.TransactionCreate;
import com.bernardomg.association.transaction.model.request.TransactionQuery;
import com.bernardomg.association.transaction.model.request.TransactionUpdate;
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

    private final TransactionMapper     mapper;

    private final TransactionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:CREATE')")
    public final Transaction create(final TransactionCreate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        entity = mapper.toEntity(transaction);
        entity.setId(null);

        created = repository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:DELETE')")
    public final Boolean delete(final Long id) {
        repository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    public final Iterable<Transaction> getAll(final TransactionQuery request, final Pageable pageable) {
        final Page<PersistentTransaction>                    page;
        final Optional<Specification<PersistentTransaction>> spec;

        spec = TransactionSpecifications.fromRequest(request);

        if (spec.isEmpty()) {
            page = repository.findAll(pageable);
        } else {
            page = repository.findAll(spec.get(), pageable);
        }

        return page.map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    public final Optional<Transaction> getOne(final Long id) {
        final Optional<PersistentTransaction> found;
        final Optional<Transaction>           result;
        final Transaction                     data;

        found = repository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
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
    public final Transaction update(final Long id, final TransactionUpdate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction updated;

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Failed update. No transaction with id %s", id));
        }

        entity = mapper.toEntity(transaction);
        entity.setId(id);

        updated = repository.save(entity);
        return mapper.toDto(updated);
    }

}

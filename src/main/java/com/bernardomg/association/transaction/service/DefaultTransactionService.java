
package com.bernardomg.association.transaction.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.model.ImmutableTransactionRange;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.request.TransactionCreationQuery;
import com.bernardomg.association.transaction.model.request.TransactionQueryRequest;
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

        startMonth = min.get(Calendar.MONTH);
        startYear = min.get(Calendar.YEAR);

        endMonth = max.get(Calendar.MONTH);
        endYear = max.get(Calendar.YEAR);

        return new ImmutableTransactionRange(startMonth, startYear, endMonth, endYear);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:UPDATE')")
    public final Transaction update(final Long id, final TransactionCreationQuery transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction updated;

        entity = toEntity(transaction);
        entity.setId(id);

        updated = repository.save(entity);
        return toDto(updated);
    }

    private final Transaction toDto(final PersistentTransaction transaction) {
        final DtoTransaction result;

        result = new DtoTransaction();
        result.setId(transaction.getId());
        result.setDescription(transaction.getDescription());
        result.setDate(transaction.getDate());
        result.setAmount(transaction.getAmount());

        return result;
    }

    private final PersistentTransaction toEntity(final TransactionCreationQuery transaction) {
        final PersistentTransaction result;

        result = new PersistentTransaction();
        result.setDescription(transaction.getDescription());
        result.setDate(transaction.getDate());
        result.setAmount(transaction.getAmount());

        return result;
    }

}

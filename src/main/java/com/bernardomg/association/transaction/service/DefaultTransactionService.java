
package com.bernardomg.association.transaction.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.model.ImmutableTransactionRange;
import com.bernardomg.association.transaction.model.PersistentTransaction;
import com.bernardomg.association.transaction.model.QPersistentTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.model.TransactionForm;
import com.bernardomg.association.transaction.model.TransactionRange;
import com.bernardomg.association.transaction.model.TransactionRequest;
import com.bernardomg.association.transaction.repository.TransactionRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultTransactionService implements TransactionService {

    private final TransactionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('CREATE_TRANSACTION')")
    public final Transaction create(final TransactionForm transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        entity = toEntity(transaction);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('DELETE_TRANSACTION')")
    public final Boolean delete(final Long id) {
        Boolean deleted;

        try {
            repository.deleteById(id);
            deleted = true;
        } catch (final EmptyResultDataAccessException e) {
            log.error("Tried to delete id {}, which doesn't exist", id);
            deleted = false;
        }

        return deleted;
    }

    @Override
    @PreAuthorize("hasAuthority('READ_TRANSACTION')")
    public final Iterable<? extends Transaction> getAll(final TransactionRequest request, final Pageable pageable) {
        final QPersistentTransaction      source;
        final Predicate                   finalPredicate;
        final Collection<Predicate>       exprs;
        final Page<PersistentTransaction> page;
        BooleanExpression                 predicate;

        source = QPersistentTransaction.persistentTransaction;

        exprs = new ArrayList<>();
        if (request.getDate() != null) {
            predicate = source.date.eq(request.getDate());
            exprs.add(predicate);
        } else {
            if (request.getStartDate() != null) {
                predicate = source.date.after(request.getStartDate())
                    .or(source.date.eq(request.getStartDate()));
                exprs.add(predicate);
            }
            if (request.getEndDate() != null) {
                predicate = source.date.before(request.getEndDate())
                    .or(source.date.eq(request.getEndDate()));
                exprs.add(predicate);
            }
        }

        if (exprs.isEmpty()) {
            page = repository.findAll(pageable);
        } else {
            finalPredicate = ExpressionUtils.allOf(exprs);
            page = repository.findAll(finalPredicate, pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('READ_TRANSACTION')")
    public final Optional<? extends Transaction> getOne(final Long id) {
        final Optional<PersistentTransaction> found;
        final Optional<? extends Transaction> result;
        final Transaction                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toDto(found.get());
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final TransactionRange getRange() {
        final Calendar min;
        final Calendar max;

        min = repository.findMinDate();
        max = repository.findMaxDate();

        return new ImmutableTransactionRange(min, max);
    }

    @Override
    @PreAuthorize("hasAuthority('UPDATE_TRANSACTION')")
    public final Transaction update(final Long id, final TransactionForm transaction) {
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

    private final PersistentTransaction toEntity(final TransactionForm transaction) {
        final PersistentTransaction result;

        result = new PersistentTransaction();
        result.setDescription(transaction.getDescription());
        result.setDate(transaction.getDate());
        result.setAmount(transaction.getAmount());

        return result;
    }

}

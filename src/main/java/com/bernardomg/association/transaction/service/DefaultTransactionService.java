
package com.bernardomg.association.transaction.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.bernardomg.association.transaction.model.DtoTransaction;
import com.bernardomg.association.transaction.model.PersistentTransaction;
import com.bernardomg.association.transaction.model.Transaction;
import com.bernardomg.association.transaction.repository.TransactionRepository;
import com.bernardomg.association.transaction.validation.TransactionValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 * <p>
 * Applies validation through the included {@link #validator}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultTransactionService implements TransactionService {

    private final TransactionRepository repository;

    private final TransactionValidator  validator;

    @Override
    public final Transaction create(final Transaction transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        validator.validate(transaction);

        entity = toEntity(transaction);
        created = repository.save(entity);
        return toDto(created);
    }

    @Override
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
    public final Iterable<? extends Transaction> getAll(final Transaction sample, final Pageable pageable) {
        final PersistentTransaction       entity;
        final List<? extends Transaction> dtos;
        final Page<PersistentTransaction> read;

        entity = toEntity(sample);

        read = repository.findAll(Example.of(entity), pageable);
        dtos = read.stream()
            .map(this::toDto)
            .collect(Collectors.toList());

        return PageableExecutionUtils.getPage(dtos, pageable, read::getTotalElements);
    }

    @Override
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
    public final Transaction update(final Long id, final Transaction transaction) {
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
        result.setDay(transaction.getDay());
        result.setMonth(transaction.getMonth());
        result.setYear(transaction.getYear());
        result.setQuantity(transaction.getQuantity());

        return result;
    }

    private final PersistentTransaction toEntity(final Transaction transaction) {
        final PersistentTransaction result;

        result = new PersistentTransaction();
        result.setId(transaction.getId());
        result.setDescription(transaction.getDescription());
        result.setDay(transaction.getDay());
        result.setMonth(transaction.getMonth());
        result.setYear(transaction.getYear());
        result.setQuantity(transaction.getQuantity());

        return result;
    }

}

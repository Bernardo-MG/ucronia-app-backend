
package com.bernardomg.association.funds.transaction.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.transaction.exception.MissingTransactionIdException;
import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.TransactionChange;
import com.bernardomg.association.funds.transaction.model.TransactionQuery;
import com.bernardomg.association.persistence.transaction.model.TransactionEntity;
import com.bernardomg.association.persistence.transaction.repository.TransactionRepository;
import com.bernardomg.association.persistence.transaction.specification.TransactionSpecifications;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTransactionService implements TransactionService {

    private final TransactionRepository transactionRepository;

    public DefaultTransactionService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final Transaction create(final TransactionChange transaction) {
        final TransactionEntity entity;
        final TransactionEntity created;
        final Long              index;

        log.debug("Creating transaction {}", transaction);

        entity = toEntity(transaction);

        index = transactionRepository.findNextIndex();
        entity.setIndex(index);

        // Trim strings
        entity.setDescription(entity.getDescription()
            .trim());

        created = transactionRepository.save(entity);
        return toDto(created);
    }

    @Override
    public final void delete(final long index) {

        log.debug("Deleting transaction {}", index);

        if (!transactionRepository.existsByIndex(index)) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        // TODO: Check this deletes on cascade
        transactionRepository.deleteByIndex(index);
    }

    @Override
    public final Iterable<Transaction> getAll(final TransactionQuery transaction, final Pageable pageable) {
        final Page<TransactionEntity>                    page;
        final Optional<Specification<TransactionEntity>> spec;

        log.debug("Reading members with sample {} and pagination {}", transaction, pageable);

        spec = TransactionSpecifications.fromQuery(transaction);

        if (spec.isEmpty()) {
            page = transactionRepository.findAll(pageable);
        } else {
            page = transactionRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDto);
    }

    @Override
    public final Optional<Transaction> getOne(final long index) {
        final Optional<TransactionEntity> found;

        log.debug("Reading member with index {}", index);

        found = transactionRepository.findOneByIndex(index);
        if (found.isEmpty()) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        return found.map(this::toDto);
    }

    @Override
    public final Transaction update(final long index, final TransactionChange transaction) {
        final Optional<TransactionEntity> found;
        final TransactionEntity           entity;
        final TransactionEntity           updated;

        log.debug("Updating transaction with index {} using data {}", index, transaction);

        found = transactionRepository.findOneByIndex(index);
        if (found.isEmpty()) {
            // TODO: change exception name
            throw new MissingTransactionIdException(index);
        }

        entity = toEntity(transaction);
        entity.setIndex(index);
        entity.setId(found.get()
            .getId());

        // Trim strings
        entity.setDescription(entity.getDescription()
            .trim());

        updated = transactionRepository.save(entity);
        return toDto(updated);
    }

    private final Transaction toDto(final TransactionEntity transaction) {
        return Transaction.builder()
            .index(transaction.getIndex())
            .date(transaction.getDate())
            .description(transaction.getDescription())
            .amount(transaction.getAmount())
            .build();
    }

    private final TransactionEntity toEntity(final TransactionChange transaction) {
        return TransactionEntity.builder()
            .description(transaction.getDescription())
            .date(transaction.getDate())
            .amount(transaction.getAmount())
            .build();
    }

}

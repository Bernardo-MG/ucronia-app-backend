
package com.bernardomg.association.funds.transaction.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.transaction.model.Transaction;
import com.bernardomg.association.funds.transaction.model.mapper.TransactionMapper;
import com.bernardomg.association.funds.transaction.model.request.TransactionCreate;
import com.bernardomg.association.funds.transaction.model.request.TransactionQuery;
import com.bernardomg.association.funds.transaction.model.request.TransactionUpdate;
import com.bernardomg.association.funds.transaction.persistence.model.PersistentTransaction;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionSpecifications;
import com.bernardomg.exception.InvalidIdException;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the transaction service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultTransactionService implements TransactionService {

    private static final String         CACHE_MULTIPLE = "transactions";

    private static final String         CACHE_SINGLE   = "transaction";

    private final TransactionMapper     mapper;

    private final TransactionRepository transactionRepository;

    public DefaultTransactionService(final TransactionRepository transactionRepo, final TransactionMapper mpr) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
        mapper = Objects.requireNonNull(mpr);
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Transaction create(final TransactionCreate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        log.debug("Creating transaction {}", transaction);

        entity = mapper.toEntity(transaction);

        // Trim strings
        entity.setDescription(entity.getDescription()
            .trim());

        created = transactionRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        log.debug("Deleting transaction {}", id);

        if (!transactionRepository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

        transactionRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Transaction> getAll(final TransactionQuery request, final Pageable pageable) {
        final Page<PersistentTransaction>                    page;
        final Optional<Specification<PersistentTransaction>> spec;

        log.debug("Reading members with sample {} and pagination {}", request, pageable);

        spec = TransactionSpecifications.fromRequest(request);

        if (spec.isEmpty()) {
            page = transactionRepository.findAll(pageable);
        } else {
            page = transactionRepository.findAll(spec.get(), pageable);
        }

        return page.map(mapper::toDto);
    }

    @Override
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Transaction> getOne(final long id) {
        final Optional<PersistentTransaction> found;
        final Optional<Transaction>           result;
        final Transaction                     data;

        log.debug("Reading member with id {}", id);

        if (!transactionRepository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

        found = transactionRepository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Transaction update(final long id, final TransactionUpdate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction updated;

        log.debug("Updating transactin with id {} using data {}", id, transaction);

        if (!transactionRepository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

        entity = mapper.toEntity(transaction);

        // Set id
        entity.setId(id);

        // Trim strings
        entity.setDescription(entity.getDescription()
            .trim());

        updated = transactionRepository.save(entity);
        return mapper.toDto(updated);
    }

}

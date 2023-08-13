
package com.bernardomg.association.transaction.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import com.bernardomg.exception.InvalidIdException;

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

    private static final String         CACHE_MULTIPLE = "fees";

    private static final String         CACHE_SINGLE   = "fee";

    private final TransactionMapper     mapper;

    private final TransactionRepository repository;

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Transaction create(final TransactionCreate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction created;

        log.debug("Creating transaction {}", transaction);

        entity = mapper.toEntity(transaction);
        entity.setId(null);

        created = repository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:DELETE')")
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        log.debug("Deleting transaction {}", id);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

        repository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Transaction> getAll(final TransactionQuery request, final Pageable pageable) {
        final Page<PersistentTransaction>                    page;
        final Optional<Specification<PersistentTransaction>> spec;

        log.debug("Reading members with sample {} and pagination {}", request, pageable);

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
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Transaction> getOne(final long id) {
        final Optional<PersistentTransaction> found;
        final Optional<Transaction>           result;
        final Transaction                     data;

        log.debug("Reading member with id {}", id);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

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
    @PreAuthorize("hasAuthority('TRANSACTION:READ')")
    public final TransactionRange getRange() {
        final Calendar min;
        final Calendar max;
        final Integer  startMonth;
        final Integer  startYear;
        final Integer  endMonth;
        final Integer  endYear;

        log.debug("Reading the transactions range");

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
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Transaction update(final long id, final TransactionUpdate transaction) {
        final PersistentTransaction entity;
        final PersistentTransaction updated;

        log.debug("Updating transactin with id {} using data {}", id, transaction);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("transaction", id);
        }

        entity = mapper.toEntity(transaction);
        entity.setId(id);

        updated = repository.save(entity);
        return mapper.toDto(updated);
    }

}

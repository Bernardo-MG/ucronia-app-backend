/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTransactionEntityMapper;
import com.bernardomg.association.transaction.domain.model.FeeTransaction;
import com.bernardomg.association.transaction.domain.repository.FeeTransactionRepository;

import jakarta.transaction.Transactional;

@Transactional
public final class JpaFeeTransactionRepository implements FeeTransactionRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                  log = LoggerFactory.getLogger(JpaFeeTransactionRepository.class);

    private final FeeTransactionSpringRepository transactionSpringRepository;

    public JpaFeeTransactionRepository(final FeeTransactionSpringRepository transactionRepo) {
        super();

        transactionSpringRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final long findNextIndex() {
        final long index;

        log.debug("Finding next index for the transactions");

        index = transactionSpringRepository.findNextIndex();

        log.debug("Found index {}", index);

        return index;
    }

    @Override
    public final Optional<FeeTransaction> findOne(final Long index) {
        final Optional<FeeTransaction> transaction;

        log.debug("Finding transaction with index {}", index);

        transaction = transactionSpringRepository.findByIndex(index)
            .map(FeeTransactionEntityMapper::toDomain);

        log.debug("Found transaction with index {}: {}", index, transaction);

        return transaction;
    }

    @Override
    public final FeeTransaction save(final FeeTransaction transaction) {
        final Optional<FeeTransactionEntity> existing;
        final FeeTransactionEntity           entity;
        final FeeTransactionEntity           created;
        final FeeTransaction                 saved;

        log.debug("Saving transaction {}", transaction);

        entity = FeeTransactionEntityMapper.toEntity(transaction);

        existing = transactionSpringRepository.findByIndex(transaction.index());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = transactionSpringRepository.save(entity);
        saved = FeeTransactionEntityMapper.toDomain(created);

        log.debug("Saved transaction {}", saved);

        return saved;
    }

}

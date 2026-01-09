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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeTypeEntityMapper;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaFeeTypeRepository implements FeeTypeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(JpaFeeTypeRepository.class);

    private final FeeTypeSpringRepository feeTypeSpringRepository;

    public JpaFeeTypeRepository(final FeeTypeSpringRepository feeTypeSpringRepo) {
        super();

        feeTypeSpringRepository = Objects.requireNonNull(feeTypeSpringRepo);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting fee type {}", number);

        feeTypeSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee type {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if fee type {} exists", number);

        exists = feeTypeSpringRepository.existsByNumber(number);

        log.debug("Fee type {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<FeeType> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<FeeType>       read;
        final org.springframework.data.domain.Page<FeeTypeEntity> page;
        final Pageable                                            pageable;

        log.debug("Finding all fee type with pagination {} and sorting {}", sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = feeTypeSpringRepository.findAll(pageable);

        read = page.map(FeeTypeEntityMapper::toDomain);

        log.debug("Found fee type with pagination {} and sorting {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<FeeType> findOne(final Long number) {
        final Optional<FeeType> feeType;

        log.debug("Finding fee type with number {}", number);

        feeType = feeTypeSpringRepository.findByNumber(number)
            .map(FeeTypeEntityMapper::toDomain);

        log.debug("Found fee type with number {}: {}", number, feeType);

        return feeType;
    }

    @Override
    public final FeeType save(final FeeType feeType) {
        final Optional<FeeTypeEntity> existing;
        final FeeTypeEntity           entity;
        final FeeTypeEntity           created;
        final FeeType                 saved;
        final Long                    number;

        log.debug("Saving fee type {}", feeType);

        entity = FeeTypeEntityMapper.toEntity(feeType);

        existing = feeTypeSpringRepository.findByNumber(feeType.number());
        if (existing.isPresent()) {
            // TODO: do like sponsor repository
            entity.setId(existing.get()
                .getId());
        } else {
            number = feeTypeSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        created = feeTypeSpringRepository.save(entity);
        saved = FeeTypeEntityMapper.toDomain(created);

        log.debug("Saved fee type {}", saved);

        return saved;
    }

}

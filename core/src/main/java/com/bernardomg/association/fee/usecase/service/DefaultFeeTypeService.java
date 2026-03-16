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

package com.bernardomg.association.fee.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.exception.MissingFeeTypeException;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the fee type service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeTypeService implements FeeTypeService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultFeeTypeService.class);

    private final FeeTypeRepository feeTypeRepository;

    public DefaultFeeTypeService(final FeeTypeRepository feeTypeRepository) {
        super();

        this.feeTypeRepository = Objects.requireNonNull(feeTypeRepository);
    }

    @Override
    public final FeeType create(final FeeType feeType) {
        final FeeType toCreate;
        final FeeType saved;

        log.debug("Creating fee type {}", feeType);

        toCreate = new FeeType(0L, feeType.name(), feeType.amount());

        saved = feeTypeRepository.save(toCreate);

        log.debug("Created fee type {}", saved);

        return saved;
    }

    @Override
    public final FeeType delete(final long number) {
        final FeeType feeType;

        log.debug("Deleting fee type {}", number);

        feeType = feeTypeRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing fee type {}", number);
                throw new MissingFeeTypeException(number);
            });

        // TODO: Check this deletes on cascade
        feeTypeRepository.delete(number);

        log.debug("Deleted fee type {}", number);

        return feeType;
    }

    @Override
    public final Page<FeeType> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<FeeType> feeTypes;

        log.info("Getting all fee types with pagination {} and sorting {}", pagination, sorting);

        feeTypes = feeTypeRepository.findAll(pagination, sorting);

        log.debug("Got all fee types with pagination {} and sorting {}: {}", pagination, sorting, feeTypes);

        return feeTypes;
    }

    @Override
    public final Optional<FeeType> getOne(final long number) {
        final Optional<FeeType> feeType;

        log.debug("Reading fee types with number {}", number);

        feeType = feeTypeRepository.findOne(number);
        if (feeType.isEmpty()) {
            log.error("Missing fee type {}", number);
            throw new MissingFeeTypeException(number);
        }

        log.debug("Read fee types with number {}: {}", number, feeType);

        return feeType;
    }

    @Override
    public final FeeType update(final FeeType feeType) {
        final boolean exists;
        final FeeType toUpdate;
        final FeeType updated;

        log.debug("Updating fee type with number {} using data {}", feeType.number(), feeType);

        exists = feeTypeRepository.exists(feeType.number());
        if (!exists) {
            log.error("Missing fee type {}", feeType.number());
            throw new MissingFeeTypeException(feeType.number());
        }

        toUpdate = new FeeType(feeType.number(), feeType.name(), feeType.amount());

        updated = feeTypeRepository.save(toUpdate);

        log.debug("Updated fee type with number {}: {}", feeType.number(), updated);

        return updated;
    }

}

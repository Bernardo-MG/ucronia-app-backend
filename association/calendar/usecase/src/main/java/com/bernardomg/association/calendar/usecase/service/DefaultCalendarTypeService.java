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

package com.bernardomg.association.calendar.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.calendar.domain.exception.MissingCalendarTypeException;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the calendar type service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultCalendarTypeService implements CalendarTypeService {

    /**
     * Logger for the class.
     */
    private static final Logger          log = LoggerFactory.getLogger(DefaultCalendarTypeService.class);

    private final CalendarTypeRepository calendarTypeRepository;

    public DefaultCalendarTypeService(final CalendarTypeRepository calendarTypeRepo) {
        super();

        calendarTypeRepository = Objects.requireNonNull(calendarTypeRepo);
    }

    @Override
    public final CalendarType create(final CalendarType calendarType) {
        final CalendarType saved;

        log.debug("Creating calendar type {}", calendarType);

        saved = calendarTypeRepository.save(calendarType);

        log.debug("Created calendar type {}", saved);

        return saved;
    }

    @Override
    public final CalendarType delete(final long number) {
        final CalendarType calendarType;

        log.debug("Deleting calendar type {}", number);

        calendarType = calendarTypeRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing calendar type {}", number);
                throw new MissingCalendarTypeException(number);
            });

        calendarTypeRepository.delete(number);

        log.debug("Deleted calendar type {}", number);

        return calendarType;
    }

    @Override
    public final Page<CalendarType> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<CalendarType> calendarTypes;

        log.info("Getting all calendar types with pagination {} and sorting {}", pagination, sorting);

        calendarTypes = calendarTypeRepository.findAll(pagination, sorting);

        log.debug("Got all calendar types with pagination {} and sorting {}: {}", pagination, sorting, calendarTypes);

        return calendarTypes;
    }

    @Override
    public final Optional<CalendarType> getOne(final long number) {
        final Optional<CalendarType> calendarType;

        log.debug("Reading calendar type with number {}", number);

        calendarType = calendarTypeRepository.findOne(number);
        if (calendarType.isEmpty()) {
            log.error("Missing calendar type {}", number);
            throw new MissingCalendarTypeException(number);
        }

        log.debug("Read calendar type with number {}: {}", number, calendarType);

        return calendarType;
    }

    @Override
    public final CalendarType update(final CalendarType calendarType) {
        final boolean      exists;
        final CalendarType updated;

        log.debug("Updating calendar type with number {} using data {}", calendarType.number(), calendarType);

        exists = calendarTypeRepository.exists(calendarType.number());
        if (!exists) {
            log.error("Missing calendar type {}", calendarType.number());
            throw new MissingCalendarTypeException(calendarType.number());
        }

        updated = calendarTypeRepository.save(calendarType);

        log.debug("Updated calendar type with number {}: {}", calendarType.number(), updated);

        return updated;
    }

}

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

import java.util.Optional;

import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

/**
 * CalendarType service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface CalendarTypeService {

    /**
     * Persists the received calendar type.
     *
     * @param calendarType
     *            calendar type to persist
     * @return the persisted calendar type
     */
    public CalendarType create(final CalendarType calendarType);

    /**
     * Deletes the calendar type with the received number.
     *
     * @param number
     *            number of the calendar type to delete
     * @return the deleted calendar type
     */
    public CalendarType delete(final long number);

    /**
     * Returns all the calendar types.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the calendar types
     */
    public Page<CalendarType> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the calendar type for the received number, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the calendar type to acquire
     * @return an {@code Optional} with the calendar type, if it exists, or an empty {@code Optional} otherwise
     */
    public Optional<CalendarType> getOne(final long number);

    /**
     * Updates the calendar type with the received number using the provided data.
     *
     * @param calendarType
     *            calendar type to update
     * @return the updated calendar type
     */
    public CalendarType update(final CalendarType calendarType);

}

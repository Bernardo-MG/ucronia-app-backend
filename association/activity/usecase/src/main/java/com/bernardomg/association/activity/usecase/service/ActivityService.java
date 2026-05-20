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

package com.bernardomg.association.activity.usecase.service;

import java.util.Optional;

import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

/**
 * Activity service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ActivityService {

    /**
     * Persists the received activity.
     *
     * @param activity
     *            activity to persist
     * @return the persisted activity
     */
    public Activity create(final Activity activity);

    /**
     * Deletes the activity with the received id.
     *
     * @param id
     *            id of the activity to delete
     * @return the deleted activity
     */
    public Activity delete(final long id);

    /**
     * Returns all the activities matching the sample. If the sample fields are empty, then all the activities are
     * returned.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the activities matching the sample
     */
    public Page<Activity> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the activity for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the activity to acquire
     * @return an {@code Optional} with the activity, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Activity> getOne(final long id);

    /**
     * Updates the received activity.
     *
     * @param activity
     *            new data for the activity
     * @return the updated activity
     */
    public Activity update(final Activity activity);

}

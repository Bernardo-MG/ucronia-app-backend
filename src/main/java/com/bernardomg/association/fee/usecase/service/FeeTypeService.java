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

import java.util.Optional;

import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Fee type service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeTypeService {

    /**
     * Creates the received fee type.
     *
     * @param feeType
     *            new data for the fee type
     * @return the updated fee type
     */
    public FeeType create(final FeeType feeType);

    /**
     * Deletes the fee type for the received member.
     *
     * @param number
     *            number for the fee type to delete
     * @return deleted fee
     */
    public FeeType delete(final long number);

    /**
     * Returns all the fee types.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the fees matching the sample
     */
    public Page<FeeType> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the fee for the received member in the received date, if it exists. Otherwise an empty {@code Optional}
     * is returned.
     *
     * @param number
     *            number for the fee type to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<FeeType> getOne(final long number);

    /**
     * Updates the received fee type.
     *
     * @param feeType
     *            new data for the fee type
     * @return the updated fee type
     */
    public FeeType update(final FeeType feeType);

}

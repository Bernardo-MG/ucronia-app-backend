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

import java.time.Year;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.dto.FeePayments;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Fee admin service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeService {

    /**
     * Pays fees for a member. This creates the fees for the received months, and registers a payment on the received
     * date.
     *
     * @param date
     *            date of the fee
     * @param number
     *            member paying the fees
     * @return the new unpaid fee
     */
    public Fee createUnpaidFee(final YearMonth date, final Long number);

    /**
     * Deletes the fee for the received member in the received date.
     *
     * @param number
     *            person number for the fee to delete
     * @param date
     *            date of the fee to delete
     * @return deleted fee
     */
    public Fee delete(final long number, final YearMonth date);

    /**
     * Returns all the fees matching the sample. If the sample fields are empty, then all the fees are returned.
     *
     * @param query
     *            sample for filtering
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the fees matching the sample
     */
    public Page<Fee> getAll(final FeeQuery query, final Pagination pagination, final Sorting sorting);

    /**
     * Returns all the member fees for a year.
     *
     * @param year
     *            year to read
     * @param status
     *            member active status
     * @param sorting
     *            sorting to apply
     * @return all the member fees for a year
     */
    public Collection<MemberFees> getForYear(final Year year, final MemberStatus status, final Sorting sorting);

    /**
     * Returns the fee for the received member in the received date, if it exists. Otherwise an empty {@code Optional}
     * is returned.
     *
     * @param memberNumber
     *            member number for the fee to acquire
     * @param date
     *            date of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Fee> getOne(final long memberNumber, final YearMonth date);

    /**
     * Returns the range of available years. These are all the years which have fees assigned, except for any future
     * year.
     *
     * @return the range of available years
     */
    public YearsRange getRange();

    /**
     * Pays fees for a member. This creates the fees for the received months, and registers a payment on the received
     * date.
     * <p>
     * TODO: use a payment model?
     *
     * @param feesPayments
     *            data to create new fees
     * @return all the paid fees
     */
    public Collection<Fee> payFees(final FeePayments feesPayments);

    /**
     * Updates the received fee.
     *
     * @param fee
     *            new data for the fee
     * @return the updated fee
     */
    public Fee update(final Fee fee);

}

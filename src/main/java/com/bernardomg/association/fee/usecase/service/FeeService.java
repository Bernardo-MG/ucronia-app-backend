
package com.bernardomg.association.fee.usecase.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
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
     * @param feeDate
     *            date of the fee
     * @param personNumber
     *            member paying the fees
     * @return the new unpaid fee
     */
    public Fee createUnpaidFee(final YearMonth feeDate, final Long personNumber);

    /**
     * Deletes the fee for the received member in the received date.
     *
     * @param personNumber
     *            person number for the fee to delete
     * @param date
     *            date of the fee to delete
     */
    public void delete(final long personNumber, final YearMonth date);

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
    public Iterable<Fee> getAll(final FeeQuery query, final Pagination pagination, final Sorting sorting);

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
     * Pays fees for a member. This creates the fees for the received months, and registers a payment on the received
     * date.
     *
     * @param feeDates
     *            dates of the fees being paid
     * @param personNumber
     *            member paying the fees
     * @param payDate
     *            date of the payment
     * @return all the paid fees
     */
    public Collection<Fee> payFees(final Collection<YearMonth> feeDates, final Long personNumber,
            final LocalDate payDate);

}

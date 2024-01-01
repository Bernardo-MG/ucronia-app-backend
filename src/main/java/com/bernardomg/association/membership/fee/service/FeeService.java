
package com.bernardomg.association.membership.fee.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.fee.model.Fee;
import com.bernardomg.association.membership.fee.model.FeeChange;
import com.bernardomg.association.membership.fee.model.FeeQuery;

/**
 * Fee service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface FeeService {

    /**
     * Deletes the fee with the received id.
     *
     * @param memberNumber
     *            id of the member for the fee to delete
     * @param date
     *            date of the fee to delete
     */
    public void delete(final long memberNumber, final YearMonth date);

    /**
     * Returns all the fees matching the sample. If the sample fields are empty, then all the fees are returned.
     *
     * @param query
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the fees matching the sample
     */
    public Iterable<Fee> getAll(final FeeQuery query, final Pageable pageable);

    /**
     * Returns the fee for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param memberNumber
     *            id of the member for the fee to acquire
     * @param date
     *            date of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Fee> getOne(final long memberNumber, final YearMonth date);

    /**
     * Pays fees for a member
     *
     * @param memberNumber
     *            member paying the fees
     * @param payDate
     *            payment day
     * @param feeDates
     *            dates for the fees being paid
     * @return all the paid fees
     */
    public Collection<Fee> payFees(final long memberNumber, final LocalDate payDate,
            final Collection<YearMonth> feeDates);

    /**
     * Updates the fee for the received id with the received data.
     *
     * @param memberNumber
     *            id of the member for the fee to acquire
     * @param date
     *            date of the fee to acquire
     * @param fee
     *            new data for the fee
     * @return the updated fee
     */
    public Fee update(final long memberNumber, final YearMonth date, final FeeChange fee);

}

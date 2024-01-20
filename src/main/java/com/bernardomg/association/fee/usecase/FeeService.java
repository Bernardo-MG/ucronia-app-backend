
package com.bernardomg.association.fee.usecase;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePayment;
import com.bernardomg.association.fee.domain.model.FeeQuery;

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
     * @param payment
     *            fees payment
     * @return all the paid fees
     */
    public Collection<Fee> payFees(final FeePayment payment);

}

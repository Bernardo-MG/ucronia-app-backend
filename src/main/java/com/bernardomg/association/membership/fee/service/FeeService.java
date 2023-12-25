
package com.bernardomg.association.membership.fee.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.fee.model.MemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeQuery;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.model.request.FeesPayment;

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
     * @param memberId
     *            id of the member for the fee to delete
     * @param date
     *            date of the fee to delete
     */
    public void delete(final Long memberId, final YearMonth date);

    /**
     * Returns all the fees matching the sample. If the sample fields are empty, then all the fees are returned.
     *
     * @param query
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the fees matching the sample
     */
    public Iterable<MemberFee> getAll(final FeeQuery query, final Pageable pageable);

    /**
     * Returns the fee for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param memberId
     *            id of the member for the fee to acquire
     * @param date
     *            date of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<MemberFee> getOne(final Long memberId, final YearMonth date);

    public Collection<? extends MemberFee> payFees(final FeesPayment fee);

    /**
     * Updates the fee for the received id with the received data.
     *
     * @param id
     *            id of the fee to update
     * @param fee
     *            new data for the fee
     * @return the updated fee
     */
    public MemberFee update(final long id, final FeeUpdate fee);

}

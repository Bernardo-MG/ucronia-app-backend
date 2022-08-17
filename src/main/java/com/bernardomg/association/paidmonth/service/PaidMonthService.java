
package com.bernardomg.association.paidmonth.service;

import java.util.Optional;

import com.bernardomg.association.paidmonth.model.PaidMonth;

/**
 * Paid month service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PaidMonthService {

    /**
     * Persists the received paid month.
     *
     * @param month
     *            paid month to persist
     * @return the persisted paid month
     */
    public PaidMonth create(final PaidMonth month);

    /**
     * Deletes the paid month with the received id.
     *
     * @param id
     *            id of the paid month to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the paid months matching the sample. If the sample fields are empty, then all the paid months are
     * returned.
     *
     * @param sample
     *            sample for filtering
     * @return all the paid months matching the sample
     */
    public Iterable<? extends PaidMonth> getAll(final PaidMonth sample);

    /**
     * Returns the paid month for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the paid month to acquire
     * @return an {@code Optional} with the paid month, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends PaidMonth> getOne(final Long id);

    /**
     * Updates the paid month for the received id with the received data.
     *
     * @param id
     *            id of the paid month to update
     * @param month
     *            new data for the paid month
     * @return the updated paid month
     */
    public PaidMonth update(final Long id, final PaidMonth month);

}


package com.bernardomg.association.payment.service;

import java.util.Optional;

import com.bernardomg.association.payment.model.Payment;

/**
 * Payment service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PaymentService {

    /**
     * Persists the received payment.
     *
     * @param payment
     *            payment to persist
     * @return the persisted payment
     */
    public Payment create(final Payment payment);

    /**
     * Deletes the payment with the received id.
     *
     * @param id
     *            id of the payment to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the payments matching the sample. If the sample fields are empty, then all the payments are returned.
     *
     * @param sample
     *            sample for filtering
     * @return all the payments matching the sample
     */
    public Iterable<Payment> getAll(final Payment sample);

    /**
     * Returns the payment for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the payment to acquire
     * @return an {@code Optional} with the payment, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Payment> getOne(final Long id);

    /**
     * Updates the payment for the received id with the received data.
     *
     * @param id
     *            id of the payment to update
     * @param payment
     *            new data for the payment
     * @return the updated payment
     */
    public Payment update(final Long id, final Payment payment);

}

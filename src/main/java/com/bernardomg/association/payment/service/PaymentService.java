
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

    public Payment create(final Payment payment);

    public Boolean delete(final Long id);

    public Iterable<Payment> getAll(final Payment sample);

    public Optional<? extends Payment> getOne(final Long id);

    public Payment update(final Long id, final Payment payment);

}

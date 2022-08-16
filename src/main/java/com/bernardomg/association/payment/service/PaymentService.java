
package com.bernardomg.association.payment.service;

import com.bernardomg.association.payment.model.Payment;

public interface PaymentService {

    public Payment create(final Payment payment);

    public Boolean delete(final Long id);

    public Iterable<Payment> getAll();

    public Iterable<Payment> getAllForMonth(final Integer month, final Integer year);

    public Payment update(final Long id, final Payment payment);

}

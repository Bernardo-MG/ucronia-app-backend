
package com.bernardomg.association.balance.service;

import com.bernardomg.association.balance.model.Payment;

public interface PaymentService {

    public Payment create(final Payment payment);

    public Boolean delete(final Long id);

    public Iterable<Payment> getAll();

    public Payment update(final Long id, final Payment payment);

}

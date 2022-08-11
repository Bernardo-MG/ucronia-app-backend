
package com.bernardomg.association.balance.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.balance.model.DtoPayment;
import com.bernardomg.association.balance.model.Payment;
import com.bernardomg.association.balance.model.PersistentPayment;
import com.bernardomg.association.balance.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultPaymentService implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public final Payment create(final Payment payment) {
        final PersistentPayment entity;
        final PersistentPayment created;

        entity = toPersistentPayment(payment);
        created = repository.save(entity);
        return toPayment(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<Payment> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toPayment)
            .collect(Collectors.toList());
    }

    @Override
    public final Payment update(final Payment payment) {
        final PersistentPayment entity;
        final PersistentPayment updated;

        entity = toPersistentPayment(payment);
        updated = repository.save(entity);
        return toPayment(updated);
    }

    private final Payment toPayment(final PersistentPayment payment) {
        final DtoPayment result;

        result = new DtoPayment();
        result.setId(payment.getId());
        result.setDescription(payment.getDescription());
        result.setDay(payment.getDay());
        result.setMonth(payment.getMonth());
        result.setYear(payment.getYear());
        result.setQuantity(payment.getQuantity());
        result.setType(payment.getType());

        return result;
    }

    private final PersistentPayment toPersistentPayment(final Payment payment) {
        final PersistentPayment result;

        result = new PersistentPayment();
        result.setId(payment.getId());
        result.setDescription(payment.getDescription());
        result.setDay(payment.getDay());
        result.setMonth(payment.getMonth());
        result.setYear(payment.getYear());
        result.setQuantity(payment.getQuantity());
        result.setType(payment.getType());

        return result;
    }

}

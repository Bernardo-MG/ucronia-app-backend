
package com.bernardomg.association.payment.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bernardomg.association.payment.model.DtoPayment;
import com.bernardomg.association.payment.model.Payment;
import com.bernardomg.association.payment.model.PersistentPayment;
import com.bernardomg.association.payment.repository.PaymentRepository;
import com.bernardomg.association.payment.validation.PaymentValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultPaymentService implements PaymentService {

    private final PaymentRepository repository;

    private final PaymentValidator  validator;

    @Override
    public final Payment create(final Payment payment) {
        final PersistentPayment entity;
        final PersistentPayment created;

        validator.validate(payment);

        entity = toEntity(payment);
        created = repository.save(entity);
        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<Payment> getAll(final Payment sample) {
        final PersistentPayment entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity))
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Payment> getOne(final Long id) {
        final Optional<PersistentPayment> found;
        final Optional<? extends Payment> result;
        final Payment                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toDto(found.get());
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Payment update(final Long id, final Payment payment) {
        final PersistentPayment entity;
        final PersistentPayment updated;

        entity = toEntity(payment);
        entity.setId(id);

        updated = repository.save(entity);
        return toDto(updated);
    }

    private final Payment toDto(final PersistentPayment payment) {
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

    private final PersistentPayment toEntity(final Payment payment) {
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

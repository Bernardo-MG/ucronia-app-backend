
package com.bernardomg.association.member.service;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoPaidMonth;
import com.bernardomg.association.member.model.PaidMonth;
import com.bernardomg.association.member.model.PersistentPaidMonth;
import com.bernardomg.association.member.repository.PaidMonthRepository;

@Service
public final class DefaultPaidMonthService implements PaidMonthService {

    private final PaidMonthRepository repository;

    public DefaultPaidMonthService(final PaidMonthRepository repo) {
        super();

        repository = Objects.requireNonNull(repo, "Received a null pointer as repository");
    }

    @Override
    public final PaidMonth create(final PaidMonth month) {
        final PersistentPaidMonth entity;
        final PersistentPaidMonth created;

        entity = toPersistentPaidMonth(month);
        created = repository.save(entity);
        return toPaidMonth(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends PaidMonth> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toPaidMonth)
            .collect(Collectors.toList());
    }

    @Override
    public final PaidMonth update(final PaidMonth month) {
        final PersistentPaidMonth entity;
        final PersistentPaidMonth updated;

        entity = toPersistentPaidMonth(month);
        updated = repository.save(entity);
        return toPaidMonth(updated);
    }

    private final PaidMonth toPaidMonth(final PersistentPaidMonth entity) {
        final DtoPaidMonth data;

        data = new DtoPaidMonth();
        data.setId(entity.getId());
        data.setMonth(entity.getMonth());
        data.setYear(entity.getYear());

        return data;
    }

    private final PersistentPaidMonth toPersistentPaidMonth(final PaidMonth data) {
        final PersistentPaidMonth entity;

        entity = new PersistentPaidMonth();
        entity.setId(data.getId());
        entity.setMember(data.getMember());
        entity.setMonth(data.getMonth());
        entity.setYear(data.getYear());

        return entity;
    }

}

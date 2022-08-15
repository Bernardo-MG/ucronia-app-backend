
package com.bernardomg.association.memberperiod.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bernardomg.association.memberperiod.model.DtoMemberPeriod;
import com.bernardomg.association.memberperiod.model.MemberPeriod;
import com.bernardomg.association.memberperiod.model.PersistentMemberPeriod;
import com.bernardomg.association.memberperiod.repository.MemberPeriodRepository;
import com.bernardomg.association.memberperiod.validation.MemberPeriodValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultMemberPeriodService implements MemberPeriodService {

    private final MemberPeriodValidator  periodValidator;

    private final MemberPeriodRepository repository;

    @Override
    public final MemberPeriod create(final Long member, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod created;

        // TODO: Check the member id matches the period

        periodValidator.validate(period);

        entity = toPersistentMemberPeriod(period);
        entity.setMember(member);

        created = repository.save(entity);
        return toMemberPeriod(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends MemberPeriod> getAllForMember(final Long member) {
        final Example<PersistentMemberPeriod> example;
        final PersistentMemberPeriod          entity;

        entity = new PersistentMemberPeriod();
        entity.setMember(member);

        example = Example.of(entity);

        // TODO: Sort by date
        return repository.findAll(example)
            .stream()
            .map(this::toMemberPeriod)
            .collect(Collectors.toList());
    }

    @Override
    public final MemberPeriod update(final Long member, final Long id, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod updated;

        // TODO: Check the member id matches the period
        // TODO: Check the id exists

        periodValidator.validate(period);

        entity = toPersistentMemberPeriod(period);
        entity.setId(id);
        entity.setMember(member);

        updated = repository.save(entity);
        return toMemberPeriod(updated);
    }

    private final MemberPeriod toMemberPeriod(final PersistentMemberPeriod entity) {
        final DtoMemberPeriod data;

        data = new DtoMemberPeriod();
        data.setId(entity.getId());
        data.setMember(entity.getMember());
        data.setStartMonth(entity.getStartMonth());
        data.setStartYear(entity.getStartYear());
        data.setEndMonth(entity.getEndMonth());
        data.setEndYear(entity.getEndYear());

        return data;
    }

    private final PersistentMemberPeriod toPersistentMemberPeriod(final MemberPeriod data) {
        final PersistentMemberPeriod entity;

        entity = new PersistentMemberPeriod();
        entity.setId(data.getId());
        entity.setMember(data.getMember());
        entity.setStartMonth(data.getStartMonth());
        entity.setStartYear(data.getStartYear());
        entity.setEndMonth(data.getEndMonth());
        entity.setEndYear(data.getEndYear());

        return entity;
    }

}

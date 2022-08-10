
package com.bernardomg.association.member.service;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMemberPeriod;
import com.bernardomg.association.member.model.MemberPeriod;
import com.bernardomg.association.member.model.PersistentMemberPeriod;
import com.bernardomg.association.member.repository.MemberPeriodRepository;

@Service
public final class DefaultMemberPeriodService implements MemberPeriodService {

    private final MemberPeriodRepository repository;

    public DefaultMemberPeriodService(final MemberPeriodRepository repo) {
        super();

        repository = Objects.requireNonNull(repo, "Received a null pointer as repository");
    }

    @Override
    public MemberPeriod create(final Long member, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod created;

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
    public final Iterable<? extends MemberPeriod> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toMemberPeriod)
            .collect(Collectors.toList());
    }

    @Override
    public final MemberPeriod update(final Long member, final MemberPeriod period) {
        final PersistentMemberPeriod entity;
        final PersistentMemberPeriod updated;

        entity = toPersistentMemberPeriod(period);
        entity.setMember(member);

        updated = repository.save(entity);
        return toMemberPeriod(updated);
    }

    private final MemberPeriod toMemberPeriod(final PersistentMemberPeriod entity) {
        final DtoMemberPeriod data;

        data = new DtoMemberPeriod();
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
        entity.setStartMonth(data.getStartMonth());
        entity.setStartYear(data.getStartYear());
        entity.setEndMonth(data.getEndMonth());
        entity.setEndYear(data.getEndYear());

        return entity;
    }

}


package com.bernardomg.association.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.PersistentMember;
import com.bernardomg.association.member.repository.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultMemberService implements MemberService {

    private final MemberRepository repository;

    @Override
    public final Member create(final Member member) {
        final PersistentMember entity;
        final PersistentMember created;

        entity = toPersistentMember(member);
        created = repository.save(entity);
        return toMember(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends Member> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toMember)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Member> getOne(final Long id) {
        final Optional<PersistentMember> found;
        final Optional<? extends Member> result;
        final Member                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toMember(found.get());
            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Member update(final Long id, final Member member) {
        final PersistentMember entity;
        final PersistentMember updated;

        entity = toPersistentMember(member);
        entity.setId(id);

        // TODO: It is returning the entity BEFORE the changes
        updated = repository.save(entity);
        return toMember(updated);
    }

    private final Member toMember(final PersistentMember entity) {
        final DtoMember data;

        data = new DtoMember();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setActive(entity.getActive());

        return data;
    }

    private final PersistentMember toPersistentMember(final Member data) {
        final PersistentMember entity;

        entity = new PersistentMember();
        entity.setId(data.getId());
        entity.setName(data.getName());
        entity.setActive(data.getActive());

        return entity;
    }

}

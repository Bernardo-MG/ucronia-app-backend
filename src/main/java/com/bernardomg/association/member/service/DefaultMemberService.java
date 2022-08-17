
package com.bernardomg.association.member.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.PersistentMember;
import com.bernardomg.association.member.repository.MemberRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultMemberService implements MemberService {

    /**
     * Member repository.
     */
    private final MemberRepository repository;

    @Override
    public final Member create(final Member member) {
        final PersistentMember entity;
        final PersistentMember created;

        entity = toEntity(member);
        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        Boolean deleted;

        try {
            repository.deleteById(id);
            deleted = true;
        } catch (final EmptyResultDataAccessException e) {
            log.error("Tried to delete id {}, which doesn't exist", id);
            deleted = false;
        }

        return deleted;
    }

    @Override
    public final Iterable<? extends Member> getAll(final Member sample) {
        final PersistentMember entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity))
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends Member> getOne(final Long id) {
        final Optional<PersistentMember> found;
        final Optional<? extends Member> result;
        final Member                     member;

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
    public final Member update(final Long id, final Member member) {
        final PersistentMember entity;
        final PersistentMember updated;

        entity = toEntity(member);
        entity.setId(id);

        updated = repository.save(entity);
        return toDto(updated);
    }

    private final Member toDto(final PersistentMember entity) {
        final DtoMember data;

        data = new DtoMember();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setSurname(entity.getSurname());
        data.setIdentifier(entity.getIdentifier());
        data.setPhone(entity.getPhone());
        data.setActive(entity.getActive());

        return data;
    }

    private final PersistentMember toEntity(final Member data) {
        final PersistentMember entity;

        entity = new PersistentMember();
        entity.setId(data.getId());
        entity.setName(data.getName());
        entity.setSurname(data.getSurname());
        entity.setIdentifier(data.getIdentifier());
        entity.setPhone(data.getPhone());
        entity.setActive(data.getActive());

        return entity;
    }

}

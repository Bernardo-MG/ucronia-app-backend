
package com.bernardomg.association.member.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.mapper.MemberMapper;
import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.persistence.model.PersistentMember;
import com.bernardomg.association.member.persistence.repository.MemberRepository;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
public final class DefaultMemberService implements MemberService {

    private final MemberMapper     mapper;

    /**
     * Member repository.
     */
    private final MemberRepository repository;

    @Override
    @PreAuthorize("hasAuthority('MEMBER:CREATE')")
    public final Member create(final MemberCreate member) {
        final PersistentMember entity;
        final PersistentMember created;

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = mapper.toEntity(member);

        created = repository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:DELETE')")
    public final Boolean delete(final Long id) {
        final Boolean deleted;

        // TODO: Forbid deleting when there are relationships

        if (repository.existsById(id)) {
            repository.deleteById(id);
            deleted = true;
        } else {
            deleted = false;
        }

        return deleted;
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    public final Iterable<Member> getAll(final MemberQuery sample, final Pageable pageable) {
        final PersistentMember entity;

        entity = mapper.toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    public final Optional<Member> getOne(final Long id) {
        final Optional<PersistentMember> found;
        final Optional<Member>           result;
        final Member                     data;

        found = repository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:UPDATE')")
    public final Member update(final Long id, final MemberUpdate member) {
        final PersistentMember entity;
        final PersistentMember updated;

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Failed update. No member with id %s", id));
        }

        entity = mapper.toEntity(member);
        entity.setId(id);

        updated = repository.save(entity);
        return mapper.toDto(updated);
    }

}


package com.bernardomg.association.member.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.ImmutableMember;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.MemberCreationRequest;
import com.bernardomg.association.member.model.request.MemberQueryRequest;
import com.bernardomg.association.member.model.request.MemberUpdateRequest;
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

    /**
     * Member repository.
     */
    private final MemberRepository repository;

    @Override
    @PreAuthorize("hasAuthority('MEMBER:CREATE')")
    public final Member create(final MemberCreationRequest member) {
        final PersistentMember entity;
        final PersistentMember created;

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = toEntity(member);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:DELETE')")
    public final Boolean delete(final Long id) {
        // TODO: Handle deleting related data

        repository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    public final Iterable<Member> getAll(final MemberQueryRequest sample, final Pageable pageable) {
        final PersistentMember entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    public final Optional<Member> getOne(final Long id) {
        final Optional<PersistentMember> found;
        final Optional<Member>           result;
        final Member                     data;

        found = repository.findById(id);

        if (found.isPresent()) {
            data = toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:UPDATE')")
    public final Member update(final Long id, final MemberUpdateRequest member) {
        final PersistentMember entity;
        final PersistentMember updated;

        entity = toEntity(member);
        entity.setId(id);

        updated = repository.save(entity);
        return toDto(updated);
    }

    private final Member toDto(final PersistentMember entity) {
        return ImmutableMember.builder()
            .id(entity.getId())
            .name(entity.getName())
            .surname(entity.getSurname())
            .identifier(entity.getIdentifier())
            .phone(entity.getPhone())
            .active(entity.getActive())
            .build();
    }

    private final PersistentMember toEntity(final MemberCreationRequest data) {
        return PersistentMember.builder()
            .name(data.getName())
            .surname(data.getSurname())
            .identifier(data.getIdentifier())
            .phone(data.getPhone())
            .active(data.getActive())
            .build();
    }

    private final PersistentMember toEntity(final MemberQueryRequest data) {
        return PersistentMember.builder()
            .name(data.getName())
            .surname(data.getSurname())
            .identifier(data.getIdentifier())
            .phone(data.getPhone())
            .active(data.getActive())
            .build();
    }

    private final PersistentMember toEntity(final MemberUpdateRequest data) {
        return PersistentMember.builder()
            .id(data.getId())
            .name(data.getName())
            .surname(data.getSurname())
            .identifier(data.getIdentifier())
            .phone(data.getPhone())
            .active(data.getActive())
            .build();
    }

}


package com.bernardomg.association.membership.member.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.mapper.MemberMapper;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.persistence.model.PersistentMember;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.InvalidIdException;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultMemberService implements MemberService {

    private final MemberMapper     mapper;

    /**
     * Member repository.
     */
    private final MemberRepository memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo, final MemberMapper mppr) {
        super();

        mapper = Objects.requireNonNull(mppr);
        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Member create(final MemberCreate member) {
        final PersistentMember entity;
        final PersistentMember created;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = mapper.toEntity(member);

        // Active by default
        entity.setActive(true);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        created = memberRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    public final void delete(final long id) {

        log.debug("Deleting member {}", id);

        if (!memberRepository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.deleteById(id);
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final PersistentMember entity;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        entity = mapper.toEntity(query);

        switch (query.getStatus()) {
            case ACTIVE:
                entity.setActive(true);
                break;
            case INACTIVE:
                entity.setActive(false);
                break;
            default:
        }

        return memberRepository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<Member> getOne(final long id) {
        final Optional<PersistentMember> found;
        final Optional<Member>           result;
        final Member                     data;

        log.debug("Reading member with id {}", id);

        if (!memberRepository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        found = memberRepository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Member update(final long id, final MemberUpdate member) {
        final PersistentMember entity;
        final PersistentMember updated;

        log.debug("Updating member with id {} using data {}", id, member);

        // TODO: Identificator and phone must be unique or empty

        if (!memberRepository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        entity = mapper.toEntity(member);

        // Set id
        entity.setId(id);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        updated = memberRepository.save(entity);
        return mapper.toDto(updated);
    }

}

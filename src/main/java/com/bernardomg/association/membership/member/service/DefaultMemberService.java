
package com.bernardomg.association.membership.member.service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.model.DtoMember;
import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.mapper.MemberMapper;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.persistence.model.ActiveMemberEntity;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.ActiveMemberRepository;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.MissingIdException;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultMemberService implements MemberService {

    private final ActiveMemberRepository activeMemberRepository;

    private final MemberMapper           mapper;

    /**
     * Member repository.
     */
    private final MemberRepository       memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo, final ActiveMemberRepository activeMemberRepo,
            final MemberMapper mppr) {
        super();

        mapper = Objects.requireNonNull(mppr);
        memberRepository = Objects.requireNonNull(memberRepo);
        activeMemberRepository = Objects.requireNonNull(activeMemberRepo);
    }

    @Override
    public final Member create(final MemberCreate member) {
        final MemberEntity entity;
        final MemberEntity created;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = mapper.toEntity(member);

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
            throw new MissingIdException("member", id);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.deleteById(id);
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Page<MemberEntity>             members;
        final Iterable<Long>                 ids;
        final Collection<ActiveMemberEntity> activeMembers;
        final Function<DtoMember, DtoMember> activeMapper;
        final Map<Long, Boolean>             actives;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        switch (query.getStatus()) {
            case ACTIVE:
                members = memberRepository.findAllActive(pageable);
                activeMapper = (m) -> {
                    m.setActive(true);
                    return m;
                };
                break;
            case INACTIVE:
                members = memberRepository.findAllInactive(pageable);
                activeMapper = (m) -> {
                    m.setActive(false);
                    return m;
                };
                break;
            default:
                members = memberRepository.findAll(pageable);

                ids = members.stream()
                    .map(MemberEntity::getId)
                    .toList();
                activeMembers = activeMemberRepository.findAllById(ids);

                actives = activeMembers.stream()
                    .collect(Collectors.toMap(ActiveMemberEntity::getMemberId, ActiveMemberEntity::getActive));

                activeMapper = (m) -> {
                    final Boolean active;

                    active = actives.getOrDefault(m.getId(), false);
                    m.setActive(active);
                    return m;
                };
        }

        return members.map(mapper::toDto)
            .map(activeMapper);
    }

    @Override
    public final Optional<Member> getOne(final long id) {
        final Optional<MemberEntity>       found;
        final Optional<Member>             result;
        final Optional<ActiveMemberEntity> activeMember;
        final DtoMember                    data;
        final boolean                      active;

        log.debug("Reading member with id {}", id);

        if (!memberRepository.existsById(id)) {
            throw new MissingIdException("member", id);
        }

        found = memberRepository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
            activeMember = activeMemberRepository.findById(data.getId());
            if (activeMember.isPresent()) {
                active = activeMember.get()
                    .getActive();
            } else {
                active = false;
            }
            data.setActive(active);
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Member update(final long id, final MemberUpdate member) {
        final MemberEntity entity;
        final MemberEntity updated;

        log.debug("Updating member with id {} using data {}", id, member);

        // TODO: Identificator and phone must be unique or empty

        if (!memberRepository.existsById(id)) {
            throw new MissingIdException("member", id);
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

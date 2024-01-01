
package com.bernardomg.association.membership.member.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.exception.MissingMemberIdException;
import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.MemberChange;
import com.bernardomg.association.membership.member.model.MemberQuery;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultMemberService implements MemberService {

    /**
     * Member repository.
     */
    private final MemberRepository memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Member create(final MemberChange member) {
        final MemberEntity entity;
        final MemberEntity created;
        final Long         index;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = toEntity(member);

        index = memberRepository.findNextNumber();
        entity.setNumber(index);

        // Trim strings
        entity.setName(StringUtils.trim(entity.getName()));
        entity.setSurname(StringUtils.trim(entity.getSurname()));

        created = memberRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final void delete(final long number) {
        final Optional<MemberEntity> member;

        log.debug("Deleting member {}", number);

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        // TODO: Forbid deleting when there are relationships

        memberRepository.deleteById(member.get()
            .getId());
    }

    @Override
    public final Iterable<Member> getAll(final MemberQuery query, final Pageable pageable) {
        final Page<MemberEntity>       members;
        final YearMonth                validStart;
        final YearMonth                validEnd;
        final Function<Member, Member> activeMapper;
        final Collection<Long>         activeNumbers;

        log.debug("Reading members with sample {} and pagination {}", query, pageable);

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        switch (query.getStatus()) {
            case ACTIVE:
                members = memberRepository.findAllActive(pageable, validStart, validEnd);

                activeMapper = m -> {
                    m.setActive(true);
                    return m;
                };
                break;
            case INACTIVE:
                members = memberRepository.findAllInactive(pageable, validStart, validEnd);

                activeMapper = m -> {
                    m.setActive(false);
                    return m;
                };
                break;
            default:
                members = memberRepository.findAll(pageable);

                activeNumbers = memberRepository.findAllActiveNumbersInRange(validStart, validEnd);
                activeMapper = m -> {
                    final boolean active;

                    active = activeNumbers.contains(m.getNumber());
                    m.setActive(active);
                    return m;
                };
        }

        return members.map(this::toDto)
            .map(activeMapper);
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<MemberEntity> member;

        log.debug("Reading member {}", number);

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        return member.map(this::toDto);
    }

    @Override
    public final Member update(final long number, final MemberChange change) {
        final Optional<MemberEntity> member;
        final MemberEntity           entity;
        final MemberEntity           updated;

        log.debug("Updating member {} using data {}", number, change);

        // TODO: Identificator and phone must be unique or empty

        member = memberRepository.findByNumber(number);
        if (member.isEmpty()) {
            // TODO: change name
            throw new MissingMemberIdException(number);
        }

        entity = toEntity(change);

        // Set id
        entity.setId(member.get()
            .getId());
        entity.setNumber(number);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        updated = memberRepository.save(entity);
        return toDto(updated);
    }

    private final Member toDto(final MemberEntity entity) {
        return Member.builder()
            .number(entity.getNumber())
            .identifier(entity.getIdentifier())
            .name(entity.getName())
            .phone(entity.getPhone())
            .surname(entity.getSurname())
            .build();
    }

    private final MemberEntity toEntity(final MemberChange data) {
        return MemberEntity.builder()
            .identifier(data.getIdentifier())
            .name(data.getName())
            .surname(data.getSurname())
            .phone(data.getPhone())
            .build();
    }

}

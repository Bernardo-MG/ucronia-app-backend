
package com.bernardomg.association.fee.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.infra.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.infra.inbound.jpa.repository.MemberSpringRepository;

import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AssignedFeeActiveMemberRepository implements MemberRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final MemberSpringRepository       memberSpringRepository;

    public AssignedFeeActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo,
            final MemberSpringRepository memberSpringRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final long number) {
        final Optional<MemberEntity> member;

        log.debug("Deleting member {}", number);

        member = memberSpringRepository.findByNumber(number);

        memberSpringRepository.deleteById(member.get()
            .getId());
    }

    @Override
    public final boolean exists(final long number) {
        return memberSpringRepository.existsByNumber(number);
    }

    @Override
    public final Page<Member> findActive(final Pageable pageable) {
        final Page<MemberEntity>       members;
        final Function<Member, Member> activeMapper;
        final YearMonth                validStart;
        final YearMonth                validEnd;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        members = activeMemberRepository.findAllActive(pageable, validStart, validEnd);

        activeMapper = m -> {
            m.setActive(true);
            return m;
        };

        return members.map(this::toDomain)
            .map(activeMapper);
    }

    @Override
    public final Page<Member> findAll(final Pageable pageable) {
        final Page<MemberEntity>       members;
        final Function<Member, Member> activeMapper;
        final YearMonth                validStart;
        final YearMonth                validEnd;
        final Collection<Long>         activeNumbers;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        members = activeMemberRepository.findAll(pageable);

        activeNumbers = activeMemberRepository.findAllActiveNumbersInRange(validStart, validEnd);
        activeMapper = m -> {
            final boolean active;

            active = activeNumbers.contains(m.getNumber());
            m.setActive(active);
            return m;
        };

        return members.map(this::toDomain)
            .map(activeMapper);
    }

    @Override
    public final Page<Member> findInactive(final Pageable pageable) {
        final Page<MemberEntity>       members;
        final Function<Member, Member> activeMapper;
        final YearMonth                validStart;
        final YearMonth                validEnd;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        members = activeMemberRepository.findAllInactive(pageable, validStart, validEnd);

        activeMapper = m -> {
            m.setActive(false);
            return m;
        };

        return members.map(this::toDomain)
            .map(activeMapper);
    }

    @Override
    public final long findNextNumber() {
        return memberSpringRepository.findNextNumber();
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        return memberSpringRepository.findByNumber(number)
            .map(this::toActive);
    }

    @Override
    public final Member save(final Member member) {
        final Optional<MemberEntity> existing;
        final MemberEntity           entity;
        final MemberEntity           created;

        log.debug("Saving member {}", member);

        entity = toEntity(member);

        existing = memberSpringRepository.findByNumber(member.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = activeMemberRepository.save(entity);

        return memberSpringRepository.findByNumber(created.getNumber())
            .map(this::toActive)
            .get();
    }

    private final Member toActive(final MemberEntity member) {
        final boolean   active;
        final YearMonth validStart;
        final YearMonth validEnd;
        final Member    domain;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = activeMemberRepository.isActive(member.getId(), validStart, validEnd);

        domain = toDomain(member);
        domain.setActive(active);
        return domain;
    }

    private final Member toDomain(final MemberEntity entity) {
        final MemberName memberName;
        final String     fullName;

        // TODO: the model should return this automatically
        fullName = Strings.trimWhitespace(entity.getName() + " " + entity.getSurname());
        memberName = MemberName.builder()
            .firstName(entity.getName())
            .lastName(entity.getSurname())
            .fullName(fullName)
            .build();
        return Member.builder()
            .number(entity.getNumber())
            .identifier(entity.getIdentifier())
            .name(memberName)
            .phone(entity.getPhone())
            .build();
    }

    private final MemberEntity toEntity(final Member data) {
        return MemberEntity.builder()
            .number(data.getNumber())
            .identifier(data.getIdentifier())
            .name(data.getName()
                .getFirstName())
            .surname(data.getName()
                .getLastName())
            .phone(data.getPhone())
            .build();
    }

}

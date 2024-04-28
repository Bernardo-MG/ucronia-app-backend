
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;
import com.bernardomg.association.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaAssignedFeeActiveMemberRepository implements MemberRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final MemberSpringRepository       memberSpringRepository;

    public JpaAssignedFeeActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo,
            final MemberSpringRepository memberSpringRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting fee {}", number);

        memberSpringRepository.deleteByNumber(number);

        log.debug("Deleted fee {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if fee {} exists", number);

        exists = memberSpringRepository.existsByNumber(number);

        log.debug("Fee {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Iterable<Member> findActive(final Pageable pageable) {
        final Page<Member> members;
        final YearMonth    validStart;
        final YearMonth    validEnd;

        log.debug("Finding active users");

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        members = activeMemberRepository.findAllActive(pageable, validStart, validEnd)
            .map(m -> toDomain(true, m));

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findAll(final Pageable pageable) {
        final Page<Member>     members;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final Collection<Long> activeNumbers;

        log.debug("Finding all the members");

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        activeNumbers = activeMemberRepository.findAllActiveNumbersInRange(validStart, validEnd);

        members = activeMemberRepository.findAll(pageable)
            .map(m -> toActiveByNumberDomain(activeNumbers, m));

        log.debug("Found all the members: {}", members);

        return members;
    }

    @Override
    public final Iterable<Member> findInactive(final Pageable pageable) {
        final Page<Member> members;
        final YearMonth    validStart;
        final YearMonth    validEnd;

        log.debug("Finding inactive users");

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        members = activeMemberRepository.findAllInactive(pageable, validStart, validEnd)
            .map(m -> toDomain(false, m));

        log.debug("Found active users {}", members);

        return members;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the transactions");

        number = memberSpringRepository.findNextNumber();

        log.debug("Found next number for the transactions: {}", number);

        return number;
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.debug("Finding member with number {}", number);

        member = memberSpringRepository.findByNumber(number)
            .map(this::toActive);

        log.debug("Found member with number {}: {}", number, member);

        return member;
    }

    @Override
    public final Member save(final Member member) {
        final Optional<MemberEntity> existing;
        final MemberEntity           entity;
        final MemberEntity           created;
        final Member                 saved;

        log.debug("Saving member {}", member);

        entity = toEntity(member);

        existing = memberSpringRepository.findByNumber(member.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = activeMemberRepository.save(entity);

        saved = memberSpringRepository.findByNumber(created.getNumber())
            .map(this::toActive)
            .get();

        log.debug("Saved member {}", saved);

        return saved;
    }

    private final Member toActive(final MemberEntity entity) {
        final boolean    active;
        final YearMonth  validStart;
        final YearMonth  validEnd;
        final MemberName memberName;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = activeMemberRepository.isActive(entity.getNumber(), validStart, validEnd);

        memberName = MemberName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .withActive(active)
            .build();
    }

    private final Member toActiveByNumberDomain(final Collection<Long> activeNumbers, final MemberEntity entity) {
        final MemberName memberName;
        final boolean    active;

        active = activeNumbers.contains(entity.getNumber());

        memberName = MemberName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .withActive(active)
            .build();
    }

    private final Member toDomain(final boolean active, final MemberEntity entity) {
        final MemberName memberName;

        memberName = MemberName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .withActive(active)
            .build();
    }

    private final MemberEntity toEntity(final Member data) {
        return MemberEntity.builder()
            .withNumber(data.getNumber())
            .withIdentifier(data.getIdentifier())
            .withName(data.getName()
                .getFirstName())
            .withSurname(data.getName()
                .getLastName())
            .withPhone(data.getPhone())
            .build();
    }

}

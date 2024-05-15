
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
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaAssignedFeeActiveMemberRepository implements MemberRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final MemberSpringRepository       memberSpringRepository;

    private final PersonSpringRepository       personSpringRepository;

    public JpaAssignedFeeActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo,
            final MemberSpringRepository memberSpringRepo, final PersonSpringRepository personSpringRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
        memberSpringRepository = memberSpringRepo;
        personSpringRepository = personSpringRepo;
    }

    @Override
    public final void activate(final Iterable<Long> numbers) {
        final Collection<MemberEntity> members;

        members = memberSpringRepository.findAllByNumber(numbers);
        members.forEach(m -> m.setActive(true));
        memberSpringRepository.saveAll(members);
    }

    @Override
    public final void activate(final long number) {
        final Optional<MemberEntity> read;
        final MemberEntity           member;

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(true);
            memberSpringRepository.save(member);
        }
    }

    @Override
    public final void deactivate(final long number) {
        final Optional<MemberEntity> read;
        final MemberEntity           member;

        read = memberSpringRepository.findByNumber(number);
        if (read.isPresent()) {
            member = read.get();
            member.setActive(false);
            memberSpringRepository.save(member);
        }
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

        log.debug("Finding next number for the members");

        number = personSpringRepository.findNextNumber();

        log.debug("Found next number for the members: {}", number);

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
        final Optional<MemberEntity> existingMember;
        final Optional<PersonEntity> existingPerson;
        final MemberEntity           entity;
        final MemberEntity           created;
        final Member                 saved;

        log.debug("Saving member {}", member);

        entity = toEntity(member);

        existingMember = memberSpringRepository.findByNumber(member.getNumber());
        if (existingMember.isPresent()) {
            entity.setId(existingMember.get()
                .getId());
        }

        existingPerson = personSpringRepository.findByNumber(member.getNumber());
        if (existingPerson.isPresent()) {
            entity.getPerson()
                .setId(existingPerson.get()
                    .getId());
        }

        personSpringRepository.save(entity.getPerson());
        created = activeMemberRepository.save(entity);

        saved = memberSpringRepository.findByNumber(created.getPerson()
            .getNumber())
            .map(this::toActive)
            .get();

        log.debug("Saved member {}", saved);

        return saved;
    }

    private final Member toActive(final MemberEntity entity) {
        final boolean    active;
        final YearMonth  validStart;
        final YearMonth  validEnd;
        final PersonName memberName;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = activeMemberRepository.isActive(entity.getPerson()
            .getNumber(), validStart, validEnd);

        memberName = PersonName.builder()
            .withFirstName(entity.getPerson()
                .getName())
            .withLastName(entity.getPerson()
                .getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getPerson()
                .getNumber())
            .withIdentifier(entity.getPerson()
                .getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPerson()
                .getPhone())
            .withActive(active)
            .build();
    }

    private final Member toActiveByNumberDomain(final Collection<Long> activeNumbers, final MemberEntity entity) {
        final PersonName memberName;
        final boolean    active;

        active = activeNumbers.contains(entity.getPerson()
            .getNumber());

        memberName = PersonName.builder()
            .withFirstName(entity.getPerson()
                .getName())
            .withLastName(entity.getPerson()
                .getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getPerson()
                .getNumber())
            .withIdentifier(entity.getPerson()
                .getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPerson()
                .getPhone())
            .withActive(active)
            .build();
    }

    private final Member toDomain(final boolean active, final MemberEntity entity) {
        final PersonName memberName;

        memberName = PersonName.builder()
            .withFirstName(entity.getPerson()
                .getName())
            .withLastName(entity.getPerson()
                .getSurname())
            .build();
        return Member.builder()
            .withNumber(entity.getPerson()
                .getNumber())
            .withIdentifier(entity.getPerson()
                .getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPerson()
                .getPhone())
            .withActive(active)
            .build();
    }

    private final MemberEntity toEntity(final Member data) {
        final PersonEntity person;

        person = PersonEntity.builder()
            .withNumber(data.getNumber())
            .withIdentifier(data.getIdentifier())
            .withName(data.getName()
                .getFirstName())
            .withSurname(data.getName()
                .getLastName())
            .withPhone(data.getPhone())
            .build();
        return MemberEntity.builder()
            .withPerson(person)
            .withActive(false)
            .build();
    }

}

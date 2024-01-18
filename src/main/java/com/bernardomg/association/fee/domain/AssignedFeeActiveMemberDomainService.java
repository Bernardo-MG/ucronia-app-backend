
package com.bernardomg.association.fee.domain;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.ActiveMemberDomainService;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.MemberName;
import com.bernardomg.association.member.persistence.model.MemberEntity;
import com.bernardomg.association.member.persistence.repository.ActiveMemberRepository;

import io.jsonwebtoken.lang.Strings;

public final class AssignedFeeActiveMemberDomainService implements ActiveMemberDomainService {

    private final ActiveMemberRepository activeMemberRepository;

    public AssignedFeeActiveMemberDomainService(final ActiveMemberRepository activeMemberRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
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

        return members.map(this::toDto)
            .map(activeMapper);
    }

    @Override
    public final Page<Member> findAllActive(final Pageable pageable) {
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

        return members.map(this::toDto)
            .map(activeMapper);
    }

    @Override
    public final Page<Member> findAllInactive(final Pageable pageable) {
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

        return members.map(this::toDto)
            .map(activeMapper);
    }

    @Override
    public final Member findOne(final Long id) {
        final Optional<MemberEntity>   member;
        final Function<Member, Member> setActiveFlag;

        member = activeMemberRepository.findById(id);

        setActiveFlag = (m) -> setActive(id, m);
        // TODO: Check it exists
        return member.map(this::toDto)
            .map(setActiveFlag)
            .get();
    }

    private final Member setActive(final long id, final Member member) {
        final YearMonth validStart;
        final YearMonth validEnd;
        final boolean   active;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        active = activeMemberRepository.isActive(id, validStart, validEnd);

        member.setActive(active);
        return member;
    }

    private final Member toDto(final MemberEntity entity) {
        final MemberName memberName;
        final String     fullName;

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

}

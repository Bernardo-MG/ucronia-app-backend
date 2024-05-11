
package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.PersonName;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.repository.UserSpringRepository;

@Transactional
public final class JpaUserMemberRepository implements UserMemberRepository {

    private final MemberSpringRepository     memberSpringRepository;

    private final UserMemberSpringRepository userMemberSpringRepository;

    private final UserSpringRepository       userSpringRepository;

    public JpaUserMemberRepository(final UserMemberSpringRepository userMemberJpaRepo,
            final UserSpringRepository userSpringRepo, final MemberSpringRepository memberSpringRepo) {
        super();

        userMemberSpringRepository = userMemberJpaRepo;
        userSpringRepository = userSpringRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final String username) {
        final Optional<UserEntity> user;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            userMemberSpringRepository.deleteByUserId(user.get()
                .getId());
        }
    }

    @Override
    public final boolean existsByMemberForAnotherUser(final String username, final long number) {
        return userMemberSpringRepository.existsByNotUsernameAndMemberNumber(username, number);
    }

    @Override
    public final Collection<Member> findAllNotAssigned(final Pageable page) {
        return userMemberSpringRepository.findAllNotAssigned(page)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Member> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserMemberEntity> userMember;
        final Optional<Member>           result;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userMemberSpringRepository.findByUserId(user.get()
                .getId());
            if ((userMember.isPresent()) && (userMember.get()
                .getMember() != null)) {
                result = Optional.of(toDomain(userMember.get()
                    .getMember()));
            } else {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Member save(final String username, final long number) {
        final UserMemberEntity       userMember;
        final Optional<UserEntity>   user;
        final Optional<MemberEntity> member;
        final Member                 result;

        user = userSpringRepository.findOneByUsername(username);
        member = memberSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (member.isPresent())) {
            userMember = UserMemberEntity.builder()
                .withUserId(member.get()
                    .getId())
                .withMember(member.get())
                .withUser(user.get())
                .build();
            userMemberSpringRepository.save(userMember);
            result = toDomain(member.get());
        } else {
            result = null;
        }

        return result;
    }

    private final Member toDomain(final MemberEntity entity) {
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
            .build();
    }

}

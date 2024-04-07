
package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.repository.UserSpringRepository;

import io.jsonwebtoken.lang.Strings;

@Transactional
public final class JpaUserMemberRepository implements UserMemberRepository {

    private final MemberSpringRepository     memberSpringRepository;

    private final UserMemberSpringRepository userMemberJpaRepository;

    private final UserSpringRepository       userSpringRepository;

    public JpaUserMemberRepository(final UserMemberSpringRepository userMemberJpaRepo,
            final UserSpringRepository userSpringRepo, final MemberSpringRepository memberSpringRepo) {
        super();

        userMemberJpaRepository = userMemberJpaRepo;
        userSpringRepository = userSpringRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final String username) {
        final Optional<UserEntity> user;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            userMemberJpaRepository.deleteByUserId(user.get()
                .getId());
        }
    }

    @Override
    public final boolean exists(final String username) {
        final Optional<UserEntity> user;
        final boolean              exists;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            exists = userMemberJpaRepository.existsByUserId(user.get()
                .getId());
        } else {
            exists = false;
        }

        return exists;
    }

    @Override
    public final boolean existsByMember(final long number) {
        return userMemberJpaRepository.existsByMemberNumber(number);
    }

    @Override
    public final Optional<Member> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserMemberEntity> userMember;
        final Optional<Member>           result;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userMemberJpaRepository.findByUserId(user.get()
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
            userMemberJpaRepository.save(userMember);
            result = toDomain(member.get());
        } else {
            result = null;
        }

        return result;
    }

    private final Member toDomain(final MemberEntity entity) {
        final MemberName memberName;
        final String     fullName;

        // TODO: the model should return this automatically
        fullName = Strings.trimWhitespace(entity.getName() + " " + entity.getSurname());
        memberName = MemberName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .withFullName(fullName)
            .build();
        return Member.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .build();
    }

}

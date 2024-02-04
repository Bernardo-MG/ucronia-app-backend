
package com.bernardomg.association.auth.user.adapter.inbound.jpa.repository;

import java.util.Optional;

import com.bernardomg.association.auth.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.repository.UserSpringRepository;

import io.jsonwebtoken.lang.Strings;

public final class JpaUserMemberRepository implements UserMemberRepository {

    public final MemberSpringRepository  memberSpringRepository;

    public final UserMemberJpaRepository userMemberJpaRepository;

    public final UserSpringRepository    userSpringRepository;

    public JpaUserMemberRepository(final UserMemberJpaRepository userMemberJpaRepo,
            final UserSpringRepository userSpringRepo, final MemberSpringRepository memberSpringRepo) {
        super();

        userMemberJpaRepository = userMemberJpaRepo;
        userSpringRepository = userSpringRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserMemberEntity> userMember;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            userMember = userMemberJpaRepository.findByUserId(user.get()
                .getId());
            userMemberJpaRepository.deleteById(userMember.get()
                .getUserId());
        }
    }

    @Override
    public final Optional<UserMember> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<MemberEntity>     member;
        final Optional<UserMemberEntity> userMember;
        final Optional<UserMember>       result;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            userMember = userMemberJpaRepository.findByUserId(user.get()
                .getId());
            member = memberSpringRepository.findById(userMember.get()
                .getMemberId());
            if (member.isPresent()) {
                result = Optional.of(toDto(user.get(), member.get()));
            } else {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final UserMember save(final String username, final Long number) {
        final UserMemberEntity       userMember;
        final Optional<UserEntity>   user;
        final Optional<MemberEntity> member;

        user = userSpringRepository.findOneByUsername(username);
        member = memberSpringRepository.findByNumber(number);
        userMember = UserMemberEntity.builder()
            .withMemberId(member.get()
                .getId())
            .withUserId(user.get()
                .getId())
            .build();
        userMemberJpaRepository.save(userMember);

        return toDto(user.get(), member.get());
    }

    private final UserMember toDto(final UserEntity user, final MemberEntity member) {
        final String fullName;

        // TODO: change model
        fullName = Strings.trimWhitespace(member.getName() + " " + member.getSurname());
        return UserMember.builder()
            .withUsername(user.getUsername())
            .withName(user.getName())
            .withFullName(fullName)
            .withNumber(member.getNumber())
            .build();
    }

}

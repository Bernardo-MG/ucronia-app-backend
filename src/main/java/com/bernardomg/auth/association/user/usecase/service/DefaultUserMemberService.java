
package com.bernardomg.auth.association.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.auth.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.auth.user.adapter.inbound.jpa.repository.UserMemberJpaRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.auth.association.user.domain.model.UserMember;
import com.bernardomg.security.authentication.user.exception.MissingUserUsernameException;
import com.bernardomg.security.authentication.user.persistence.model.UserEntity;
import com.bernardomg.security.authentication.user.persistence.repository.UserRepository;

import io.jsonwebtoken.lang.Strings;

public final class DefaultUserMemberService implements UserMemberService {

    private final MemberSpringRepository  memberRepository;

    private final UserMemberJpaRepository userMemberRepository;

    private final UserRepository          userRepository;

    public DefaultUserMemberService(final UserRepository userRepo, final MemberSpringRepository memberRepo,
            final UserMemberJpaRepository userMemberRepo) {
        super();

        userRepository = userRepo;
        memberRepository = memberRepo;
        userMemberRepository = userMemberRepo;
    }

    @Override
    public final UserMember assignMember(final String username, final long memberNumber) {
        final Optional<UserEntity>   readUser;
        final Optional<MemberEntity> readMember;
        final UserMemberEntity       userMember;

        readUser = userRepository.findOneByUsername(username);
        if (readUser.isEmpty()) {
            throw new MissingUserUsernameException(username);
        }

        readMember = memberRepository.findByNumber(memberNumber);
        if (readMember.isEmpty()) {
            // FIXME: correct name
            throw new MissingMemberIdException(memberNumber);
        }

        userMember = UserMemberEntity.builder()
            .withUserId(readUser.get()
                .getId())
            .withMemberId(readMember.get()
                .getId())
            .build();

        userMemberRepository.save(userMember);

        return toDto(readUser.get(), readMember.get());
    }

    @Override
    public final void deleteMember(final String username) {
        final Optional<UserEntity> readUser;

        readUser = userRepository.findOneByUsername(username);
        if (readUser.isEmpty()) {
            throw new MissingUserUsernameException(username);
        }

        userMemberRepository.deleteById(readUser.get()
            .getId());
    }

    @Override
    public final Optional<UserMember> getMember(final String username) {
        final Optional<UserEntity>       readUser;
        final Optional<MemberEntity>     readMember;
        final Optional<UserMemberEntity> readRelationship;
        final Optional<UserMember>       result;

        readUser = userRepository.findOneByUsername(username);
        if (readUser.isEmpty()) {
            throw new MissingUserUsernameException(username);
        }

        readRelationship = userMemberRepository.findById(readUser.get()
            .getId());
        if (readRelationship.isEmpty()) {
            result = Optional.empty();
        } else {
            readMember = memberRepository.findById(readRelationship.get()
                .getMemberId());

            result = Optional.of(toDto(readUser.get(), readMember.get()));
        }

        return result;
    }

    @Override
    public final UserMember updateMember(final String username, final long memberNumber) {
        final Optional<UserEntity>   readUser;
        final Optional<MemberEntity> readMember;
        final UserMemberEntity       userMember;

        readUser = userRepository.findOneByUsername(username);
        if (readUser.isEmpty()) {
            throw new MissingUserUsernameException(username);
        }

        readMember = memberRepository.findByNumber(memberNumber);
        if (readMember.isEmpty()) {
            // FIXME: correct name
            throw new MissingMemberIdException(memberNumber);
        }

        userMember = UserMemberEntity.builder()
            .withUserId(readUser.get()
                .getId())
            .withMemberId(readMember.get()
                .getId())
            .build();

        userMemberRepository.save(userMember);

        return toDto(readUser.get(), readMember.get());
    }

    private final UserMember toDto(final UserEntity user, final MemberEntity memberEntity) {
        final String fullName;

        fullName = Strings.trimWhitespace(memberEntity.getName() + " " + memberEntity.getSurname());
        return UserMember.builder()
            .withUsername(user.getUsername())
            .withName(user.getName())
            .withFullName(fullName)
            .withNumber(memberEntity.getNumber())
            .build();
    }

}

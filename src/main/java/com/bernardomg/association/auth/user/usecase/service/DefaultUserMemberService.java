
package com.bernardomg.association.auth.user.usecase.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.auth.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.security.authentication.user.domain.exception.MissingUserException;
import com.bernardomg.security.authentication.user.domain.model.User;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;

@Transactional
public final class DefaultUserMemberService implements UserMemberService {

    private final MemberRepository     memberRepository;

    private final UserMemberRepository userMemberRepository;

    private final UserRepository       userRepository;

    public DefaultUserMemberService(final UserRepository userRepo, final MemberRepository memberRepo,
            final UserMemberRepository userMemberRepo) {
        super();

        userRepository = userRepo;
        memberRepository = memberRepo;
        userMemberRepository = userMemberRepo;
    }

    @Override
    public final UserMember assignMember(final String username, final long memberNumber) {
        final Optional<User>   readUser;
        final Optional<Member> readMember;

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        readMember = memberRepository.findOne(memberNumber);
        if (readMember.isEmpty()) {
            // FIXME: correct name
            throw new MissingMemberException(memberNumber);
        }

        return userMemberRepository.save(readUser.get()
            .getUsername(),
            readMember.get()
                .getNumber());
    }

    @Override
    public final void deleteMember(final String username) {
        final boolean exists;

        exists = userRepository.exists(username);
        if (!exists) {
            throw new MissingUserException(username);
        }

        userMemberRepository.delete(username);
    }

    @Override
    public final Optional<Member> getMember(final String username) {
        final Optional<User> readUser;

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        return userMemberRepository.findByUsername(username);
    }

    @Override
    public final UserMember updateMember(final String username, final long memberNumber) {
        final Optional<User>   readUser;
        final Optional<Member> readMember;

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        readMember = memberRepository.findOne(memberNumber);
        if (readMember.isEmpty()) {
            // FIXME: correct name
            throw new MissingMemberException(memberNumber);
        }

        return userMemberRepository.save(readUser.get()
            .getUsername(),
            readMember.get()
                .getNumber());
    }

}

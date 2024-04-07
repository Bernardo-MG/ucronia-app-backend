
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
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
    public final Member assignMember(final String username, final long memberNumber) {
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

        userMemberRepository.save(readUser.get()
            .getUsername(),
            readMember.get()
                .getNumber());

        return readMember.get();
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
    public final Member updateMember(final String username, final long memberNumber) {
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

        userMemberRepository.save(readUser.get()
            .getUsername(),
            readMember.get()
                .getNumber());

        return readMember.get();
    }

}

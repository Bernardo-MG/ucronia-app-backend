
package com.bernardomg.association.security.user.usecase.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.security.user.domain.model.UserMember;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.association.security.user.usecase.validation.AssignMemberValidator;
import com.bernardomg.security.authentication.user.domain.exception.MissingUserException;
import com.bernardomg.security.authentication.user.domain.model.User;
import com.bernardomg.security.authentication.user.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
public final class DefaultUserMemberService implements UserMemberService {

    private final AssignMemberValidator assignMemberValidator;

    private final MemberRepository      memberRepository;

    private final UserMemberRepository  userMemberRepository;

    private final UserRepository        userRepository;

    public DefaultUserMemberService(final UserRepository userRepo, final MemberRepository memberRepo,
            final UserMemberRepository userMemberRepo) {
        super();

        userRepository = userRepo;
        memberRepository = memberRepo;
        userMemberRepository = userMemberRepo;

        assignMemberValidator = new AssignMemberValidator(userMemberRepository);
    }

    @Override
    public final Member assignMember(final String username, final long memberNumber) {
        final Optional<User>   readUser;
        final Optional<Member> readMember;
        final UserMember       userMember;

        log.debug("Assigning member {} to {}", memberNumber, username);

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        readMember = memberRepository.findOne(memberNumber);
        if (readMember.isEmpty()) {
            throw new MissingMemberException(memberNumber);
        }

        userMember = UserMember.builder()
            .withUsername(username)
            .withNumber(memberNumber)
            .build();
        assignMemberValidator.validate(userMember);

        userMemberRepository.save(readUser.get()
            .getUsername(),
            readMember.get()
                .getNumber());

        return readMember.get();
    }

    @Override
    public final Collection<Member> getAvailableMembers(final String username, final Pageable pageable) {
        final boolean exists;

        log.debug("Reading available members for {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            throw new MissingUserException(username);
        }

        return userMemberRepository.findAvailableMembers(username, pageable);
    }

    @Override
    public final Optional<Member> getMember(final String username) {
        final Optional<User> readUser;

        log.debug("Reading member for {}", username);

        readUser = userRepository.findOne(username);
        if (readUser.isEmpty()) {
            throw new MissingUserException(username);
        }

        return userMemberRepository.findByUsername(username);
    }

    @Override
    public final void unassignMember(final String username) {
        final boolean exists;

        log.debug("Unassigning member to {}", username);

        exists = userRepository.exists(username);
        if (!exists) {
            throw new MissingUserException(username);
        }

        userMemberRepository.delete(username);
    }

}

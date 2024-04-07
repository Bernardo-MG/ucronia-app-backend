
package com.bernardomg.association.security.user.usecase.validation;

import java.util.Collection;

import com.bernardomg.association.security.user.domain.model.UserMember;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AssignMemberValidator extends AbstractValidator<UserMember> {

    private final UserMemberRepository userMemberRepository;

    public AssignMemberValidator(final UserMemberRepository userMemberRepo) {
        super();

        userMemberRepository = userMemberRepo;
    }

    @Override
    protected final void checkRules(final UserMember member, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (userMemberRepository.exists(member.getUsername())) {
            log.error("User {} already assigned to a member", member.getUsername());
            failure = FieldFailure.of("username", "existing", member.getUsername());
            failures.add(failure);
        }

        if (userMemberRepository.existsByMember(member.getNumber())) {
            log.error("Member {} already assigned to a user", member.getNumber());
            failure = FieldFailure.of("member", "existing", member.getNumber());
            failures.add(failure);
        }
    }

}

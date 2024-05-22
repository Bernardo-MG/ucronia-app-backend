
package com.bernardomg.association.security.user.usecase.validation;

import java.util.Collection;
import java.util.Objects;

import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AssignPersonValidator extends AbstractValidator<UserPerson> {

    private final UserPersonRepository userPersonRepository;

    public AssignPersonValidator(final UserPersonRepository userPersonRepo) {
        super();

        userPersonRepository = Objects.requireNonNull(userPersonRepo);
    }

    @Override
    protected final void checkRules(final UserPerson member, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        if (userPersonRepository.existsByPersonForAnotherUser(member.getUsername(), member.getNumber())) {
            log.error("Person {} already assigned to a user", member.getNumber());
            failure = FieldFailure.of("person", "existing", member.getNumber());
            failures.add(failure);
        }
    }

}

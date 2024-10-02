
package com.bernardomg.association.security.user.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.security.user.domain.model.UserPerson;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class UserPersonNameNotEmptyRule implements FieldRule<UserPerson> {

    private final UserPersonRepository userPersonRepository;

    public UserPersonNameNotEmptyRule(final UserPersonRepository userPersonRepo) {
        super();

        userPersonRepository = Objects.requireNonNull(userPersonRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final UserPerson member) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (userPersonRepository.existsByPersonForAnotherUser(member.username(), member.number())) {
            log.error("Person {} already assigned to a user", member.number());
            fieldFailure = FieldFailure.of("person", "existing", member.number());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}

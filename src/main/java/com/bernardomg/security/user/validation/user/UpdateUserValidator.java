
package com.bernardomg.security.user.validation.user;

import java.util.Optional;

import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.validation.EmailValidationRule;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateUserValidator extends AbstractValidator<UserUpdate> {

    /**
     * Email validation rule. To check the email fits into the valid email pattern.
     */
    private final ValidationRule<String> emailValidationRule = new EmailValidationRule();

    private final UserRepository         userRepository;

    public UpdateUserValidator(final UserRepository userRepo) {
        super();

        userRepository = userRepo;
    }

    @Override
    protected final void checkRules(final UserUpdate user) {
        final Optional<Failure> optFailure;
        final Boolean           exists;
        FieldFailure            failure;

        // Verify the id exists
        if (!userRepository.existsById(user.getId())) {
            log.error("No user exists for id {}", user.getId());
            // TODO: Is the code exists or is it existing? Make sure all use the same
            failure = FieldFailure.of("id", "notExisting", user.getUsername());
            addFailure(failure);
            exists = false;
        } else {
            exists = true;
        }

        if (exists) {
            // Verify the email is not registered
            if (userRepository.existsByIdNotAndEmail(user.getId(), user.getEmail())) {
                log.error("A user already exists with the username {}", user.getUsername());
                // TODO: Is the code exists or is it existing? Make sure all use the same
                failure = FieldFailure.of("email", "existing", user.getEmail());
                addFailure(failure);
            }

            // Verify the email matches the valid pattern
            optFailure = emailValidationRule.test(user.getEmail());
            if (optFailure.isPresent()) {
                failure = FieldFailure.of(optFailure.get()
                    .getMessage(), "email",
                    optFailure.get()
                        .getCode(),
                    user.getEmail());
                addFailure(failure);
            }

            // Verify the name is not changed
            if (!userRepository.existsByIdAndUsername(user.getId(), user.getUsername())) {
                log.error("Tried to change username for {} with id {}", user.getUsername(), user.getId());
                failure = FieldFailure.of("username", "immutable", user.getId());
                addFailure(failure);
            }
        }
    }

}

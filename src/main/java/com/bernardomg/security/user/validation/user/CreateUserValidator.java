
package com.bernardomg.security.user.validation.user;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.validation.EmailValidationRule;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateUserValidator extends AbstractValidator<UserCreate> {

    /**
     * Email validation rule. To check the email fits into the valid email pattern.
     */
    private final ValidationRule<String> emailValidationRule = new EmailValidationRule();

    private final UserRepository         userRepository;

    public CreateUserValidator(final UserRepository userRepo) {
        super();

        userRepository = userRepo;
    }

    @Override
    protected final void checkRules(final UserCreate user, final Collection<FieldFailure> failures) {
        final Optional<Failure> optFailure;
        FieldFailure            failure;

        // Verify the username is not registered
        if (userRepository.existsByUsername(user.getUsername())) {
            log.error("A user already exists with the username {}", user.getUsername());
            // TODO: Is the code exists or is it existing? Make sure all use the same
            failure = FieldFailure.of("username", "existing", user.getUsername());
            failures.add(failure);
        }

        // TODO: Don't give hints about existing emails
        // Verify the email is not registered
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("A user already exists with the username {}", user.getUsername());
            // TODO: Is the code exists or is it existing? Make sure all use the same
            failure = FieldFailure.of("email", "existing", user.getEmail());
            failures.add(failure);
        }

        // Verify the email matches the valid pattern
        optFailure = emailValidationRule.test(user.getEmail());
        if (optFailure.isPresent()) {
            log.error("Received invalid email {}", user.getEmail());
            failure = FieldFailure.of(optFailure.get()
                .getMessage(), "email",
                optFailure.get()
                    .getCode(),
                user.getEmail());
            failures.add(failure);
        }
    }

}

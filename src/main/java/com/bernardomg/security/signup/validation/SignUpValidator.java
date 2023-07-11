
package com.bernardomg.security.signup.validation;

import java.util.Collection;

import com.bernardomg.security.signup.model.SignUp;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SignUpValidator extends AbstractValidator<SignUp> {

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    public SignUpValidator(@NonNull final UserRepository repo) {
        super();

        userRepository = repo;
    }

    @Override
    protected final void checkRules(final SignUp signUp, final Collection<FieldFailure> failures) {
        FieldFailure error;

        // Verify no user exists with the received username
        if (userRepository.existsByUsername(signUp.getUsername()
            .toLowerCase())) {
            log.error("A user already exists with the username {}", signUp.getUsername());
            // TODO: The code is exists or is it existing? Make sure all use the same
            error = FieldFailure.of("username", "existing", signUp.getUsername());
            failures.add(error);
        }

        // Verify no user exists with the received email
        if (userRepository.existsByEmail(signUp.getEmail()
            .toLowerCase())) {
            log.error("A user already exists with the email {}", signUp.getEmail());
            // TODO: The code is exists or is it existing? Make sure all use the same
            error = FieldFailure.of("email", "existing", signUp.getEmail());
            failures.add(error);
        }
    }

}

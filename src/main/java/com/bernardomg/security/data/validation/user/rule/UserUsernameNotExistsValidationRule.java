
package com.bernardomg.security.data.validation.user.rule;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserUsernameNotExistsValidationRule implements ValidationRule<User> {

    private final UserRepository repository;

    @Override
    public final Optional<Failure> test(final User user) {
        final Failure           error;
        final PersistentUser    sample;
        final Optional<Failure> result;

        sample = new PersistentUser();
        sample.setUsername(user.getUsername());

        if (repository.exists(Example.of(sample))) {
            log.error("A user already exists with the username {}", user.getUsername());
            error = FieldFailure.of("error.username.existing", "memberId", "existing", user.getUsername());
            result = Optional.of(error);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}

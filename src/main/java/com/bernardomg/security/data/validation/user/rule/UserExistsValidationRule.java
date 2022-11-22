
package com.bernardomg.security.data.validation.user.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserExistsValidationRule implements ValidationRule<User> {

    private final UserRepository repository;

    @Override
    public final Optional<Failure> test(final User user) {
        final Failure           error;
        final Optional<Failure> result;

        if (!repository.existsById(user.getId())) {
            log.error("Found no role with id {}", user.getId());
            error = FieldFailure.of("error.id.notExisting", "id", user.getId());
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

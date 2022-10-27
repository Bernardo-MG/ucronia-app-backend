
package com.bernardomg.security.validation.user.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserUsernameExistsValidationRule implements ValidationRule<User> {

    private final UserRepository repository;

    @Override
    public final Collection<Failure> test(final User user) {
        final Collection<Failure> result;
        final Failure             error;
        final PersistentUser      sample;

        sample = new PersistentUser();
        sample.setUsername(user.getUsername());

        result = new ArrayList<>();
        if (!repository.exists(Example.of(sample))) {
            log.error("A user already exists with the username {}", user.getUsername());
            error = FieldFailure.of("error.username.notExisting", "roleForm", "memberId", user.getUsername());
            result.add(error);
        }

        return result;
    }

    @Override
    public final String toString() {
        return this.getClass()
            .getName();
    }

}

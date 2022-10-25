
package com.bernardomg.security.validation.user.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserExistsValidationRule implements ValidationRule<User> {

    private final UserRepository repository;

    @Override
    public final Collection<Failure> test(final User user) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (!repository.existsById(user.getId())) {
            log.error("Found no role with id {}", user.getId());
            error = FieldFailure.of("error.id.notExisting", "roleForm", "id", user.getId());
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

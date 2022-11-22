
package com.bernardomg.security.data.validation.role.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class RoleExistsValidationRule implements ValidationRule<Role> {

    private final RoleRepository repository;

    @Override
    public final Optional<Failure> test(final Role role) {
        final Failure           error;
        final Optional<Failure> result;

        if (!repository.existsById(role.getId())) {
            log.error("Found no role with id {}", role.getId());
            error = FieldFailure.of("error.id.notExisting", "memberId", "notExisting", role.getId());
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

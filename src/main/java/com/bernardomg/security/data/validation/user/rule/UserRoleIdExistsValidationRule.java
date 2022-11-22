
package com.bernardomg.security.data.validation.user.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserRoleIdExistsValidationRule implements ValidationRule<Long> {

    private final RoleRepository repository;

    @Override
    public final Optional<Failure> test(final Long id) {
        final Failure           error;
        final Optional<Failure> result;

        if (!repository.existsById(id)) {
            log.error("Found no role with id {}", id);
            error = FieldFailure.of("error.role.notExisting", "memberId", id);
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

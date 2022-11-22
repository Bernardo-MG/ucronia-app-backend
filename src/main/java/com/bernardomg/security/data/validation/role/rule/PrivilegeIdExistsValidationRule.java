
package com.bernardomg.security.data.validation.role.rule;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class PrivilegeIdExistsValidationRule implements ValidationRule<Long> {

    private final PrivilegeRepository repository;

    @Override
    public final Optional<Failure> test(final Long id) {
        final Failure           error;
        final Optional<Failure> result;

        if (!repository.existsById(id)) {
            log.error("Found no privilege with id {}", id);
            error = FieldFailure.of("error.privilege.id.notExisting", "memberId", "notExisting", id);
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

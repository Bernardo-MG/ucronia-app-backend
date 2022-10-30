
package com.bernardomg.security.data.validation.user.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class UserRoleIdExistsValidationRule implements ValidationRule<Long> {

    private final RoleRepository repository;

    @Override
    public final Collection<Failure> test(final Long id) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (!repository.existsById(id)) {
            log.error("Found no role with id {}", id);
            error = FieldFailure.of("error.role.notExisting", "roleForm", "memberId", id);
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

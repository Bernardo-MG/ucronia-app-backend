
package com.bernardomg.security.data.validation.role.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class RoleExistsValidationRule implements ValidationRule<Role> {

    private final RoleRepository repository;

    @Override
    public final Collection<Failure> test(final Role role) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (!repository.existsById(role.getId())) {
            log.error("Found no role with id {}", role.getId());
            error = FieldFailure.of("error.id.notExisting", "roleForm", "memberId", role.getId());
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

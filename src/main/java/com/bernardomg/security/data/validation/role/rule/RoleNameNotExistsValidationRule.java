
package com.bernardomg.security.data.validation.role.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class RoleNameNotExistsValidationRule implements ValidationRule<Role> {

    private final RoleRepository repository;

    @Override
    public final Collection<Failure> test(final Role role) {
        final Collection<Failure> result;
        final Failure             error;
        final PersistentRole      sample;

        sample = new PersistentRole();
        sample.setName(role.getName());

        result = new ArrayList<>();
        if (repository.exists(Example.of(sample))) {
            log.error("A role already exists with the name {}", role.getName());
            error = FieldFailure.of("error.name.existing", "roleForm", "memberId", role.getName());
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

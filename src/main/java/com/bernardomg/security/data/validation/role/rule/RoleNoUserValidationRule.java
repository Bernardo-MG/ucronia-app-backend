
package com.bernardomg.security.data.validation.role.rule;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class RoleNoUserValidationRule implements ValidationRule<Role> {

    private final UserRolesRepository repository;

    @Override
    public final Optional<Failure> test(final Role role) {
        final Failure             error;
        final PersistentUserRoles sample;
        final Optional<Failure>   result;

        sample = new PersistentUserRoles();
        sample.setRoleId(role.getId());

        if (repository.exists(Example.of(sample))) {
            log.error("Role with id {} has a relationship with a user", role.getId());
            error = FieldFailure.of("error.user.existing", "memberId", role.getId());
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

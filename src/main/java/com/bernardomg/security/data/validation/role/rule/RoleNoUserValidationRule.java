
package com.bernardomg.security.data.validation.role.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class RoleNoUserValidationRule implements ValidationRule<Role> {

    private final UserRolesRepository repository;

    @Override
    public final Collection<Failure> test(final Role role) {
        final Collection<Failure> result;
        final Failure             error;
        final PersistentUserRoles sample;

        sample = new PersistentUserRoles();
        sample.setRoleId(role.getId());

        result = new ArrayList<>();
        if (repository.exists(Example.of(sample))) {
            log.error("Role with id {} has a relationship with a user", role.getId());
            error = FieldFailure.of("error.user.existing", "roleForm", "memberId", role.getId());
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

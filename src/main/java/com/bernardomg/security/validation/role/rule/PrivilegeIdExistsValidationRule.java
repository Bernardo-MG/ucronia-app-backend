
package com.bernardomg.security.validation.role.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.persistence.repository.PrivilegeRepository;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public final class PrivilegeIdExistsValidationRule implements ValidationRule<Long> {

    private final PrivilegeRepository repository;

    @Override
    public final Collection<Failure> test(final Long id) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (!repository.existsById(id)) {
            log.error("Found no privilege with id {}", id);
            error = FieldFailure.of("error.privilege.id.notExisting", "roleForm", "memberId", id);
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

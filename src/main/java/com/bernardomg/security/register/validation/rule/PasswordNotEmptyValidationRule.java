
package com.bernardomg.security.register.validation.rule;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.validation.ValidationRule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public final class PasswordNotEmptyValidationRule implements ValidationRule<String> {

    @Override
    public final Collection<Failure> test(final String password) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();
        if (Strings.isEmpty(password)) {
            error = FieldFailure.of("error.password.invalid", "roleForm", "id", password);
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

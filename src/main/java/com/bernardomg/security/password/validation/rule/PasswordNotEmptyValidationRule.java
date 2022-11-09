
package com.bernardomg.security.password.validation.rule;

import java.util.Optional;

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
    public final Optional<Failure> test(final String password) {
        final Failure           error;
        final Optional<Failure> result;

        if (Strings.isEmpty(password)) {
            error = FieldFailure.of("error.password.invalid", "roleForm", "id", password);
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

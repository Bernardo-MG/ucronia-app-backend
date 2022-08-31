
package com.bernardomg.validation.error;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.bernardomg.validation.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RuleValidator<T> implements Validator<T> {

    private final Collection<ValidationRule<T>> rules;

    public RuleValidator(final Collection<ValidationRule<T>> rls) {
        super();

        this.rules = rls;
    }

    public RuleValidator(final ValidationRule<T> rule) {
        super();

        this.rules = Arrays.asList(rule);
    }

    @Override
    public final void validate(final T obj) {
        final Collection<ValidationFailure> errors;

        errors = rules.stream()
            .map((r) -> r.test(obj))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        log.debug("Applied rules: {}", rules);

        if (!errors.isEmpty()) {
            log.debug("Got errors: {}", errors);
            throw new ValidationException(errors);
        }

        log.debug("No errors");
    }

}

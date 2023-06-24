
package com.bernardomg.validation;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractValidator<T> implements Validator<T> {

    private final Collection<FieldFailure> failures = new ArrayList<>();

    @Override
    public final void validate(final T obj) {
        checkRules(obj);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

    protected final void addFailure(final FieldFailure failure) {
        failures.add(failure);
    }

    protected abstract void checkRules(final T obj);

}

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.validation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.exception.FailureException;

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
        final Collection<Failure> errors;

        errors = rules.stream()
            .peek(r -> log.debug("Applying validation rule {}", r))
            .map((r) -> r.test(obj))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

        log.debug("Applied rules: {}", rules);

        if (!errors.isEmpty()) {
            log.debug("Got errors: {}", errors);
            throw new FailureException(errors);
        }

        log.debug("No errors");
    }

}


package com.bernardomg.association.library.publisher.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class PublisherNameNotEmptyRule implements FieldRule<Publisher> {

    public PublisherNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Publisher publisher) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(publisher.name())) {
            log.error("Empty publisher name");
            fieldFailure = FieldFailure.of("name", "empty", publisher.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}

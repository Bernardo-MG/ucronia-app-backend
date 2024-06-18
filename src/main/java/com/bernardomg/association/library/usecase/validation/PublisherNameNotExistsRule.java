
package com.bernardomg.association.library.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class PublisherNameNotExistsRule implements FieldRule<Publisher> {

    private final PublisherRepository publisherRepository;

    public PublisherNameNotExistsRule(final PublisherRepository publisherRepo) {
        super();

        publisherRepository = Objects.requireNonNull(publisherRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Publisher publisher) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(publisher.getName())) && (publisherRepository.exists(publisher.getName()))) {
            log.error("Existing name {}", publisher.getName());
            fieldFailure = FieldFailure.of("name", "existing", publisher.getName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}

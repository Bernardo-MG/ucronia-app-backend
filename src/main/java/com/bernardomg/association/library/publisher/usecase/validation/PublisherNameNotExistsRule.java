
package com.bernardomg.association.library.publisher.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the donor has a name.
 */
public final class PublisherNameNotExistsRule implements FieldRule<Publisher> {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(PublisherNameNotExistsRule.class);

    private final PublisherRepository publisherRepository;

    public PublisherNameNotExistsRule(final PublisherRepository publisherRepo) {
        super();

        publisherRepository = Objects.requireNonNull(publisherRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Publisher publisher) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(publisher.name())) && (publisherRepository.existsByName(publisher.name()))) {
            log.error("Existing publisher name {}", publisher.name());
            fieldFailure = new FieldFailure("existing", "name", publisher.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}

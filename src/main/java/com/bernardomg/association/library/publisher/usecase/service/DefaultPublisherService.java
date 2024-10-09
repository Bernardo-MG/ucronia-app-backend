
package com.bernardomg.association.library.publisher.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.exception.PublisherHasRelationshipsException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.usecase.validation.PublisherNameNotEmptyRule;
import com.bernardomg.association.library.publisher.usecase.validation.PublisherNameNotExistsRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultPublisherService implements PublisherService {

    private final Validator<Publisher> createPublisherValidator;

    private final PublisherRepository  publisherRepository;

    public DefaultPublisherService(final PublisherRepository publisherRepo) {
        super();

        publisherRepository = Objects.requireNonNull(publisherRepo);

        createPublisherValidator = new FieldRuleValidator<>(new PublisherNameNotEmptyRule(),
            new PublisherNameNotExistsRule(publisherRepository));
    }

    @Override
    public final Publisher create(final Publisher publisher) {
        final Publisher toCreate;
        final Long      number;

        log.debug("Creating publisher {}", publisher);

        // Set number
        number = publisherRepository.findNextNumber();

        toCreate = new Publisher(number, publisher.name());

        createPublisherValidator.validate(toCreate);

        return publisherRepository.save(toCreate);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting author {}", name);

        if (!publisherRepository.exists(name)) {
            throw new MissingPublisherException(name);
        }

        if (publisherRepository.hasRelationships(name)) {
            throw new PublisherHasRelationshipsException(name);
        }

        publisherRepository.delete(name);
    }

    @Override
    public final Iterable<Publisher> getAll(final Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    @Override
    public final Optional<Publisher> getOne(final String name) {
        final Optional<Publisher> publisher;

        log.debug("Reading author {}", name);

        publisher = publisherRepository.findOne(name);
        if (publisher.isEmpty()) {
            log.error("Missing publisher {}", name);
            throw new MissingPublisherException(name);
        }

        return publisher;
    }

}

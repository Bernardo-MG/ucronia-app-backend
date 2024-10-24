
package com.bernardomg.association.library.publisher.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.publisher.domain.exception.MissingPublisherException;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.usecase.validation.PublisherNameNotEmptyRule;
import com.bernardomg.association.library.publisher.usecase.validation.PublisherNameNotExistsForAnotherRule;
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

    private final Validator<Publisher> updatePublisherValidator;

    public DefaultPublisherService(final PublisherRepository publisherRepo) {
        super();

        publisherRepository = Objects.requireNonNull(publisherRepo);

        createPublisherValidator = new FieldRuleValidator<>(new PublisherNameNotEmptyRule(),
            new PublisherNameNotExistsRule(publisherRepository));
        updatePublisherValidator = new FieldRuleValidator<>(new PublisherNameNotEmptyRule(),
            new PublisherNameNotExistsForAnotherRule(publisherRepository));
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
    public final void delete(final long number) {

        log.debug("Deleting publisher {}", number);

        if (!publisherRepository.exists(number)) {
            throw new MissingPublisherException(number);
        }

        publisherRepository.delete(number);
    }

    @Override
    public final Iterable<Publisher> getAll(final Pageable pageable) {
        log.debug("Reading publishers with pagination {}", pageable);

        return publisherRepository.findAll(pageable);
    }

    @Override
    public final Optional<Publisher> getOne(final long number) {
        final Optional<Publisher> publisher;

        log.debug("Reading publisher {}", number);

        publisher = publisherRepository.findOne(number);
        if (publisher.isEmpty()) {
            log.error("Missing publisher {}", number);
            throw new MissingPublisherException(number);
        }

        return publisher;
    }

    @Override
    public final Publisher update(final Publisher publisher) {
        log.debug("Updating publisher {}", publisher);

        if (!publisherRepository.exists(publisher.number())) {
            throw new MissingPublisherException(publisher.number());
        }

        // Set number
        updatePublisherValidator.validate(publisher);

        return publisherRepository.save(publisher);
    }

}

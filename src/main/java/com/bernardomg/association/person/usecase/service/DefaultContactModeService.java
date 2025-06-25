
package com.bernardomg.association.person.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingContactModeException;
import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.association.person.domain.repository.ContactModeRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the ContactMode service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactModeService implements ContactModeService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultContactModeService.class);

    private final ContactModeRepository contactModeRepository;

    public DefaultContactModeService(final ContactModeRepository contactModeRepo) {
        super();

        contactModeRepository = Objects.requireNonNull(contactModeRepo);
    }

    @Override
    public final ContactMode create(final ContactMode ContactMode) {
        final ContactMode toCreate;
        final Long        number;

        log.debug("Creating ContactMode {}", ContactMode);

        // Set number
        number = contactModeRepository.findNextNumber();

        toCreate = new ContactMode(number, ContactMode.name());

        return contactModeRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting ContactMode {}", number);

        if (!contactModeRepository.exists(number)) {
            log.error("Missing ContactMode {}", number);
            throw new MissingContactModeException(number);
        }

        contactModeRepository.delete(number);
    }

    @Override
    public final Iterable<ContactMode> getAll(final Pagination pagination, final Sorting sorting) {
        log.debug("Reading ContactModes with pagination {} and sorting {}", pagination, sorting);

        return contactModeRepository.findAll(pagination, sorting);
    }

    @Override
    public final Optional<ContactMode> getOne(final long number) {
        final Optional<ContactMode> ContactMode;

        log.debug("Reading ContactMode {}", number);

        ContactMode = contactModeRepository.findOne(number);
        if (ContactMode.isEmpty()) {
            log.error("Missing ContactMode {}", number);
            throw new MissingContactModeException(number);
        }

        return ContactMode;
    }

    @Override
    public final ContactMode update(final ContactMode ContactMode) {
        log.debug("Updating ContactMode {} using data {}", ContactMode.number(), ContactMode);

        // TODO: Identificator and phone must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!contactModeRepository.exists(ContactMode.number())) {
            log.error("Missing ContactMode {}", ContactMode.number());
            throw new MissingContactModeException(ContactMode.number());
        }

        return contactModeRepository.save(ContactMode);
    }

}

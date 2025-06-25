
package com.bernardomg.association.person.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingContactMethodException;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the ContactMethod service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactMethodService implements ContactMethodService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultContactMethodService.class);

    private final ContactMethodRepository ContactMethodRepository;

    public DefaultContactMethodService(final ContactMethodRepository ContactMethodRepo) {
        super();

        ContactMethodRepository = Objects.requireNonNull(ContactMethodRepo);
    }

    @Override
    public final ContactMethod create(final ContactMethod ContactMethod) {
        final ContactMethod toCreate;
        final Long          number;

        log.debug("Creating ContactMethod {}", ContactMethod);

        // Set number
        number = ContactMethodRepository.findNextNumber();

        toCreate = new ContactMethod(number, ContactMethod.name());

        return ContactMethodRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting ContactMethod {}", number);

        if (!ContactMethodRepository.exists(number)) {
            log.error("Missing ContactMethod {}", number);
            throw new MissingContactMethodException(number);
        }

        ContactMethodRepository.delete(number);
    }

    @Override
    public final Iterable<ContactMethod> getAll(final Pagination pagination, final Sorting sorting) {
        log.debug("Reading ContactMethods with pagination {} and sorting {}", pagination, sorting);

        return ContactMethodRepository.findAll(pagination, sorting);
    }

    @Override
    public final Optional<ContactMethod> getOne(final long number) {
        final Optional<ContactMethod> ContactMethod;

        log.debug("Reading ContactMethod {}", number);

        ContactMethod = ContactMethodRepository.findOne(number);
        if (ContactMethod.isEmpty()) {
            log.error("Missing ContactMethod {}", number);
            throw new MissingContactMethodException(number);
        }

        return ContactMethod;
    }

    @Override
    public final ContactMethod update(final ContactMethod ContactMethod) {
        log.debug("Updating ContactMethod {} using data {}", ContactMethod.number(), ContactMethod);

        // TODO: Identificator and phone must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!ContactMethodRepository.exists(ContactMethod.number())) {
            log.error("Missing ContactMethod {}", ContactMethod.number());
            throw new MissingContactMethodException(ContactMethod.number());
        }

        return ContactMethodRepository.save(ContactMethod);
    }

}

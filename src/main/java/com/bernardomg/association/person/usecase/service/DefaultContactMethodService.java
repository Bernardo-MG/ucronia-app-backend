
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
import com.bernardomg.association.person.usecase.validation.ContactMethodNotEmptyRule;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

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
    private static final Logger            log = LoggerFactory.getLogger(DefaultContactMethodService.class);

    private final ContactMethodRepository  contactMethodRepository;

    private final Validator<ContactMethod> createContactMethodValidator;

    private final Validator<ContactMethod> updateContactMethodValidator;

    public DefaultContactMethodService(final ContactMethodRepository ContactMethodRepo) {
        super();

        contactMethodRepository = Objects.requireNonNull(ContactMethodRepo);
        createContactMethodValidator = new FieldRuleValidator<>(new ContactMethodNotEmptyRule());
        updateContactMethodValidator = new FieldRuleValidator<>(new ContactMethodNotEmptyRule());
    }

    @Override
    public final ContactMethod create(final ContactMethod contactMethod) {
        final ContactMethod toCreate;
        final Long          number;

        log.debug("Creating ContactMethod {}", contactMethod);

        // Set number
        number = contactMethodRepository.findNextNumber();

        toCreate = new ContactMethod(number, contactMethod.name());

        createContactMethodValidator.validate(toCreate);

        return contactMethodRepository.save(toCreate);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting ContactMethod {}", number);

        if (!contactMethodRepository.exists(number)) {
            log.error("Missing ContactMethod {}", number);
            throw new MissingContactMethodException(number);
        }

        contactMethodRepository.delete(number);
    }

    @Override
    public final Iterable<ContactMethod> getAll(final Pagination pagination, final Sorting sorting) {
        log.debug("Reading ContactMethods with pagination {} and sorting {}", pagination, sorting);

        return contactMethodRepository.findAll(pagination, sorting);
    }

    @Override
    public final Optional<ContactMethod> getOne(final long number) {
        final Optional<ContactMethod> ContactMethod;

        log.debug("Reading ContactMethod {}", number);

        ContactMethod = contactMethodRepository.findOne(number);
        if (ContactMethod.isEmpty()) {
            log.error("Missing ContactMethod {}", number);
            throw new MissingContactMethodException(number);
        }

        return ContactMethod;
    }

    @Override
    public final ContactMethod update(final ContactMethod contactMethod) {
        log.debug("Updating ContactMethod {} using data {}", contactMethod.number(), contactMethod);

        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing ContactMethod {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }

        updateContactMethodValidator.validate(contactMethod);

        return contactMethodRepository.save(contactMethod);
    }

}

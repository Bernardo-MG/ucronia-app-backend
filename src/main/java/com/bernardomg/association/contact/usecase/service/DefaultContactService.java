/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.contact.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.exception.MissingContactException;
import com.bernardomg.association.contact.domain.filter.ContactFilter;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.contact.domain.repository.ContactMethodRepository;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.usecase.validation.ContactIdentifierNotExistForAnotherRule;
import com.bernardomg.association.contact.usecase.validation.ContactIdentifierNotExistRule;
import com.bernardomg.association.contact.usecase.validation.ContactMethodExistsRule;
import com.bernardomg.association.contact.usecase.validation.ContactNameNotEmptyRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the person service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactService implements ContactService {

    /**
     * Logger for the class.
     */
    private static final Logger      log = LoggerFactory.getLogger(DefaultContactService.class);

    private final ContactRepository  contactRepository;

    private final Validator<Contact> createContactValidator;

    private final Validator<Contact> patchContactValidator;

    private final Validator<Contact> updateContactValidator;

    public DefaultContactService(final ContactRepository contactRepo, final ContactMethodRepository contactMethodRepo) {
        super();

        contactRepository = Objects.requireNonNull(contactRepo);
        createContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactMethodExistsRule(contactMethodRepo), new ContactIdentifierNotExistRule(contactRepo));
        updateContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactMethodExistsRule(contactMethodRepo), new ContactIdentifierNotExistForAnotherRule(contactRepo));
        patchContactValidator = new FieldRuleValidator<>(new ContactNameNotEmptyRule(),
            new ContactMethodExistsRule(contactMethodRepo), new ContactIdentifierNotExistForAnotherRule(contactRepo));
    }

    @Override
    public final Contact create(final Contact contact) {
        final Contact toCreate;
        final Long    number;

        log.debug("Creating contact {}", contact);

        // Set number
        number = contactRepository.findNextNumber();

        toCreate = new Contact(contact.identifier(), number, contact.name(), contact.birthDate(), contact.membership(),
            contact.contactChannels());

        createContactValidator.validate(toCreate);

        return contactRepository.save(toCreate);
    }

    @Override
    public final Contact delete(final long number) {
        final Contact existing;

        log.debug("Deleting contact {}", number);

        existing = contactRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing contact {}", number);
                throw new MissingContactException(number);
            });

        contactRepository.delete(number);

        return existing;
    }

    @Override
    public final Page<Contact> getAll(final ContactFilter filter, final Pagination pagination, final Sorting sorting) {
        log.debug("Reading contacts with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        return contactRepository.findAll(filter, pagination, sorting);
    }

    @Override
    public final Optional<Contact> getOne(final long number) {
        final Optional<Contact> contact;

        log.debug("Reading contact {}", number);

        contact = contactRepository.findOne(number);
        if (contact.isEmpty()) {
            log.error("Missing contact {}", number);
            throw new MissingContactException(number);
        }

        return contact;
    }

    @Override
    public final Contact patch(final Contact person) {
        final Contact existing;
        final Contact toSave;

        log.debug("Patching contact {} using data {}", person.number(), person);

        // TODO: Identificator must be unique or empty
        // TODO: Apply the creation validations

        existing = contactRepository.findOne(person.number())
            .orElseThrow(() -> {
                log.error("Missing contact {}", person.number());
                throw new MissingContactException(person.number());
            });

        toSave = copy(existing, person);

        patchContactValidator.validate(toSave);

        return contactRepository.save(toSave);
    }

    @Override
    public final Contact update(final Contact person) {
        log.debug("Updating contact {} using data {}", person.number(), person);

        // TODO: Identificator must be unique or empty
        // TODO: The membership maybe can't be removed

        if (!contactRepository.exists(person.number())) {
            log.error("Missing contact {}", person.number());
            throw new MissingContactException(person.number());
        }

        updateContactValidator.validate(person);

        return contactRepository.save(person);
    }

    private final Contact copy(final Contact existing, final Contact updated) {
        final ContactName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ContactName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new Contact(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.membership())
                .orElse(Optional.empty()),
            updated.contactChannels());
    }

}
